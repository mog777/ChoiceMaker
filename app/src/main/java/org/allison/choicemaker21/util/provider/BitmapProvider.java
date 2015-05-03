package org.allison.choicemaker21.util.provider;

import android.graphics.Bitmap;

/**
 * Created by Allison on 5/3/2015.
 */
public class BitmapProvider implements DataProvider<Bitmap> {
    private final Bitmap bitmap;

    public BitmapProvider(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public Bitmap getData() {
        return bitmap;
    }
}
