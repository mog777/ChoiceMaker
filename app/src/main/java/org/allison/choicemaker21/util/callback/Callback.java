package org.allison.choicemaker21.util.callback;

/**
 * Created by Allison on 5/2/2015.
 */
public interface Callback<T> {

    Class<T> getType();

    void call(T t);
}
