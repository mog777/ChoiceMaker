package org.allison.choicemaker21;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.allison.choicemaker21.data.CategoryData;
import org.allison.choicemaker21.util.OnConfirm;
import org.allison.choicemaker21.util.OnInput;
import org.allison.choicemaker21.util.SideBySideButtonsView;
import org.allison.choicemaker21.util.SimpleConfirmButton;
import org.allison.choicemaker21.util.SimpleTextInputButton;
import org.allison.choicemaker21.util.StaticStringProvider;
import org.allison.choicemaker21.view.MultiSelectGroup;


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
                        new SimpleTextInputButton(
                                this,
                                "Add Category",
                                new StaticStringProvider("Add Category Title"),
                                new OnInput() {
                                    @Override
                                    public void onInput(String input) {
                                        categoryData.addName(input);
                                        setContentView(createView());
                                    }
                                }),
                        new SimpleConfirmButton(
                                this,
                                "Delete",
                                new StaticStringProvider("Delete Category Title"),
                                new OnConfirm()
                                {
                                    @Override
                                    public void confirmed() {
                                        categoryData.removeName("");
                                        setContentView(createView());
                                    }
                                }
                        )
                ).createView());
        /*
        categoryNamesGroup.withBottomView(
                new SimpleTextInputButton(this, "Add Category",
                        new OnInput() {
                            @Override
                            public void onInput(String input) {
                                categoryData.addName(input);
                                setContentView(createView());
                            }
                        }));
                        */

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
