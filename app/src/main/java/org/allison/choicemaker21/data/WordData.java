package org.allison.choicemaker21.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Allison on 5/2/2015.
 */
public class WordData {
    private static final String PREFS_PREFIX = "WORD_DATA_";
    private static final String NAMES_KEY = "names";

    private final List<String> names = new ArrayList<>();
    private final String categoryName;

    private final Context context;

    public WordData(
            Context context, String categoryName) {
        this.context = context;
        this.categoryName = categoryName;
        load();
    }

    private SharedPreferences getPreferences() {
        try {
            return context.getSharedPreferences(PREFS_PREFIX + URLEncoder.encode(categoryName, "UTF-8"), 0);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        names.clear();
        SharedPreferences sharedPref = getPreferences();
        Set<String> categories = sharedPref.getStringSet(NAMES_KEY, Collections.<String>emptySet());
        names.addAll(categories);
    }

    public List<String> getNames() {
        return new ArrayList(names);
    }

    public void addName(String name) {
        names.add(name);
        SharedPreferences sharedPref = getPreferences();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(NAMES_KEY, new HashSet<String>(names));
        editor.commit();
    }

    public void removeNames(Collection<String> removeNames) {
        names.removeAll(removeNames);
        SharedPreferences sharedPref = getPreferences();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(NAMES_KEY, new HashSet<String>(names));
        editor.commit();
    }

    public void removeName(String name) {
        this.removeNames(Collections.singleton(name));
    }
}
