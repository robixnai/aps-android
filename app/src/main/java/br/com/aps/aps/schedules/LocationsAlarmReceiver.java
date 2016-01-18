package br.com.aps.aps.schedules;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import br.com.aps.aps.utils.AppUtil;

public class LocationsAlarmReceiver extends WakefulBroadcastReceiver {

    private static final int INTERVAL = 1000 * 60;

    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        final Intent service = new Intent(context, LocationsSchedulingService.class);
        startWakefulService(context, service);
    }

    public void setAlarm(Context context) {
        mAlarmManager = AppUtil.get(context.getSystemService(Context.ALARM_SERVICE));
        final Intent intent = new Intent(context, LocationsAlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL, mPendingIntent);

        final ComponentName componentName = new ComponentName(context, LocationsBootReceiver.class);
        final PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
    }


}
