package org.allison.choicemaker21.util.transferable;

import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class CategoryToWordScreen extends JacksonTransfer<CategoryToWordScreen> {

    List<WordChoice> wordChoices;

    String category;

    public CategoryToWordScreen() {
        super(CategoryToWordScreen.class);
    }

    public List<WordChoice> getWordChoices() {
        return wordChoices;
    }

    public void setWordChoices(List<WordChoice> wordChoices) {
        this.wordChoices = wordChoices;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
