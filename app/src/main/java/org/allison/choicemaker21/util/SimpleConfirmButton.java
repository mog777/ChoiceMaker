package org.allison.choicemaker21.util;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Allison on 5/2/2015.
 */
public class SimpleConfirmButton<T> extends Button {
    public SimpleConfirmButton(
            Context context,
            String buttonText,
            final StringProvider confirmationTitle,
            final OnConfirm onConfirm) {

        super(context);

        this.setClickable(true);
        this.setText(buttonText);
        this.setOnClickListener(
                new OnClickUserInput(context, confirmationTitle) {
                    @Override
                    public void onInput(String input) {
                        onConfirm.confirmed();
                    }
                }
        );
    }
}
