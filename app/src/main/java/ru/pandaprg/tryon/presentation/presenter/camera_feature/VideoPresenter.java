/*
 *  Лоигика управления окном Video
 */

package ru.pandaprg.tryon.presentation.presenter.camera_feature;

import android.util.Log;
import android.view.ViewGroup;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.pandaprg.tryon.App;
import ru.pandaprg.tryon.R;
import ru.pandaprg.tryon.presentation.view.camera_feature.VideoView;
import ru.terrakok.cicerone.Router;


@InjectViewState
public class VideoPresenter extends MvpPresenter<VideoView> {
    private static final String TAG = "VideoPresenter";

    private Router router;

    private float imageAlpha;
    private int imageSize;
    private ViewGroup.LayoutParams params;

    public VideoPresenter () {
        imageAlpha = 90;
        getViewState().setAlpha(imageAlpha);
        getViewState().setLocalPicture(R.drawable.v3);

        router = App.INSTANCE.getRouter();
    }

    public void onSizeSeekBarChange (int progress) {
        imageSize = progress;
        getViewState().getPictureParams();
        params.width =progress;

        getViewState().setPictureParams(params);
    }

    public void onAlphaSeekBarChange (int progress) {
        getViewState().setAlpha((float)progress/100);
    }

    public void onSetPictureParams (ViewGroup.LayoutParams params){
        this.params = params;
    }

    public void onPictureClick () {
        Log.i(TAG, "onPictureClick: " + "StartGallery router="+router.toString());
        router.navigateTo( "StartGallery");
    }
}

/*
* if (pictureN == 0){
                Picasso.get().load(R.drawable.v1).into(imageView);
                pictureN++;
            } else if (pictureN == 1) {
                Picasso.get().load(R.drawable.v2).into(imageView);
                pictureN++;
            } else {
                Picasso.get().load(R.drawable.v3).into(imageView);
                pictureN++;
            }
* */
