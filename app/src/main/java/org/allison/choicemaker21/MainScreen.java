package org.allison.choicemaker21;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.allison.choicemaker21.data.CategoryData;
import org.allison.choicemaker21.util.SideBySideButtonsView;
import org.allison.choicemaker21.util.buttons.GotoAnotherActivityButton;
import org.allison.choicemaker21.util.buttons.SimpleConfirmButton;
import org.allison.choicemaker21.util.callback.StringCallback;
import org.allison.choicemaker21.util.callback.VoidCallback;
import org.allison.choicemaker21.util.provider.StaticStringProvider;
import org.allison.choicemaker21.view.MultiSelectGroup;

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

        categoryNamesGroup.withBottomView(
                new SideBySideButtonsView(
                        this,
                        new SimpleConfirmButton(
                                this,
                                "Add Category",
                                new StaticStringProvider("Add Category"),
                                new StringCallback() {
                                    @Override
                                    public void call(String input) {
                                        categoryData.addName(input);
                                        setContentView(createView());
                                    }
                                }),
                        new SimpleConfirmButton(
                                this,
                                "Delete Category",
                                new StaticStringProvider("Delete Category"),
                                new VoidCallback() {
                                    @Override
                                    public void call(Void v) {
                                        List<String> selected = categoryNamesGroup.getSelected();
                                        categoryData.removeNames(selected);
                                        setContentView(createView());
                                    }
                                }
                        ),
                        new GotoAnotherActivityButton(this, "Go!")
                ).createView());

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
