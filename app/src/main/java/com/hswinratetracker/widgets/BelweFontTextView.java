package com.hswinratetracker.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hswinratetracker.fonts.OPTIBelweMedium;

public class BelweFontTextView extends TextView {

    public BelweFontTextView(Context context) {
        super(context);
        setTypeface(OPTIBelweMedium.getInstance(context).getTypeFace());
    }

    public BelweFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(OPTIBelweMedium.getInstance(context).getTypeFace());
    }

    public BelweFontTextView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(OPTIBelweMedium.getInstance(context).getTypeFace());
    }

}
