/**
 *Класс GallaryAdapter используется для преобразования  набора данных JAVA list в набор VIEW компонентов
 *  list.imageName -> textView
 *  list.imagePath -> imageView
 *
 * Класс ViewHolder хранит прямые ссылки на View компоненты.
 * Уменьшает количеств вызовов findViewById, т.к. они выполняются только при инициализации
 * привязывает обработчики событий к View компонентам
 */



package ru.pandaprg.tryon.ui.activity.gallery;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.pandaprg.tryon.R;
import ru.pandaprg.tryon.presentation.presenter.gallery.GalleryItem;
import ru.pandaprg.tryon.presentation.presenter.gallery.GalleryPresenter;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private static final String TAG = "GalleryAdapter";
    ArrayList<GalleryItem> list;
    GalleryPresenter mGalleryPresenter;

    public GalleryAdapter(ArrayList<GalleryItem> list, GalleryPresenter presenter) {
        this.list = list;
        this.mGalleryPresenter = presenter;
    }

    @NonNull
    @Override
    // Создание элементов списка
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_layout, parent, false);

        return new ViewHolder(v);
    }

    //Заполнение элемента списка View данными из набора (с индексом position)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GalleryItem item = list.get(position);
        //holder.textView.setText(item.getImageName());
        Log.i(TAG, "onBindViewHolder: " + item.getImagePath());
        Picasso.get().load("file://" + item.getImagePath()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "ViewHolder";
        //TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //textView = itemView.findViewById(R.id.galleryTextItem);
            imageView = itemView.findViewById(R.id.galleryImageItem);
            imageView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            String filePath = list.get(getAdapterPosition()).getImagePath();
            Log.i(TAG, "onClick: "+ filePath);
            mGalleryPresenter.onClick(filePath);
        }
    }
}
