package org.allison.choicemaker21.util.views;

import android.app.ActionBar;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class FillScreenColumns {

    private View createdView;

    private final List<String> names;
    private final Context context;

    public FillScreenColumns(
            List<String> names,
            Context context) {
        this.names = names;
        this.context = context;
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
            layout.setOrientation(LinearLayout.HORIZONTAL);
        }

        for(String word : names) {
            Button button = new Button(context);
            button.setText(word);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f);
            button.setLayoutParams(p);
            layout.addView(button);
        }

        createdView = layout;
        return createdView;
    }
}
