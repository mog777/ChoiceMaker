package org.allison.choicemaker21.util.views;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Allison on 5/2/2015.
 */
public class MultiSelectGroup {

    public enum Mode {
        SINGLE_SELECT,
        MULTI_SELECT
    }

    private final List<String> names;
    private final Context context;
    private final Mode selectMode;
    private Queue<View> bottomViews = new LinkedList<>();

    private ListView listView;
    private View createdView;

    public MultiSelectGroup(
            Mode mode,
            List<String> names,
            Context context) {
        this.selectMode = mode;
        this.names = names;
        this.context = context;


    }

    public MultiSelectGroup withBottomView(View view) {
        bottomViews.add(view);
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
            switch (selectMode) {
                case SINGLE_SELECT:
                    listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                    break;
                case MULTI_SELECT:
                    listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                    break;
            }
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

        for(View v : bottomViews) {
            addBottomView(layout, v);
        }

        createdView = layout;
        return createdView;
    }

    private void addBottomView(LinearLayout layout, View bottomView) {
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
