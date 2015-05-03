package org.allison.choicemaker21.data.sql;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Allison on 5/2/2015.
 */
public class WordTableMetadata implements DatabaseTableMetadata {

    public static final String TABLE_NAME = "WORD";

    public static final String WORD_NAME_COLUMN = "word_name";

    public static final String CATEGORY_NAME_COLUMN = "category_name";

    public static final String THUMBNAIL_IMG_COLUMN = "thumbnail_img";

    public static final String RECORDED_AUDIO_FILE_COLUMN = "recorded_audio_file";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public List<TableColumn> getColumns() {
        return Arrays.asList(
                Columns.create(CATEGORY_NAME_COLUMN, TableColumn.Type.TEXT, 2),
                Columns.create(WORD_NAME_COLUMN, TableColumn.Type.TEXT, 2),
                Columns.create(THUMBNAIL_IMG_COLUMN, TableColumn.Type.BLOB, 3),
                Columns.create(RECORDED_AUDIO_FILE_COLUMN, TableColumn.Type.TEXT, 4)
        );
    }

    @Override
    public int sinceVersion() {
        return 2;
    }
}
