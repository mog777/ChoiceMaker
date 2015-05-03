package org.allison.choicemaker21.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.allison.choicemaker21.CategoryScreen;
import org.allison.choicemaker21.util.provider.StringProvider;

import java.io.IOException;

/**
 * Created by Allison on 5/2/2015.
 */
public class MediaSelectorDialog implements View.OnClickListener {

    private final Context context;

    private final StringProvider intentData;

    public MediaSelectorDialog(Context context, StringProvider intentData) {
        this.context = context;
        this.intentData = intentData;
    }

    @Override
    public void onClick(View v) {
        dispatchTakePictureIntent();
    }

    public static final int MEDIA_DIALOG_IMAGE_REQ_ID = 100;
    public static final String INTENT_DATA = "media_intent_data";

    private void dispatchTakePictureIntent() {
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else {
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(INTENT_DATA, intentData.getData());
        CategoryScreen.MEDIA_DIALOG_STRING.set(intentData.getData());
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, MEDIA_DIALOG_IMAGE_REQ_ID);
        }
    }

    private void tryToTakePicture() {
        final Camera camera = Camera.open();
        if (camera == null) {
            return;
        }

        final View currentView;
        final Activity activity;
        final SurfaceView surfaceView = new SurfaceView(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
            currentView = activity.findViewById(android.R.id.content);
            ViewParent parent = currentView.getParent();
            if (parent instanceof ViewGroup) {
                System.out.println("Removing view");
                ((ViewGroup) parent).removeView(currentView);
            }
            activity.setContentView(surfaceView);
        } else {
            return;
        }

        try {
            camera.setPreviewDisplay(surfaceView.getHolder());
        } catch (IOException e) {
            //e.printStackTrace();
            return;
        }
        camera.startPreview();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
        }
        camera.takePicture(new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                camera.stopPreview();
                camera.release();
                activity.setContentView(currentView);
            }
        }, null, null, null);

    }
}
