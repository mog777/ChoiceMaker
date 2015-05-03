package org.allison.choicemaker21;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.allison.choicemaker21.util.AudioFile;
import org.allison.choicemaker21.util.IntentKeys;
import org.allison.choicemaker21.util.callback.StringCallback;
import org.allison.choicemaker21.util.callback.VoidCallback;
import org.allison.choicemaker21.util.provider.BitmapProvider;
import org.allison.choicemaker21.util.provider.DataProvider;
import org.allison.choicemaker21.util.provider.StaticStringProvider;
import org.allison.choicemaker21.util.transferable.CategoryToWordScreen;
import org.allison.choicemaker21.util.transferable.WordChoice;
import org.allison.choicemaker21.util.views.FillScreenColumns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class WordScreen extends ActionBarActivity {

    List<WordChoice> words;

    String category;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        tts = initTextToSpeech();

        Intent intent = getIntent();
        byte[] bytes = intent.getByteArrayExtra(IntentKeys.SERIALIZED_DATA);
        CategoryToWordScreen data = new CategoryToWordScreen();
        data = data.deserialize(bytes);
        words = data.getWordChoices();

        setContentView(createView());
    }

    private View createView() {
        final List<Pair<DataProvider, VoidCallback>> in = new ArrayList<>();
        for (WordChoice w : words) {
            in.add(createPairForChoice(w));
        }
        FillScreenColumns grid = new FillScreenColumns(this, in);

        return grid.createView();

    }

    private Pair<DataProvider, VoidCallback> createPairForChoice(final WordChoice choice) {

        DataProvider dp;
        byte[] thumbnail = choice.getThumbnail();
        if (thumbnail != null && thumbnail.length > 0) {
            final Bitmap bmp = BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length);
            dp = new BitmapProvider(bmp);
        } else {
            dp = new StaticStringProvider(choice.getWord());
        }

        final String audioFile = choice.getAudioFile();
        VoidCallback vc;
        if (audioFile != null && !audioFile.isEmpty()) {
            vc = new VoidCallback() {
                @Override
                public void call(Void aVoid) {
                    startPlaying(audioFile);
                }
            };
        } else {
            vc = new VoidCallback() {
                @Override
                public void call(Void aVoid) {
                    tts.speak(choice.getWord(), TextToSpeech.QUEUE_FLUSH, null);
                }
            };
        }

        return new Pair<>(dp, vc);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_word_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private TextToSpeech initTextToSpeech() {
        TextToSpeech ret = new TextToSpeech(
                WordScreen.this,
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {

                    }
                });

        ret.setLanguage(Locale.US);

        return ret;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        tts.shutdown();
    }

    private void startPlaying(String audioFile) {
        final MediaPlayer mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(audioFile);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(
                    new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mPlayer.release();
                        }
                    }

            );
        } catch (IOException e) {
            //Log.e(LOG_TAG, "prepare() failed");
            e.printStackTrace();
        }
    }
}
