/*
 *  Класс обработчик обытий Surface (области просмотра).
 *  Запуск Окна с Surface является стартовой точкой для запуска камеры MyCamera2.
 *  Чтоб избежать ошибок начинать работу с камерой, следует только, после формирования Surface (onSurfaceTextureAvailable)
 *
 */

package ru.pandaprg.core_camera_impl.Camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;

public class SurfaceListener implements TextureView.SurfaceTextureListener {
    private static final String TAG = "SurfaceListener";

    private MyCamera2 myCamera2;
    private Context ctx;

    TextureView view;


    public SurfaceListener(Context ctx) {
        this.ctx = ctx;
    }

    public void setTextureView(TextureView view){
        this.view = view;
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {


        Log.i(TAG, "onSurfaceTextureAvailable:"+surface.toString()+" w="+width+"  h="+height);
        myCamera2 = new MyCamera2(ctx, width, height, view, surface); // (1)-(6)    // Перегружен конструктор, Передаётся много лишних параметров

        //(7) Setup the image buffer size to TextureView
        surface.setDefaultBufferSize(width, height);

        //(8) Start Camera
        myCamera2.startCameraCaptureSession (surface);
    }



    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.i(TAG, "onSurfaceTextureSizeChanged: w="+width+"  h="+height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.i(TAG, "onSurfaceTextureDestroyed: ");
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        Log.i(TAG, "onSurfaceTextureUpdated: ");
    }
}
