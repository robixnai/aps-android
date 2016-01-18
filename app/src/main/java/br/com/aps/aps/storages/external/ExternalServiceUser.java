package br.com.aps.aps.storages.external;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.com.aps.aps.helpers.LocationHelper;
import br.com.aps.aps.models.Location;
import br.com.aps.aps.models.Locations;
import br.com.aps.aps.models.User;

public class ExternalServiceUser {

    private ExternalServiceUser() {
        super();
    }

    public static ExternalServiceUser getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static User postUser(User user) throws IOException {
        final OkHttpClient userSave = new OkHttpClient();
        final String url = ExternalService.URI_SERVER + "/user";
        final RequestBody body = RequestBody.create(ExternalService.JSON, userJson(user));
        final Request request = new Request.Builder().url(url).post(body).build();
        final Response response = userSave.newCall(request).execute();

        if (response.code() != 200) {
            throw new IOException(response.message());
        }

        final String responseUser = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(responseUser, User.class);
    }

    private static String userJson(User user) {
        return "{\"user\": {"
                + "\"email\": \"" + user.getEmail() + "\" ,"
                + "\"phone\": \"" + user.getPhone() + "\""
                + "}"
                + ""
                + "}";
    }

    public void postMyLocation(Location location) throws IOException {
        final OkHttpClient myLcaotionSave = new OkHttpClient();
        final String url = ExternalService.URI_SERVER + "/overflow";
        final RequestBody body = RequestBody.create(ExternalService.JSON, locationJson(location));
        final Request request = new Request.Builder().url(url).post(body).build();
        final Response response = myLcaotionSave.newCall(request).execute();

        if (response.code() != 200) {
            throw new IOException(response.message());
        }
    }

    private static String locationJson(Location location) {
        return "{\n" +
                "    \"user_id\": " + location.getUser().getId() + ",\n" +
                "    \"address\": {\n" +
                "        \"latitude\": \"" + location.getLatitude() + "\",\n" +
                "        \"longitude\": \" " + location.getLongitude() + " \"\n" +
                "    }\n" +
                "}";
    }

    public Locations getLocations() throws IOException {
        final OkHttpClient client = new OkHttpClient();
        final String url = ExternalService.URI_SERVER + "/overflow/" + LocationHelper.timeUpdate();
        final Request request = new Request.Builder().url(url).build();
        final Response response = client.newCall(request).execute();

        if (response.code() != 200) {
            throw new IOException(response.message());
        }

        final String responseLocation = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(responseLocation, Locations.class);
    }

    private static class LazyHolder {
        private static final ExternalServiceUser INSTANCE = new ExternalServiceUser();
    }

}
