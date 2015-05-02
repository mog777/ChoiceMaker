package org.allison.choicemaker21.util.callback;

/**
 * Created by Allison on 5/2/2015.
 */
public abstract class VoidCallback implements Callback<Void> {
    @Override
    public Class<Void> getType() {
        return Void.class;
    }
}
