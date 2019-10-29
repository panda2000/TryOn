/*
 *  Класс  MyCamera2 реализует взаимодействие с камерой через API
 *  // (1) создаём Camera Manager
 *  // (2) Устанавливаем Front cameraID
 *  // (3) Настраиваем область предпросмотра. Камеры и экраны могут иметь различное разрешение,
 *          необходимо выбрать оптимальное для ориентации и разрешения экрана
 *  // (4) Настраиваем матрицу трансформации. Для подгонки изображения кэкрану используем матрицу трансформации
 *  // (5) Запускаем фоновый поток
 *  // (6) Подключаемся к камере с полученным ID
 *  // (7) Возвращаемся В SurfaceListener и устанавливаем размер буффера просмотра TextureView
 *  // (8) Запускаем камеру
 *  // (9) Запускаем предпросмотр привязанный к камере
 */

package ru.pandaprg.core_camera_impl.Camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class MyCamera2 {
    private static final String TAG = "MyCamera2";

    private Context ctx;
    private CameraManager cameraManager;

    private int width;
    private int height;

    private String cameraID;

    boolean isClosed;

    private HandlerThread backgroundHandlerThread;
    private Handler backgroundHandler;

    private CameraCaptureSession cameraCaptureSession = null;


    private CameraDevice cameraDevice;

    private Surface surface;

    private ImageReader imageReader;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyCamera2(Context ctx, int width, int height) {

        this.ctx = ctx;
        this.width = width;
        this.height = height;

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // 1 создаём Camera Manager
        cameraManager = (CameraManager) ctx.getSystemService(Context.CAMERA_SERVICE);
        Log.i(TAG, "MyCamera2: (1) Create CameraManager");

        // 2 Устанавливаем Front cameraID
        cameraID = setupCameraID(cameraManager);
        Log.i(TAG, "MyCamera2: (2) set Camera ID = " + cameraID);

        // (3) Настраиваем область предпросмотра. Камеры и экраны могут иметь различное разрешение, необходимо выбрать оптимальное для ориентации и разрешения экрана
        setUpCameraOutputs(ctx, width, height);

        // (4) Настраиваем матрицу трансформации. Для подгонки изображения кэкрану используем матрицу трансформации
        configureTransform(width, height);

        // (5) Запускаем фоновый поток
        // (6) Подключаемся к камере с полученным ID
        cameraOpen(); // (5) (6)

        // Возвращаемся в SurfaceListener

    }


    // 2 Устанавливаем Front cameraID
    private String setupCameraID(CameraManager cameraManager) throws IllegalStateException {
        String[] cameraList = new String[0];
        try {
            cameraList = cameraManager.getCameraIdList(); // получаем список камер

            for (String camera : cameraList) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(camera);
                Integer cameraDirection = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if (cameraDirection != null &&
                        cameraDirection == CameraCharacteristics.LENS_FACING_BACK) {
                    return camera;
                }
            }
        } catch (CameraAccessException e) {
            throw new IllegalStateException("Could not set Camera Id");
        }
        return null;
    }

    // (3) Настраиваем область предпросмотра
    private void setUpCameraOutputs(Context ctx, int width, int height) {
        Log.i(TAG, "setUpCameraOutputs: width=" + width + " height=" + height);
    }

    // (4) Настраиваем матрицу трансформации. Для подгонки изображения кэкрану используем матрицу трансформации
    private void configureTransform(int width, int height) {
        Log.i(TAG, "configureTransform: width=" + width + " height=" + height);
    }

    // (5) Открываем камеру. Запускаем фоновый поток
    private void cameraOpen() {
        try {

            startBackgroundThread(); // (5)
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            ////(6) Open a connection to the camera with the given id
            cameraManager.openCamera(cameraID, cameraDeviceStateCallback, backgroundHandler); //(6)
            Log.i(TAG, "cameraOpen: Open a connection to the camera with the given id");
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    // Чтоб не загружать основной поток, запускаем камеру в фоновом потоке
    private void startBackgroundThread() {
        Log.i(TAG, "cameaOpen: Start Background Thread");
        backgroundHandlerThread = new HandlerThread("Camera2VideoImage");
        if (backgroundHandlerThread != null) {
            backgroundHandlerThread.start();
            Log.i(TAG, "startBackgroundThread: getLooper()");
            backgroundHandler = new Handler(backgroundHandlerThread.getLooper());
        }
    }

    // Останавливаем фоновый поток, если не нужна сьёмка
    private void stopBackgroundThread() {
        Log.i(TAG, "stopBackgroundThread: ");
            backgroundHandlerThread.quitSafely();
        try {
            backgroundHandlerThread.join();
            backgroundHandlerThread = null;
            backgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //(8) Запускаем камеру
    public void startCameraCaptureSession(SurfaceTexture surface) {
        /*
        Log.i(TAG, "startCameraCaptureSession: Create Image reader");
        imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1); // Создаём но не используем?

        Log.i(TAG, "startCameraCaptureSession: Create Image reader DONE");
        */

    ArrayList<Surface> surfaceArrayList = new ArrayList<Surface>();
        this.surface = new Surface(surface);
        surfaceArrayList.add(this.surface);


        try {
            Log.i(TAG, "startCameraCaptureSession: createCaptureSession");
            cameraDevice.createCaptureSession(surfaceArrayList,
                    cameraCaptureStateCallback,
                    backgroundHandler);
            Log.i(TAG, "startCameraCaptureSession: createCaptureSession DONE");
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


    }

    //(9) Запускаем предпросмотр привязанный к камере
    private void startPreview() {

        try {

            CaptureRequest.Builder builder = createPreviewRequestBuilder();

            cameraCaptureSession.setRepeatingRequest(
                    builder.build(), captureCallback, backgroundHandler);

        } catch (IllegalStateException e1 ) {
        } catch (CameraAccessException e2) {
        }
    }

    private CaptureRequest.Builder createPreviewRequestBuilder()  {
        CaptureRequest.Builder builder = null;
        try {
            builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        builder.addTarget(surface);
        //enableDefaultModes(builder);
        return builder;
    }

    //---------------------------------------------------------------------------------------
    //Callback реагирует на состояние камеры
    CameraDevice.StateCallback cameraDeviceStateCallback = new CameraDevice.StateCallback () {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            Log.i(TAG, "onOpened: ");
            cameraDevice = camera;
            isClosed = false;

        }

        public void onClosed(@NonNull CameraDevice camera) {
            Log.i(TAG, "onClosed: ");
            isClosed = true;
            stopBackgroundThread();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            Log.i(TAG, "onDisconnected: ");
            camera.close();
            cameraDevice = null;
            isClosed = true;
            stopBackgroundThread();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Log.i(TAG, "onError: ");
            camera.close();
            cameraDevice = null;
            isClosed = true;
            stopBackgroundThread();
        }
    };

    //Callback реагирует на настройку сессиий просмотра
    CameraCaptureSession.StateCallback cameraCaptureStateCallback = new CameraCaptureSession.StateCallback(){

        @Override
        public void onConfigured(@NonNull CameraCaptureSession session) {
            Log.i(TAG, "onConfigured: ");
            cameraCaptureSession = session;
            startPreview(); //(9) (Finally) Start the camera preview
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
            Log.i(TAG, "onConfigureFailed: ");
        }
    };

    //Callback реагирует на получение кадра
     CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() {

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                       @NonNull CaptureRequest request,
                                       @NonNull TotalCaptureResult result) {
            //Log.i(TAG, "onCaptureCompleted: ");
        }
    };
}
