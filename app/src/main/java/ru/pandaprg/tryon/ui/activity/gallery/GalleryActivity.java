/*
 * Окно отображения  и выбора изображения из списка
 * View элемент MVP
 */

package ru.pandaprg.tryon.ui.activity.gallery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import moxy.MvpActivity;
import moxy.presenter.InjectPresenter;
import ru.pandaprg.tryon.App;
import ru.pandaprg.tryon.R;
import ru.pandaprg.tryon.navigator.MainNavigator;
import ru.pandaprg.tryon.presentation.presenter.gallery.GalleryPresenter;
import ru.pandaprg.tryon.presentation.view.gallery.GalleryView;
import ru.terrakok.cicerone.Navigator;

public class GalleryActivity extends MvpActivity implements GalleryView {
    public static final String TAG = "GalleryActivity";
    @InjectPresenter
    GalleryPresenter mGalleryPresenter;
    private Navigator navigator;

    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public final int EXTERNAL_REQUEST = 138;

    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));
    }

    public boolean requestForPermission() {
        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            if (!canAccessExternalSd()) {
                isPermissionOn = false;
                ActivityCompat.requestPermissions(this, EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
        }
        return isPermissionOn;
    }

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, GalleryActivity.class);

        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.galleryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestForPermission();
        mGalleryPresenter.generateList ();

        navigator = new MainNavigator(this) ;

        File root = Environment.getExternalStorageDirectory().getAbsoluteFile();

        recyclerView.setAdapter(new GalleryAdapter(mGalleryPresenter.getListFiles(root)));
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
