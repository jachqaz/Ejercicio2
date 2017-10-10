package co.edu.ucc.ejercicio2.app;

import android.app.Application;

import co.edu.ucc.ejercicio2.repositorio.AppDB;

/**
 * Created by jach_ on 3/10/2017.
 */

public class ApplicationList extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AppDB.init(getApplicationContext());
    }
}
