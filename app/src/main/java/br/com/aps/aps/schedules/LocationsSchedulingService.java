package br.com.aps.aps.schedules;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import br.com.aps.aps.R;
import br.com.aps.aps.helpers.MapsHelper;
import br.com.aps.aps.models.Locations;
import br.com.aps.aps.storages.internal.async.LocationsTask;
import br.com.aps.aps.storages.internal.listener.LocationsListener;
import br.com.aps.aps.utils.AppUtil;
import br.com.aps.aps.views.MapsActivity;

public class LocationsSchedulingService extends IntentService {

    private static final String SCHEDULING_SERVICE = "LocationsSchedulingService";
    private static final int NOTIFICATION_ID = 1;

    private NotificationManager mNotificationManager;

    public LocationsSchedulingService() {
        super(SCHEDULING_SERVICE);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new LocationsTask(new LocationsListener() {
            @Override
            public void onPostExecuteLocations(Locations result) {
                MapsHelper.locationsMap(result.getLocations());
                if (MapsHelper.getLocations().size() > 0) {
                    sendNotification(getString(R.string.msg_flooding_notification));
                }
            }
        }).execute();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LocationsAlarmReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        mNotificationManager = AppUtil.get(this.getSystemService(Context.NOTIFICATION_SERVICE));

        final Intent intent = new Intent(this, MapsActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);

        // TODO: Parâmetros definido nas configurações
        builder.setVibrate(new long[]{1000, 1000});
        builder.setSound(soundUri);

        builder.setContentTitle(getString(R.string.app_name));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
        builder.setContentText(msg).setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
