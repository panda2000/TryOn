/*
 *
 *  (1) Инициализируем библиотеку управления переходами между экранами
 *
 */
package ru.pandaprg.tryon;

import android.app.Application;
import android.util.Log;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App  extends Application {
    private static final String TAG = "App";

    public static App INSTANCE;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate (){
        Log.i(TAG, "onCreate: ");
        super.onCreate();
        INSTANCE = this;

        initCicerone(); //(1)

    }

    public static App getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new App();
        }
        return INSTANCE;
    }

    //todo Make Private
    public void initCicerone () {
        Log.i(TAG, "initCicerone: ");
        cicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        Log.i(TAG, "getNavigatorHolder: ");
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter () {
        Log.i(TAG, "getRouter: ");
        return cicerone.getRouter();
    }

}
