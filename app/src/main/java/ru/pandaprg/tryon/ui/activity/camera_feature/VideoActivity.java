package ru.pandaprg.tryon.ui.activity.camera_feature;

/*
 *  Окно построения дополненной реальности.
 *  Совмещает изображение с камеры и добавленные кртинки
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.squareup.picasso.Picasso;

import java.io.File;

import moxy.MvpActivity;
import moxy.presenter.InjectPresenter;
import ru.pandaprg.core_camera_impl.Camera.SurfaceListener;
import ru.pandaprg.tryon.App;
import ru.pandaprg.tryon.R;
import ru.pandaprg.tryon.navigator.MainNavigator;
import ru.pandaprg.tryon.presentation.presenter.camera_feature.VideoPresenter;
import ru.pandaprg.tryon.presentation.view.camera_feature.VideoView;
import ru.terrakok.cicerone.Navigator;

public class VideoActivity extends MvpActivity implements VideoView, SeekBar.OnSeekBarChangeListener {
    public static final String TAG = "VideoActivity";

    @InjectPresenter
    VideoPresenter mVideoPresenter;

    private Navigator navigator;

    private TextureView videoView = null;
    private SurfaceListener surfaceTextureListener;

    private ImageView videoImageView;
    private SeekBar sizeSeekBar;
    private SeekBar alphaSeekBar;

    public static Intent getIntent(final Context context) {
        return new Intent(context, VideoActivity.class);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_video);

        navigator = new MainNavigator(this) ;

        sizeSeekBar = findViewById(R.id.pictureSizeSeekBar);
        sizeSeekBar.setOnSeekBarChangeListener(this);

        alphaSeekBar = findViewById(R.id.pictureAlphaSeekBar);
        alphaSeekBar.setOnSeekBarChangeListener(this);

        videoImageView = findViewById(R.id.videoImageView);
        videoImageView.setOnClickListener(clickListener1);

        videoView =  findViewById(R.id.textureViewVideo);
        surfaceTextureListener = new SurfaceListener(this); // подключаем Камеру
        videoView.setSurfaceTextureListener(surfaceTextureListener);
        surfaceTextureListener.setTextureView(videoView);
    }

    @Override
    protected void onResume () {
        super.onResume();
        App.INSTANCE.getNavigatorHolder().setNavigator(navigator);
        //mVideoPresenter.onResume();
    }

    @Override
    protected void onPause () {
        App.INSTANCE.getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    @Override
    public void setAlpha(float alpha) {
        videoImageView.setAlpha(alpha);
    }

    @Override
    public void setLocalPicture(int id) {
        Picasso.get().load(id).into(videoImageView);
    }

    @Override
    public void setLocalPicture(String fileName) {
        Log.i(TAG, "setLocalPicture: Picasso Load..." + fileName);
        Picasso.get().load(new File (fileName)).into(videoImageView);
        Log.i(TAG, "setLocalPicture: Picasso Loaded...");
    }

    @Override
    public void getPictureParams() {

        mVideoPresenter.onSetPictureParams( videoImageView.getLayoutParams() );
    }

    @Override
    public void setPictureParams(ViewGroup.LayoutParams params) {
        videoImageView.setLayoutParams(params);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.pictureSizeSeekBar :
                mVideoPresenter.onSizeSeekBarChange(progress);
                break;
            case R.id.pictureAlphaSeekBar :
                mVideoPresenter.onAlphaSeekBarChange(progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: ");
            mVideoPresenter.onPictureClick();
        }
    };

}
