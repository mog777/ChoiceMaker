package org.allison.choicemaker21;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.allison.choicemaker21.data.WordData;
import org.allison.choicemaker21.util.IntentKeys;
import org.allison.choicemaker21.util.callback.StringCallback;
import org.allison.choicemaker21.util.transferable.CategoryToWordScreen;
import org.allison.choicemaker21.util.transferable.WordChoice;
import org.allison.choicemaker21.util.views.FillScreenColumns;

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
        Map<String, Object> wordMap = new HashMap<>();
        for(WordChoice w : words) {
            wordMap.put(w.getWord(), w.getWord());
        }
        FillScreenColumns grid =
                new FillScreenColumns(
                        wordMap,
                        this,
                        new StringCallback() {
                            @Override
                            public void call(String s) {
                                tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        });
        return grid.createView();

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
}
