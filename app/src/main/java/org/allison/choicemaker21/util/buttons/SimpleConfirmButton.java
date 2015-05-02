package org.allison.choicemaker21.util.buttons;

import android.content.Context;
import android.widget.Button;

import org.allison.choicemaker21.util.SimpleDialogBox;
import org.allison.choicemaker21.util.callback.Callback;
import org.allison.choicemaker21.util.provider.StringProvider;

/**
 * Created by Allison on 5/2/2015.
 */
public class SimpleConfirmButton<T> extends Button {
    public SimpleConfirmButton(
            Context context,
            String buttonText,
            final StringProvider confirmationTitle,
            final Callback<T> callback) {

        super(context);

        this.setClickable(true);
        this.setText(buttonText);
        this.setOnClickListener(
                new SimpleDialogBox(context, confirmationTitle, callback));
    }
}
