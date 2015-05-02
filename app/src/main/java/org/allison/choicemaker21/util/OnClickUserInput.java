package org.allison.choicemaker21.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Allison on 5/2/2015.
 */
public abstract class OnClickUserInput implements View.OnClickListener {

    private final Context context;
    private final StringProvider title;

    public OnClickUserInput(
            Context context,
            StringProvider title ) {
        this.context = context;
        this.title = title;
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        final EditText input = new EditText(context);

        alertDialogBuilder
                .setTitle(title.getString())
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setView(input);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                onInput(value);
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

    public abstract void onInput(String input);
}
