/*
 * Окно приветствия и загрузки программы. Предназначено для инициализации системы,
 * запуска сервисов, создания служебных классов, получения контекста и прав.
 * После подготовки необходимо, как можно быстрее покинуть данное окно
 */

package ru.pandaprg.tryon.ui.activity.main;

import android.app.Activity;
import android.os.Bundle;

import ru.pandaprg.tryon.App;
import ru.pandaprg.tryon.R;
import ru.pandaprg.tryon.navigator.MainNavigator;
import ru.pandaprg.tryon.presentation.presenter.main.MainPresenter;
import ru.terrakok.cicerone.Navigator;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private Navigator navigator;
    private MainPresenter presenter;

   // private Intent intentVideo;
   // private Intent intentGallery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getINSTANCE();
        App.INSTANCE.initCicerone();

        presenter = new MainPresenter();

        navigator = new MainNavigator(this) ;

        presenter.start();

       // intentVideo = VideoActivity.getIntent(this);
       // intentGallery = GalleryActivity.getIntent(this);

       // startActivity(intentGallery);
        //startActivity(intentVideo);
    }

    @Override
    protected void onResume () {
        super.onResume();
        App.INSTANCE.getNavigatorHolder().setNavigator(navigator);

    }

    @Override
    protected void onPause () {
        App.INSTANCE.getNavigatorHolder().removeNavigator();
        super.onPause();
    }


}

