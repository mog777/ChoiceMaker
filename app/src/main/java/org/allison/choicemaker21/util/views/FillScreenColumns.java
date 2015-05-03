package org.allison.choicemaker21.util.views;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.allison.choicemaker21.util.callback.Callback;

import java.util.List;
import java.util.Map;

/**
 * Created by Allison on 5/2/2015.
 */
public class FillScreenColumns<T> {

    private View createdView;

    private final Map<String, Object> names;
    private final Context context;
    private final Callback<String> callback;

    public FillScreenColumns(
            Map<String, Object> names,
            Context context,
            Callback<String> callback) {
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

        for (final Map.Entry<String, Object> entry : names.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();

            View v = null;
            if (value == null) {
                v = createStringButton(name, name);
            } else if (value instanceof String) {
                v = createStringButton(name, (String) value);
            } else if(value instanceof Bitmap) {
                v = createImageButton(name, (Bitmap)value);
            }
            if (v != null) {
                layout.addView(v);
            }
        }

        createdView = layout;
        return createdView;
    }

    private Button createStringButton(final String name, final String text) {
        Button button = new Button(context);
        button.setText(text);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f);
        if (callback.getType().isAssignableFrom(String.class)) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.call(name);
                }
            });
        }
        button.setLayoutParams(p);
        return button;
    }

    private ImageButton createImageButton(final String name, final Bitmap bmp) {
        ImageButton button = new ImageButton(context);
        button.setImageBitmap(bmp);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f);
        if (callback.getType().isAssignableFrom(String.class)) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.call(name);
                }
            });
        }
        button.setLayoutParams(p);
        return button;
    }
}
