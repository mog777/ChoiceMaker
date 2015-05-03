package org.allison.choicemaker21.util.views;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import org.allison.choicemaker21.util.callback.Callback;

import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class GridView<T> {

    private View createdView;

    private final List<String> names;
    private final Context context;
    private final Callback<T> callback;

    public GridView(
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

        GridLayout layout = new GridLayout(context);
        layout.setRowCount(2);
        layout.setColumnCount(2);

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
