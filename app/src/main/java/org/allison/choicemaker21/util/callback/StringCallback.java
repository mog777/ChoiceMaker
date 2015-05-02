package org.allison.choicemaker21.util.callback;

/**
 * Created by Allison on 5/2/2015.
 */
public abstract class StringCallback implements Callback<String> {

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
