package br.com.aps.aps.storages.internal.async;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import br.com.aps.aps.R;
import br.com.aps.aps.helpers.LocationHelper;
import br.com.aps.aps.models.Location;
import br.com.aps.aps.storages.external.ExternalService;
import br.com.aps.aps.storages.sqlite.user.UsersRepository;
import br.com.aps.aps.utils.AppUtil;

public class MyLocationTask extends AsyncTask<Void, Void, String> {

    public static final String SUCESSO = "sucesso";

    @Override
    protected String doInBackground(Void... params) {
        String result;
        try {
            ExternalService.getInstance().postMyLocation(getLocation());
            result = SUCESSO;
        } catch (IOException e) {
            result = e.getMessage();
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        String message = "";
        if (SUCESSO.equals(result)) {
            message = AppUtil.CONTEXT.getString(R.string.shared_location);
        } else {
            message = AppUtil.CONTEXT.getString(R.string.error_server) + result;
        }
        Toast.makeText(AppUtil.CONTEXT, message, Toast.LENGTH_LONG).show();
    }

    private Location getLocation() {
        Location location = new Location();
        location.setUser(UsersRepository.getInstance().getAll().get(0));
        location.setLatitude(LocationHelper.getLatitude(AppUtil.CONTEXT));
        location.setLongitude(LocationHelper.getLongitude(AppUtil.CONTEXT));

        return location;
    }

}