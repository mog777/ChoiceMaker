package org.allison.choicemaker21;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.allison.choicemaker21.data.CategoryData;
import org.allison.choicemaker21.util.callback.Predicate;
import org.allison.choicemaker21.util.views.SideBySideButtonsView;
import org.allison.choicemaker21.util.buttons.GotoAnotherActivityButton;
import org.allison.choicemaker21.util.buttons.SimpleConfirmButton;
import org.allison.choicemaker21.util.callback.StringCallback;
import org.allison.choicemaker21.util.callback.VoidCallback;
import org.allison.choicemaker21.util.provider.DataProvider;
import org.allison.choicemaker21.util.provider.StaticStringProvider;
import org.allison.choicemaker21.util.transferable.MainToCategoryScreen;
import org.allison.choicemaker21.util.transferable.Transferable;
import org.allison.choicemaker21.util.views.MultiSelectGroup;

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
                        MultiSelectGroup.Mode.SINGLE_SELECT,
                        categoryData.getNames(),
                        this);

        categoryNamesGroup.withBottomView(
                new SideBySideButtonsView(
                        this,
                        addCategoryButton(categoryData),
                        removeCategoryButton(categoryData, categoryNamesGroup),
                        gotoCategoryButton(categoryNamesGroup)
                ).createView());

        return categoryNamesGroup.createView();
    }

    @SuppressWarnings("unchecked")
    private Button addCategoryButton(final CategoryData categoryData) {
        return new SimpleConfirmButton(
                this,
                "Add Category",
                new StaticStringProvider("Add Category"),
                new StringCallback() {
                    @Override
                    public void call(String input) {
                        categoryData.addName(input);
                        setContentView(createView());
                    }
                });
    }

    @SuppressWarnings("unchecked")
    private Button removeCategoryButton(
            final CategoryData categoryData,
            final MultiSelectGroup categoryNamesGroup) {
        return new SimpleConfirmButton(
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
        );
    }

    private Button gotoCategoryButton(final MultiSelectGroup multiSelectGroup) {
        return new GotoAnotherActivityButton(
                this,
                "Go!",
                CategoryScreen.class,
                new DataProvider<Transferable<?>>() {
                    @Override
                    public Transferable<?> getData() {
                        MainToCategoryScreen data = new MainToCategoryScreen();
                        data.setCategories(multiSelectGroup.getSelected());
                        return data;
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
