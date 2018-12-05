package com.prowidgetstudio.gitstatsmvp.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Dzano on 17.11.2018.
 */

public class LogoCustomView extends View {

    Paint paint = null;
    int x,y;

    public LogoCustomView(Context context) {
        super(context);

    }

    public LogoCustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        x = getWidth();
        y = getHeight();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(0, 0, (x*2)/10, y, paint);
        canvas.drawRect((x*4)/10, y/4, (x*6)/10, y, paint);
        canvas.drawRect((x*8)/10, y/2, x, y, paint);

    }
}
