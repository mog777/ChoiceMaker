package org.allison.choicemaker21.util.buttons;

import android.content.Context;
import android.widget.Button;

import org.allison.choicemaker21.util.MediaSelectorDialog;
import org.allison.choicemaker21.util.provider.StringProvider;

/**
 * Created by Allison on 5/2/2015.
 */
public class MediaSelectorButton extends Button {

    public MediaSelectorButton(
            Context context,
            String buttonText,
            StringProvider intentData) {
        super(context);

        this.setText(buttonText);

        this.setOnClickListener(new MediaSelectorDialog(context, intentData));
    }
}
