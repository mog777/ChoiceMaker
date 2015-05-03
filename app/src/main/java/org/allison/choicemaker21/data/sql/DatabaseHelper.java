package org.allison.choicemaker21.data.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static List<DatabaseTableMetadata> TABLES = new ArrayList<>();
    static {
        TABLES.add(new CategoryTableMetadata());
        TABLES.add(new WordTableMetadata());
    }

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "ChoiceMaker.db";

    private static final String COMMA_SEP = ",";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(DatabaseTableMetadata table : TABLES) {
            createTable(db, table);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(DatabaseTableMetadata table : TABLES) {
            if(table.sinceVersion() > oldVersion && table.sinceVersion() <= newVersion) {
                createTable(db, table);
            } else {
                for(TableColumn column : table.getColumns()) {
                    if(column.sinceVersion() > oldVersion && column.sinceVersion() <= newVersion) {
                        addColumn(db, table, column);
                    }
                }
            }
        }
    }

    private void createTable(SQLiteDatabase db, DatabaseTableMetadata table) {
        String sql =
                "CREATE TABLE IF NOT EXISTS " + table.getTableName() + " (" +
                        table._ID + " INTEGER PRIMARY KEY";

        for(TableColumn column : table.getColumns()) {
            sql += ", " + column.getColumnName() + " " + column.getColumnType().name();

        }
        sql += " )";
        db.execSQL(sql);
    }

    private void addColumn(SQLiteDatabase db, DatabaseTableMetadata table, TableColumn column) {
        String sql = String.format(
                "ALTER TABLE %s ADD COLUMN %s %s;",
                table.getTableName(),
                column.getColumnName(),
                column.getColumnType().name());
        db.execSQL(sql);
    }
}
