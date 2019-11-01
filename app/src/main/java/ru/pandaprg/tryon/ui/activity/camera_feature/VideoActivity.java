package ru.pandaprg.tryon.ui.activity.camera_feature;

/*
 *  Окно построения дополненной реальности.
 *  Совмещает изображение с камеры и добавленные кртинки
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

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

    TextureView videoView = null;
    TextureView.SurfaceTextureListener surfaceTextureListener;

    private ImageView videoImageView;
    private SeekBar sizeSeekBar;
    private SeekBar alphaSeekBar;

    int pictureN =0;

    private Intent intentGallery;

    Uri selectedImageUri; // Global Variable
    String  selectedPath; // Global Variable

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


    //***************************************************************************************************************************************/


    View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: ");
            mVideoPresenter.onPictureClick();

            //cam.choiceCamera(0);
/*
            if (pictureN == 0){
                setLocalPicture(R.drawable.v1);
                pictureN++;
            } else if (pictureN == 1) {
                setLocalPicture(R.drawable.v2);
                pictureN++;
            } else {
                setLocalPicture(R.drawable.v3);
                pictureN = 0;
            }
*/
        }
    };

    View.OnClickListener clickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intentGallery.setType("image/*");
            intentGallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intentGallery,"Select file to upload "), 10);
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if(data.getData() != null){
                selectedImageUri = data.getData();
            }else{
                Log.d("selectedPath1 : ","Came here its null !");
                Toast.makeText(getApplicationContext(), "failed to get Image!", Toast.LENGTH_LONG).show();
            }

            if (requestCode == 10)

            {

                selectedPath = selectedImageUri.getPath();
                videoImageView.setImageURI(selectedImageUri);
                Log.d("selectedPath1 : " ,selectedPath);
                Toast.makeText(getApplicationContext(), " get Image!" + selectedPath, Toast.LENGTH_LONG).show();

            }

        }

    }

}
