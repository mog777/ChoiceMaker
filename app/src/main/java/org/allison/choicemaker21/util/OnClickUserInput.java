package org.allison.choicemaker21.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import org.allison.choicemaker21.util.callback.Callback;
import org.allison.choicemaker21.util.provider.StringProvider;

/**
 * Created by Allison on 5/2/2015.
 */
public class OnClickUserInput<T> implements View.OnClickListener {

    private final Context context;
    private final StringProvider title;
    private Callback<T> callback;

    public OnClickUserInput(
            Context context,
            StringProvider title,
            Callback<T> callback) {
        this.context = context;
        this.title = title;
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setTitle(title.getData())
                .setMessage("Click yes to exit!")
                .setCancelable(false);

        final boolean isTextInput = callback.getType().isAssignableFrom(String.class);
        final EditText input =  isTextInput ? new EditText(context) : null;
        if(input != null) {
            alertDialogBuilder.setView(input);
        }

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(input != null) {
                    String value = input.getText().toString();
                    callback.call((T) value);
                } else {
                    callback.call(null);
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
