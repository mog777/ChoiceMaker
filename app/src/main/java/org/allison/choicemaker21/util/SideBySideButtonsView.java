package org.allison.choicemaker21.util;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class SideBySideButtonsView {

    private final Context context;

    private final List<View> views;

    public SideBySideButtonsView(Context context, View v1, View v2) {
        this.context = context;
        this.views = Arrays.asList(v1, v2);
    }

    public View createView() {

        LinearLayout layout = new LinearLayout(context);
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            layout.setLayoutParams(params);
            layout.setOrientation(LinearLayout.HORIZONTAL);
        }

        for(View v : views) {
            layout.addView(v);
        }

        return layout;
    }
}
