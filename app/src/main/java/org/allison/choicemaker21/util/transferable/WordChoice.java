package org.allison.choicemaker21.util.transferable;

import android.graphics.Bitmap;

/**
 * Created by Allison on 5/3/2015.
 */
public class WordChoice {

    private String word;

    private byte[] thumbnail;

    private String audioFile;

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

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }
}
