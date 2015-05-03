package org.allison.choicemaker21.util.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.allison.choicemaker21.util.callback.VoidCallback;
import org.allison.choicemaker21.util.provider.BitmapProvider;
import org.allison.choicemaker21.util.provider.DataProvider;
import org.allison.choicemaker21.util.provider.StringProvider;

import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class FillScreenColumns<T> {

    private View createdView;

    private final Context context;
    private final List<Pair<DataProvider, VoidCallback>> dataProviders;

    public FillScreenColumns(
            Context context,
            List<Pair<DataProvider, VoidCallback>> dataProviders) {
        this.context = context;
        this.dataProviders = dataProviders;
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

        for (final Pair<DataProvider, VoidCallback> pair : dataProviders) {
            DataProvider dp = pair.first;
            VoidCallback cb = pair.second;

            View v = null;
            if (dp instanceof StringProvider) {
                v = createStringButton(((StringProvider) dp).getData(), cb);
            } else if (dp instanceof BitmapProvider) {
                v = createImageButton(((BitmapProvider) dp).getData(), cb);
            } else {
                // TODO log error
            }

            if (v != null) {
                layout.addView(v);
            }
        }

        createdView = layout;
        return createdView;
    }

    private Button createStringButton(final String text, final VoidCallback vc) {
        Button button = new Button(context);
        button.setText(text);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vc.call(null);
                //callback.call(name);
            }
        });
        button.setLayoutParams(p);
        return button;
    }

    private ImageButton createImageButton(final Bitmap bmp, final VoidCallback vc) {
        ImageButton button = new ImageButton(context);
        button.setImageBitmap(bmp);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vc.call(null);
                //callback.call(name);
            }
        });
        button.setLayoutParams(p);
        return button;
    }
}
