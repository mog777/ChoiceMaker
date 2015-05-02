package org.allison.choicemaker21.util.transferable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class MainToActivity extends JacksonTransfer<MainToActivity> {

    private List<String> categories;

    public MainToActivity() {
        super(MainToActivity.class);
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
