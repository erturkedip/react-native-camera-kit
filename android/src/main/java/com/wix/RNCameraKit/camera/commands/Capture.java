package com.wix.RNCameraKit.camera.commands;

import android.content.Context;
import android.hardware.Camera;

import com.facebook.react.bridge.Promise;

import com.wix.RNCameraKit.camera.CameraViewManager;
import com.wix.RNCameraKit.SaveImageTask;

public class Capture implements Command {

    private final Context context;
    private boolean saveToCameraRoll;
    private String fileName;
    private String path;

    public Capture(Context context, boolean saveToCameraRoll, String fileName, String path) {
        this.context = context;
        this.saveToCameraRoll = saveToCameraRoll;
        this.fileName = fileName;
        this.path = path;
    }

    @Override
    public void execute(final Promise promise) {
        try {
            tryTakePicture(promise);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryTakePicture(final Promise promise) throws Exception {
        CameraViewManager.getCamera().takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                camera.stopPreview();
                new SaveImageTask(context, promise, saveToCameraRoll, fileName, path).execute(data);
            }
        });
    }
}
