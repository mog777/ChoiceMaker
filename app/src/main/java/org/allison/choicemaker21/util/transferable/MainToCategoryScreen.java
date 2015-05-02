package org.allison.choicemaker21.util.transferable;

import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class MainToCategoryScreen extends JacksonTransfer<MainToCategoryScreen> {

    private List<String> categories;

    public MainToCategoryScreen() {
        super(MainToCategoryScreen.class);
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
