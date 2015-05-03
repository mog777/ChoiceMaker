package org.allison.choicemaker21.data.sql;

import org.allison.choicemaker21.data.sql.TableColumn;

/**
 * Created by Allison on 5/2/2015.
 */
public class Columns {

    public static TableColumn create(
            final String name,
            final TableColumn.Type type,
            final int sinceVersion) {
        return new TableColumn() {
            @Override
            public String getColumnName() {
                return name;
            }

            @Override
            public Type getColumnType() {
                return type;
            }

            @Override
            public int sinceVersion() {
                return sinceVersion;
            }
        };
    }
}
