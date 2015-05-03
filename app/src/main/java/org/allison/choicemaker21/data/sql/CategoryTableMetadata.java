package org.allison.choicemaker21.data.sql;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class CategoryTableMetadata implements DatabaseTableMetadata {

    public static final String TABLE_NAME = "CATEGORY";

    public static final String CATEGORY_NAME_COLUMN = "category_name";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public List<TableColumn> getColumns() {
        return Arrays.asList(
                Columns.create(CATEGORY_NAME_COLUMN, TableColumn.Type.TEXT, 2)
        );
    }

    @Override
    public int sinceVersion() {
        return 2;
    }
}
