package org.allison.choicemaker21.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Allison on 5/2/2015.
 */
public class CategoryData {

    private static final String PREFS_NAME = "CATEGORY_DATA";
    private static final String NAMES_KEY = "names";

    private final List<String> names = new ArrayList<>();

    private final Context context;

    public CategoryData(Context context) {
        this.context = context;
        load();
    }

    public void load() {
        names.clear();
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        Set<String> categories = sharedPref.getStringSet(NAMES_KEY, Collections.<String>emptySet());
        names.addAll(categories);
    }

    public List<String> getNames() {
        return names;
    }
}
