package org.allison.choicemaker21.view;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class MultiSelectGroup {

    private final List<String> names;

    private final Context context;

    private String bottomText;

    public MultiSelectGroup(List<String> names, Context context) {
        this.names = names;
        this.context = context;
    }

    public MultiSelectGroup withButtonAtBottom(String text) {
        this.bottomText = text;
        return this;
    }

    public View createView() {
        LinearLayout layout = new LinearLayout(context);
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            layout.setLayoutParams(params);
            layout.setOrientation(LinearLayout.VERTICAL);
        }

        {
            ListView listView = new ListView(context);
            listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(
                            context,
                            android.R.layout.simple_list_item_single_choice, names);
            listView.setAdapter(adapter);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1.0f);
            listView.setLayoutParams(params);

            layout.addView(listView);
        }

        if (bottomText != null) {
            addBottom(layout);
        }

        return layout;
    }

    private void addBottom(LinearLayout layout) {
        Button button = new Button(context);
        button.setClickable(true);
        button.setText(bottomText);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.0f);
        button.setLayoutParams(params);
        layout.addView(button);
    }
}
