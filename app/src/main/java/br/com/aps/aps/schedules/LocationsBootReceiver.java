package br.com.aps.aps.schedules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationsBootReceiver extends BroadcastReceiver {

    private static final String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    private LocationsAlarmReceiver locationsAlarmReceiver = new LocationsAlarmReceiver();

    public LocationsBootReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BOOT_COMPLETED.equals(intent.getAction())) {
            locationsAlarmReceiver.setAlarm(context);
        }
    }
}
