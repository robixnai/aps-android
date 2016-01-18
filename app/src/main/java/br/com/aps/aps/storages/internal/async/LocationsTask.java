package br.com.aps.aps.storages.internal.async;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import br.com.aps.aps.R;
import br.com.aps.aps.models.Locations;
import br.com.aps.aps.storages.external.ExternalService;
import br.com.aps.aps.storages.internal.listener.LocationsListener;
import br.com.aps.aps.utils.AppUtil;

public class LocationsTask extends AsyncTask<Void, Void, Locations> {

    private LocationsListener mLocationsListener;

    public LocationsTask(LocationsListener locationsListener) {
        mLocationsListener = locationsListener;
    }

    @Override
    protected Locations doInBackground(Void... params) {
        Locations locations = null;
        try {
            locations = ExternalService.getInstance().getLocations();
        } catch (IOException e) {
            Toast.makeText(AppUtil.CONTEXT, AppUtil.CONTEXT.getString(R.string.error_server) + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return locations;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Locations result) {
        if (mLocationsListener != null) {
            mLocationsListener.onPostExecuteLocations(result);
        }
    }

}
