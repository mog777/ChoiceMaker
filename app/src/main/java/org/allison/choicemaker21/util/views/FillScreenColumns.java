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

import org.allison.choicemaker21.util.callback.Callback;

import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class FillScreenColumns<T> {

    private View createdView;

    private final List<String> names;
    private final Context context;
    private final Callback<T> callback;

    public FillScreenColumns(
            List<String> names,
            Context context,
            Callback<T> callback) {
        this.names = names;
        this.context = context;
        this.callback = callback;
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

        for(final String word : names) {
            Button button = new Button(context);
            button.setText(word);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f);
            if(callback.getType().isAssignableFrom(String.class)) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.call((T) word);
                    }
                });

            }
            button.setLayoutParams(p);
            layout.addView(button);
        }

        createdView = layout;
        return createdView;
    }
}
