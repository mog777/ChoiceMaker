package org.allison.choicemaker21.data.sql;

import android.provider.BaseColumns;

import org.allison.choicemaker21.data.sql.TableColumn;

import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public interface DatabaseTableMetadata extends BaseColumns {

    public String getTableName();

    List<TableColumn> getColumns();

    int sinceVersion();

}
