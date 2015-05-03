package org.allison.choicemaker21.util.buttons;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import org.allison.choicemaker21.util.callback.Callback;
import org.allison.choicemaker21.util.provider.StringProvider;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Allison on 5/3/2015.
 */
public class CaptureAudioButton extends Button {

    public enum Type {
        RECORDER,
        PLAYER
    }

    private final AtomicBoolean isRecording = new AtomicBoolean(false);

    private final String buttonText;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;

    private final StringProvider filenameProvider;
    private final StringProvider stringContextProvider;

    private final Context context;

    private final Callback<Pair<String, String>> recordedCallback;

    private final AtomicReference<String> recordingFile = new AtomicReference<>();
    private final AtomicReference<String> recordingContext = new AtomicReference<>();

    public CaptureAudioButton(
            Context context,
            final String buttonText,
            Type type,
            StringProvider filenameProvider,
            StringProvider stringContextProvider,
            Callback<Pair<String, String>> recordedCallback) {
        super(context);

        this.context = context;
        this.buttonText = buttonText;
        this.stringContextProvider = stringContextProvider;
        this.recordedCallback = recordedCallback;

        this.setText(buttonText);

        //audioFile = context.getFilesDir().getAbsolutePath() + "/audiotest.3gp";
        this.filenameProvider = filenameProvider;

        if (type == Type.RECORDER) {
            this.setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!isRecording.get()) {
                                startRecording();
                                isRecording.set(true);
                                CaptureAudioButton.this.setText("RECORDING");
                            } else {
                                stopRecording();
                                isRecording.set(false);
                                CaptureAudioButton.this.setText(buttonText);
                            }
                        }
                    });
        } else if (type == Type.PLAYER) {
            this.setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPlaying();
                        }
                    });
        }
    }

    private void startRecording() {
        String audioFile = context.getFilesDir().getAbsolutePath() + "/" + filenameProvider.getData();
        recordingFile.set(audioFile);
        recordingContext.set(stringContextProvider.getData());
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(audioFile);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            //Log.e(LOG_TAG, "prepare() failed");
            e.printStackTrace();
            return;
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        final String audioFile = recordingFile.get();
        recordingFile.set(null);
        recordedCallback.call(new Pair(recordingContext.get(), audioFile));
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            String audioFile = context.getFilesDir().getAbsolutePath() + "/" + filenameProvider.getData();
            mPlayer.setDataSource(audioFile);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(
                    new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mPlayer.release();
                            mPlayer = null;
                        }
                    }

            );
        } catch (IOException e) {
            //Log.e(LOG_TAG, "prepare() failed");
            e.printStackTrace();
        }
    }
}
