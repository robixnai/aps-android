package br.com.aps.aps;

import android.app.Application;

import br.com.aps.aps.utils.AppUtil;

public class Aps extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.CONTEXT = getApplicationContext();
    }
}
