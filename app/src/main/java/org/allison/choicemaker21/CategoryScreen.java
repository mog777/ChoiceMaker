package org.allison.choicemaker21;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.allison.choicemaker21.data.WordData;
import org.allison.choicemaker21.util.IntentKeys;
import org.allison.choicemaker21.util.views.SideBySideButtonsView;
import org.allison.choicemaker21.util.buttons.GotoAnotherActivityButton;
import org.allison.choicemaker21.util.buttons.SimpleConfirmButton;
import org.allison.choicemaker21.util.callback.StringCallback;
import org.allison.choicemaker21.util.callback.VoidCallback;
import org.allison.choicemaker21.util.provider.DataProvider;
import org.allison.choicemaker21.util.provider.StaticStringProvider;
import org.allison.choicemaker21.util.transferable.CategoryToWordScreen;
import org.allison.choicemaker21.util.transferable.MainToCategoryScreen;
import org.allison.choicemaker21.util.transferable.Transferable;
import org.allison.choicemaker21.util.views.MultiSelectGroup;

import java.util.List;


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
        if(data.getCategories().size() > 0) {
            category = data.getCategories().get(0);
        }

        setContentView(createView());
    }

    private View createView() {
        final WordData wordData = new WordData(this, category);

        final MultiSelectGroup categoryNamesGroup =
                new MultiSelectGroup(
                        MultiSelectGroup.Mode.MULTI_SELECT,
                        wordData.getNames(),
                        this);

        categoryNamesGroup.withBottomView(
                new SideBySideButtonsView(
                        this,
                        addWordButton(wordData),
                        removeWordButton(wordData, categoryNamesGroup),
                        gotoWordActivityButton(categoryNamesGroup))
                        .createView());

        return categoryNamesGroup.createView();
    }

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

    private Button gotoWordActivityButton(final MultiSelectGroup multiSelectGroup) {
        return new GotoAnotherActivityButton(
                this,
                "Go!",
                WordScreen.class,
                new DataProvider<Transferable<?>>() {
                    @Override
                    public Transferable<?> getData() {
                        CategoryToWordScreen data = new CategoryToWordScreen();
                        data.setWords(multiSelectGroup.getSelected());
                        return data;
                    }
                });
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
}
