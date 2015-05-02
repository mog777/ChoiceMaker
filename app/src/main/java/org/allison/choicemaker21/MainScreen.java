package org.allison.choicemaker21;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.allison.choicemaker21.data.CategoryData;
import org.allison.choicemaker21.util.OnClickUserInput;
import org.allison.choicemaker21.view.MultiSelectGroup;

import java.util.Arrays;
import java.util.List;


public class MainScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());
    }

    private View createView() {
        final CategoryData categoryData = new CategoryData(this);

        final MultiSelectGroup categoryNamesGroup =
                new MultiSelectGroup(
                        categoryData.getNames(),
                        this);

        categoryNamesGroup.withButtonAtBottom(
                "add category",
                new OnClickUserInput(this) {
                    @Override
                    public void onInput(String input) {
                        categoryData.addName(input);
                        setContentView(createView());
                    }
                }
        );

        return categoryNamesGroup.createView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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
