package fmi.java.web.tourFinder.internal.util;

public final class Routes {
    private final static String API = "/api";
    public final static String LOGIN = API + "/login";

    public final static String USERS = API + "/users";
    public final static String USERS_ID = API + "/users/{id}";

    public final static String TOURS = API + "/tours";
    public final static String TOURS_ID = API + "/tours/{id}";
    public final static String TOURS_ID_PICTURES = API + "/tours/{id}/pictures";

    public final static String BOOKING = API + "/booking";
    public final static String BOOKING_ID = API + "/booking/{id}";
}
