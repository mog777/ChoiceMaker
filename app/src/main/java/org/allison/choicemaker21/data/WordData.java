package org.allison.choicemaker21.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.BaseColumns;

import org.allison.choicemaker21.data.sql.DatabaseHelper;
import org.allison.choicemaker21.data.sql.WordTableMetadata;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class WordData {
    private static final String PREFS_PREFIX = "WORD_DATA_";
    private static final String NAMES_KEY = "names";

    private final List<String> names = new ArrayList<>();
    private final String categoryName;

    private final Context context;

    private final DatabaseHelper dbHelper;

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }

    public WordData(Context context, String categoryName, boolean load) {
        this.dbHelper = new DatabaseHelper(context);

        this.context = context;
        this.categoryName = categoryName;
        if (load) {
            load();
        }
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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] args = {};
        Cursor cursor = db.rawQuery("SELECT * FROM " + WordTableMetadata.TABLE_NAME, args);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name =
                        cursor.getString(
                                cursor.getColumnIndex(WordTableMetadata.WORD_NAME_COLUMN));
                names.add(name);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
    }

    public List<String> getNames() {
        return new ArrayList(names);
    }

    public void addName(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WordTableMetadata.WORD_NAME_COLUMN, name);
        values.put(WordTableMetadata.CATEGORY_NAME_COLUMN, categoryName);
        db.insert(
                WordTableMetadata.TABLE_NAME,
                null,
                values);
        names.add(name);
    }

    public void removeNames(Collection<String> removeNames) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (String name : removeNames) {
            String selection = WordTableMetadata.CATEGORY_NAME_COLUMN + " = ? AND " +
                    WordTableMetadata.WORD_NAME_COLUMN + " = ?";
            String[] args = {categoryName, name};
            db.delete(
                    WordTableMetadata.TABLE_NAME,
                    selection,
                    args);
        }
        names.removeAll(removeNames);
    }

    public void removeName(String name) {
        this.removeNames(Collections.singleton(name));
    }

    public void addThumbnail(String name, Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(WordTableMetadata.THUMBNAIL_IMG_COLUMN, byteArray);

        String selection = WordTableMetadata.CATEGORY_NAME_COLUMN + " = ? AND " +
                WordTableMetadata.WORD_NAME_COLUMN + " = ?";
        String[] args = {categoryName, name};

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.update(
                WordTableMetadata.TABLE_NAME,
                values,
                selection,
                args);

    }

    public byte[] getThumbnail(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] args = {categoryName, name};
        Cursor cursor = db.rawQuery(
                String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?",
                        WordTableMetadata.THUMBNAIL_IMG_COLUMN,
                        WordTableMetadata.TABLE_NAME,
                        WordTableMetadata.CATEGORY_NAME_COLUMN,
                        WordTableMetadata.WORD_NAME_COLUMN
                ),
                args);
        try {
            byte[] ret = null;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ret = cursor.getBlob(cursor.getColumnIndex(WordTableMetadata.THUMBNAIL_IMG_COLUMN));
                names.add(name);
                cursor.moveToNext();
            }
            return ret;
        } finally {
            cursor.close();
        }
    }
}
