package org.allison.choicemaker21.util.buttons;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Allison on 5/2/2015.
 */
public class GotoAnotherActivityButton extends Button {

    public GotoAnotherActivityButton(
            Context context,
            String buttonText) {
        super(context);

        this.setClickable(true);
        this.setText(buttonText);
        //this.setOnClickListener(  new OnClickUserInput(context, confirmationTitle, callback));
    }
}
