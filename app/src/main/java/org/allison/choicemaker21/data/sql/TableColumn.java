package org.allison.choicemaker21.data.sql;

/**
 * Created by Allison on 5/2/2015.
 */
public interface TableColumn {

    public enum Type {
        INTEGER,
        REAL,
        TEXT,
        BLOB
    }

    String getColumnName();

    Type getColumnType();

    int sinceVersion();

}
