package org.allison.choicemaker21.util.transferable;

import android.graphics.Bitmap;

/**
 * Created by Allison on 5/3/2015.
 */
public class WordChoice {

    private String word;

    private byte[] thumbnail;

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
