package org.allison.choicemaker21.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.allison.choicemaker21.data.sql.CategoryTableMetadata;
import org.allison.choicemaker21.data.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class CategoryData {

    private final List<String> names = new ArrayList<>();

    private final DatabaseHelper dbHelper;

    public CategoryData(Context context) {
        dbHelper = new DatabaseHelper(context);
        load();
    }

    public void load() {
        names.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] args = {};
        Cursor cursor = db.rawQuery("SELECT * FROM " + CategoryTableMetadata.TABLE_NAME, args);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name =
                        cursor.getString(
                                cursor.getColumnIndex(CategoryTableMetadata.CATEGORY_NAME_COLUMN));
                names.add(name);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
    }

    public List<String> getNames() {
        return new ArrayList<>(names);
    }

    public void addName(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryTableMetadata.CATEGORY_NAME_COLUMN, name);
        db.insert(
                CategoryTableMetadata.TABLE_NAME,
                null,
                values);
        names.add(name);
    }

    public void removeNames(Collection<String> removeNames) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (String name : removeNames) {
            String selection = CategoryTableMetadata.CATEGORY_NAME_COLUMN + " = ?";
            String[] args = {name};
            db.delete(
                    CategoryTableMetadata.TABLE_NAME,
                    selection,
                    args);
        }
        names.removeAll(removeNames);
    }

    public void removeName(String name) {
        this.removeNames(Collections.singleton(name));
    }
}
