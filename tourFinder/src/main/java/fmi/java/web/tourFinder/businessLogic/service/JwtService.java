package fmi.java.web.tourFinder.businessLogic.service;

import fmi.java.web.tourFinder.businessLogic.Result;
import fmi.java.web.tourFinder.businessLogic.exception.UnauthorizedException;
import fmi.java.web.tourFinder.model.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final static String SECRET = "VerySecretSecret";
    private final static String CLAIM_USER_ID = "userId";
    private final static Integer EXPIRATION_TIME = 7_200_000; //2 hours in millis

    public String generateToken(User user) {
        return createToken(createClaims(user));
    }

    private String createToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    private Claims createClaims(User user) {
        Claims claims = Jwts.claims();
        claims.put(CLAIM_USER_ID, user.getId());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
        return claims;
    }

    public Result<String> getUserId(String jwt) {
        try {
            var claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();
            return Result.success(claims.get(CLAIM_USER_ID).toString());
        }
        catch (ExpiredJwtException | IllegalArgumentException | SignatureException | MalformedJwtException | UnsupportedJwtException exception) {
            return Result.failure(UnauthorizedException.instance());
        }
    }
}