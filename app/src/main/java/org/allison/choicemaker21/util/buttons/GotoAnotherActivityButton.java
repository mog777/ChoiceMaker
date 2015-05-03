package org.allison.choicemaker21.util.buttons;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.allison.choicemaker21.util.IntentKeys;
import org.allison.choicemaker21.util.callback.Predicate;
import org.allison.choicemaker21.util.provider.DataProvider;
import org.allison.choicemaker21.util.transferable.Transferable;

/**
 * Created by Allison on 5/2/2015.
 */
public class GotoAnotherActivityButton extends Button {

    public GotoAnotherActivityButton(
            final Context context,
            String buttonText,
            final Class<?> activityClass,
            final DataProvider<Transferable<?>> data,
            final Predicate<View> noopFilter
            ) {
        super(context);

        this.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0.1f));
        this.setClickable(true);
        this.setText(buttonText);
        this.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!noopFilter.success(v)) {
                            return;
                        }
                        Intent intent = new Intent(context, activityClass);
                        intent.putExtra(IntentKeys.SERIALIZED_DATA, data.getData().serialize());
                        context.startActivity(intent);
                    }
                });
    }
}
