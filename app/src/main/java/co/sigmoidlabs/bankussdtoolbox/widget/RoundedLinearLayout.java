package co.sigmoidlabs.bankussdtoolbox.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import co.sigmoidlabs.bankussdtoolbox.R;

public class RoundedLinearLayout extends ForegroundLinearLayout {

    private int mCornerRadius;
    private Path mPath;
    private RectF mRect;

    public RoundedLinearLayout(Context context) {
        this(context, null);
    }

    public RoundedLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mPath = new Path();
        mRect = new RectF();
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedView, defStyle, 0);

            mCornerRadius = a.getDimensionPixelOffset(R.styleable.RoundedView_radius, 0);
            a.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPath.reset();
        mRect.set(0, 0, w, h);
        mPath.addRoundRect(mRect, mCornerRadius, mCornerRadius, Path.Direction.CW);
        mPath.close();
    }

    public void setCornerRadius(int radius) {
        mCornerRadius = radius;
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(mPath);
        super.draw(canvas);
        canvas.restore();
    }

//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        int save = canvas.save();
//        canvas.clipPath(mPath);
//        super.dispatchDraw(canvas);
//        canvas.restoreToCount(save);
//    }
}
