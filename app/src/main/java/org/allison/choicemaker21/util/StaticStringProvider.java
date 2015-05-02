package org.allison.choicemaker21.util;

/**
 * Created by Allison on 5/2/2015.
 */
public class StaticStringProvider implements StringProvider {

    private final String string;

    public StaticStringProvider(String string) {
        this.string = string;
    }

    @Override
    public String getString() {
        return string;
    }
}
