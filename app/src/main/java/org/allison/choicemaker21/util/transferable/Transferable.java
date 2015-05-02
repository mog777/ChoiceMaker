package org.allison.choicemaker21.util.transferable;

/**
 * Created by Allison on 5/2/2015.
 */
public interface Transferable<T> {

    byte[] serialize();

    T deserialize(byte[] bytes);
}
