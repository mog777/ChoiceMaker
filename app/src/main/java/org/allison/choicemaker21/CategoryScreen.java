package org.allison.choicemaker21;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.allison.choicemaker21.data.WordData;
import org.allison.choicemaker21.util.IntentKeys;
import org.allison.choicemaker21.util.MediaSelectorDialog;
import org.allison.choicemaker21.util.buttons.CaptureAudioButton;
import org.allison.choicemaker21.util.buttons.GotoAnotherActivityButton;
import org.allison.choicemaker21.util.buttons.MediaSelectorButton;
import org.allison.choicemaker21.util.buttons.SimpleConfirmButton;
import org.allison.choicemaker21.util.callback.Callback;
import org.allison.choicemaker21.util.callback.Predicate;
import org.allison.choicemaker21.util.callback.StringCallback;
import org.allison.choicemaker21.util.callback.VoidCallback;
import org.allison.choicemaker21.util.provider.DataProvider;
import org.allison.choicemaker21.util.provider.StaticStringProvider;
import org.allison.choicemaker21.util.provider.StringProvider;
import org.allison.choicemaker21.util.transferable.CategoryToWordScreen;
import org.allison.choicemaker21.util.transferable.MainToCategoryScreen;
import org.allison.choicemaker21.util.transferable.Transferable;
import org.allison.choicemaker21.util.transferable.WordChoice;
import org.allison.choicemaker21.util.views.MultiSelectGroup;
import org.allison.choicemaker21.util.views.SideBySideButtonsView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class CategoryScreen extends ActionBarActivity {

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        byte[] bytes = intent.getByteArrayExtra(IntentKeys.SERIALIZED_DATA);
        MainToCategoryScreen data = new MainToCategoryScreen();
        data = data.deserialize(bytes);
        if (data.getCategories().size() > 0) {
            category = data.getCategories().get(0);
        }

        setContentView(createView());
    }

    private View createView() {
        final WordData wordData = new WordData(this, category, true);

        final MultiSelectGroup categoryNamesGroup =
                new MultiSelectGroup(
                        MultiSelectGroup.Mode.MULTI_SELECT,
                        wordData.getNames(),
                        this);

        categoryNamesGroup.withBottomView(
                new SideBySideButtonsView(
                        this,
                        addMediaButton(categoryNamesGroup),
                        recordAudioButton(categoryNamesGroup, wordData)
                )
                        .createView());

        categoryNamesGroup.withBottomView(
                new SideBySideButtonsView(
                        this,
                        addWordButton(wordData),
                        removeWordButton(wordData, categoryNamesGroup),
                        gotoWordActivityButton(categoryNamesGroup, wordData))
                        .createView());

        return categoryNamesGroup.createView();
    }

    @SuppressWarnings("unchecked")
    private Button addWordButton(final WordData wordData) {
        return new SimpleConfirmButton(
                this,
                "Add Word",
                new StaticStringProvider("Add Word"),
                new StringCallback() {
                    @Override
                    public void call(String input) {
                        wordData.addName(input);
                        setContentView(createView());
                    }
                });
    }

    @SuppressWarnings("unchecked")
    private Button removeWordButton(
            final WordData wordData,
            final MultiSelectGroup categoryNamesGroup) {
        return new SimpleConfirmButton(
                this,
                "Delete Word",
                new StaticStringProvider("Delete Word"),
                new VoidCallback() {
                    @Override
                    public void call(Void v) {
                        List<String> selected = categoryNamesGroup.getSelected();
                        wordData.removeNames(selected);
                        setContentView(createView());
                    }
                }
        );
    }

    private Button addMediaButton(final MultiSelectGroup categoryNamesGroup) {
        return new MediaSelectorButton(this, "Take Picture", new StringProvider() {
            @Override
            public String getData() {
                List<String> selected = categoryNamesGroup.getSelected();
                if (selected.isEmpty()) {
                    return "";
                } else {
                    return selected.get(0);
                }
            }
        });
    }

    private Button recordAudioButton(
            final MultiSelectGroup categoryNamesGroup,
            final WordData wordData
            ) {
        return new CaptureAudioButton(
                this,
                "Record Voice",
                CaptureAudioButton.Type.RECORDER,
                new StringProvider() {
                    @Override
                    public String getData() {
                        List<String> selected = categoryNamesGroup.getSelected();
                        if (selected.isEmpty()) {
                            return "";
                        } else {
                            return category + "_" + selected.get(0);
                        }
                    }
                },
                new StringProvider() {
                    @Override
                    public String getData() {
                        List<String> selected = categoryNamesGroup.getSelected();
                        if (selected.isEmpty()) {
                            return "";
                        } else {
                            return selected.get(0);
                        }
                    }
                },
                new Callback<Pair<String,String>>() {
                    @Override
                    public Class<Pair<String, String>> getType() {
                        //TODO fix this
                        return (Class<Pair<String, String>>) this.getClass().getGenericSuperclass();
                    }

                    @Override
                    public void call(Pair<String, String> stringStringPair) {
                        wordData.addAudioFile(stringStringPair.first, stringStringPair.second);
                    }
                });
    }

    /*
    private Button playAudioButton() {
        return new CaptureAudioButton(this, "Play", CaptureAudioButton.Type.PLAYER);
    }
    */

    private Button gotoWordActivityButton(
            final MultiSelectGroup multiSelectGroup,
            final WordData wordData) {
        return new GotoAnotherActivityButton(
                this,
                "Go!",
                WordScreen.class,
                new DataProvider<Transferable<?>>() {
                    @Override
                    public Transferable<?> getData() {
                        List<WordChoice> choices = new ArrayList<>();
                        for (String word : multiSelectGroup.getSelected()) {
                            WordChoice choice = new WordChoice();
                            choice.setWord(word);
                            choice.setThumbnail(wordData.getThumbnail(word));
                            choice.setAudioFile(wordData.getAudioFile(word));
                            choices.add(choice);
                        }
                        CategoryToWordScreen categoryToWordScreen = new CategoryToWordScreen();
                        categoryToWordScreen.setCategory(category);
                        categoryToWordScreen.setWordChoices(choices);
                        return categoryToWordScreen;
                    }
                },
                new Predicate<View>() {
                    @Override
                    public boolean success(View view) {
                        return !multiSelectGroup.getSelected().isEmpty();
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category_screen, menu);
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

    public final static AtomicReference<String> MEDIA_DIALOG_STRING = new AtomicReference<String>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MediaSelectorDialog.MEDIA_DIALOG_IMAGE_REQ_ID && resultCode == RESULT_OK) {
            String name = MEDIA_DIALOG_STRING.get();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            WordData wordData = new WordData(this, category, false);
            wordData.addThumbnail(name, imageBitmap);
            //mImageView.setImageBitmap(imageBitmap);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
