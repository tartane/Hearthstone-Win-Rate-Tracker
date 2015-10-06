package com.hswinratetracker.fonts;

import android.content.Context;
import android.graphics.Typeface;

public class OPTIBelweMedium {

    private static OPTIBelweMedium instance;
    private static Typeface typeface;

    public static OPTIBelweMedium getInstance(Context context) {
        synchronized (OPTIBelweMedium.class) {
            if (instance == null) {
                instance = new OPTIBelweMedium();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "OPTIBelwe-Medium.otf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}