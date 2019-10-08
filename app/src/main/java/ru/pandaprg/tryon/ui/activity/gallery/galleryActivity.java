/**
 * Окно отображения  и выбора изображения из списка
 * View элемент MVP
 */

package ru.pandaprg.tryon.ui.activity.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import moxy.MvpActivity;
import moxy.presenter.InjectPresenter;
import ru.pandaprg.tryon.R;
import ru.pandaprg.tryon.presentation.presenter.gallery.GalleryItem;
import ru.pandaprg.tryon.presentation.presenter.gallery.GalleryPresenter;
import ru.pandaprg.tryon.presentation.view.gallery.GalleryView;

public class galleryActivity extends MvpActivity implements GalleryView {
    public static final String TAG = "galleryActivity";
    @InjectPresenter
    GalleryPresenter mGalleryPresenter;


    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, galleryActivity.class);

        return intent;
    }

    private ArrayList<GalleryItem> GenerateFakeList () {
        ArrayList<GalleryItem> list = new ArrayList<GalleryItem>();
        list.add(new GalleryItem("V1",R.drawable.v1));
        list.add(new GalleryItem("V2",R.drawable.v2));
        list.add(new GalleryItem("V3",R.drawable.v3));
        return list;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.galleryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GalleryAdapter(GenerateFakeList()));
    }
}
