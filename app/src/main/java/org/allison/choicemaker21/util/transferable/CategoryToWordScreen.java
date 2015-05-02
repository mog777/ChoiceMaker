package org.allison.choicemaker21.util.transferable;

import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class CategoryToWordScreen extends JacksonTransfer<CategoryToWordScreen> {

    List<String> words;

    public CategoryToWordScreen() {
        super(CategoryToWordScreen.class);
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
