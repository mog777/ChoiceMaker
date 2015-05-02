package org.allison.choicemaker21.util;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Allison on 5/2/2015.
 */
public class SimpleTextInputButton<T> extends Button {

    private final OnInput onInput;

    public SimpleTextInputButton(
            Context context,
            String buttonText,
            StringProvider confirmationTitle,
            final OnInput onInput) {
        super(context);
        this.onInput = onInput;

        this.setClickable(true);
        this.setText(buttonText);
        this.setOnClickListener(
                new OnClickUserInput(context, confirmationTitle) {
                    @Override
                    public void onInput(String input) {
                        onInput.onInput(input);
                    }
                }
        );
    }
}
