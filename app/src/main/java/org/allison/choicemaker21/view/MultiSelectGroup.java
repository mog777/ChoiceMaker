package org.allison.choicemaker21.view;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class MultiSelectGroup {

    private final List<String> names;
    private final Context context;
    private View bottomView;

    private ListView listView;
    private View createdView;

    public MultiSelectGroup(List<String> names, Context context) {
        this.names = names;
        this.context = context;


    }

    public MultiSelectGroup withBottomView(View view) {
        this.bottomView = view;
        return this;
    }

    public View createView() {
        if (createdView != null) {
            return createdView;
        }
        LinearLayout layout = new LinearLayout(context);
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            layout.setLayoutParams(params);
            layout.setOrientation(LinearLayout.VERTICAL);
        }

        {
            listView = new ListView(context);
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

        if (bottomView != null) {
            addBottomView(layout);
        }

        createdView = layout;
        return createdView;
    }

    private void addBottomView(LinearLayout layout) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.0f);
        bottomView.setLayoutParams(params);
        layout.addView(bottomView);
    }

    public List<String> getSelected() {
        final List<String> ret = new ArrayList<>();
        int len = listView.getCount();
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        for (int i = 0; i < listView.getCount(); i++) {
            if (checked.get(i)) {
                String item = names.get(i);
                ret.add(item);
            }
        }
        return ret;
    }

}
