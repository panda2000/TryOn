package ru.pandaprg.tryon.presentation.presenter.gallery;


import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.pandaprg.tryon.App;
import ru.pandaprg.tryon.model.Model;
import ru.pandaprg.tryon.presentation.view.gallery.GalleryView;


@InjectViewState
public class GalleryPresenter extends MvpPresenter<GalleryView> {

    private static final String TAG = "GalleryPresenter";

    private ArrayList<GalleryItem> list;
    private ArrayList<String> dirs;


    public ArrayList<GalleryItem> generateList () {
        list = new ArrayList<GalleryItem>();
        dirs = new ArrayList<String>();

        return list;
    }

    public ArrayList<GalleryItem> getListFiles (File dir) {
        //Log.i(TAG, "getListFiles: "+ dir.getAbsolutePath());
        ArrayList<GalleryItem> tempList = new ArrayList<GalleryItem>();
        for (File file : dir.listFiles()) {
                //Log.i(TAG, "getListFiles: " + file.getName());
                if (file.isDirectory()) {
                    dirs.add(file.getName());
                    getListFiles(file);
                } else {
                    if (file.getPath().endsWith(".jpg")||file.getPath().endsWith(".jpeg")) {
                        GalleryItem item = new GalleryItem(file.getName(), file.getAbsolutePath());
                        list.add(item);
                    }
                }
            }

        Log.i(TAG, "getListFiles: len = " + list.size());
        return list;
    }

    public void onClick (String fileName) {
        Model.getInstance().setPictureName(fileName);
        App.INSTANCE.getRouter().backTo(null);
    }

}
