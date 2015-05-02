package org.allison.choicemaker21;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.allison.choicemaker21.data.CategoryData;
import org.allison.choicemaker21.view.MultiSelectGroup;

import java.util.Arrays;


public class MainScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        CategoryData categoryData = new CategoryData(this);

        MultiSelectGroup categoryNamesGroup = new MultiSelectGroup(Arrays.asList("foo", "bar"), this);
        categoryNamesGroup.withButtonAtBottom("add category");

        //getSupportActionBar().

        setContentView(categoryNamesGroup.createView());
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
