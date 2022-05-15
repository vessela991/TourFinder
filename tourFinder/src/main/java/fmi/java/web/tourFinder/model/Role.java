package fmi.java.web.tourFinder.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    USER, AGENCY, ADMIN;


    private static Map<String, Role> rolesMap = new HashMap<String, Role>(3);

    static {
        rolesMap.put("user", USER);
        rolesMap.put("agency", AGENCY);
        rolesMap.put("admin", ADMIN);
    }

    @JsonCreator
    public static Role forValue(String value) {
        return rolesMap.get(value.toLowerCase());
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, Role> entry : rolesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null;
    }
}
