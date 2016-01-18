package br.com.aps.aps.storages.external;

import com.squareup.okhttp.MediaType;

import java.io.IOException;

import br.com.aps.aps.models.Location;
import br.com.aps.aps.models.Locations;
import br.com.aps.aps.models.User;

public class ExternalService {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String URI_SERVER = "http://aps.herokuapp.com";

    private ExternalService() {
        super();
    }

    public static ExternalService getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static User postUser(User user) throws IOException {
        return ExternalServiceUser.getInstance().postUser(user);

    }

    public static void postMyLocation(Location location) throws IOException {
        ExternalServiceUser.getInstance().postMyLocation(location);
    }

    public static Locations getLocations() throws IOException {
        return ExternalServiceUser.getInstance().getLocations();
    }

    private static class LazyHolder {
        private static final ExternalService INSTANCE = new ExternalService();
    }

}
