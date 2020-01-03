package com.xxc.dev.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.xxc.dev.common.R;
import com.xxc.dev.common.utils.DisplayUtils;

/**
 * 圆形的ImageView
 * {@link 'https://github.com/SheHuan/NiceImageView'}
 */
public class RoundImageView extends ImageView {

    private Context mContext;

    private boolean isCircle; // 是否显示为圆形，如果为圆形则设置的corner无效
    private boolean isCoverSrc; // border、inner_border是否覆盖图片
    private int mBorderWidth; // 边框宽度
    private int mBorderColor = Color.WHITE; // 边框颜色
    private int mInnerBorderWidth; // 内层边框宽度
    private int mInnerBorderColor = Color.WHITE; // 内层边框充色

    private int mCornerRadius; // 统一设置圆角半径，优先级高于单独设置每个角的半径
    private int mCornerTopLeftRadius; // 左上角圆角半径
    private int mCornerTopRightRadius; // 右上角圆角半径
    private int mCornerBottomLeftRadius; // 左下角圆角半径
    private int mCornerBottomRightRadius; // 右下角圆角半径

    private int mMaskColor; // 遮罩颜色

    private Xfermode mXfermode;

    private int mWidth;
    private int mHeight;
    private float mRadius;

    private float[] mBorderRadii;
    private float[] mSrcRadii;

    private RectF mSrcRectF; // 图片占的矩形区域
    private RectF mBorderRectF; // 边框的矩形区域

    private Paint mPaint;
    private Path mPath; // 用来裁剪图片的ptah
    private Path mSrcPath; // 图片区域大小的path

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, 0, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.RoundImageView_is_cover_src) {
                isCoverSrc = ta.getBoolean(attr, isCoverSrc);
            } else if (attr == R.styleable.RoundImageView_is_circle) {
                isCircle = ta.getBoolean(attr, isCircle);
            } else if (attr == R.styleable.RoundImageView_border_width) {
                mBorderWidth = ta.getDimensionPixelSize(attr, mBorderWidth);
            } else if (attr == R.styleable.RoundImageView_border_color) {
                mBorderColor = ta.getColor(attr, mBorderColor);
            } else if (attr == R.styleable.RoundImageView_inner_border_width) {
                mInnerBorderWidth = ta.getDimensionPixelSize(attr, mInnerBorderWidth);
            } else if (attr == R.styleable.RoundImageView_inner_border_color) {
                mInnerBorderColor = ta.getColor(attr, mInnerBorderColor);
            } else if (attr == R.styleable.RoundImageView_corner_radius) {
                mCornerRadius = ta.getDimensionPixelSize(attr, mCornerRadius);
            } else if (attr == R.styleable.RoundImageView_corner_top_left_radius) {
                mCornerTopLeftRadius = ta.getDimensionPixelSize(attr, mCornerTopLeftRadius);
            } else if (attr == R.styleable.RoundImageView_corner_top_right_radius) {
                mCornerTopRightRadius = ta.getDimensionPixelSize(attr, mCornerTopRightRadius);
            } else if (attr == R.styleable.RoundImageView_corner_bottom_left_radius) {
                mCornerBottomLeftRadius = ta.getDimensionPixelSize(attr, mCornerBottomLeftRadius);
            } else if (attr == R.styleable.RoundImageView_corner_bottom_right_radius) {
                mCornerBottomRightRadius = ta.getDimensionPixelSize(attr, mCornerBottomRightRadius);
            } else if (attr == R.styleable.RoundImageView_mask_color) {
                mMaskColor = ta.getColor(attr, mMaskColor);
            }
        }
        ta.recycle();

        mBorderRadii = new float[8];
        mSrcRadii = new float[8];

        mBorderRectF = new RectF();
        mSrcRectF = new RectF();

        mPaint = new Paint();
        mPath = new Path();

        if (Build.VERSION.SDK_INT <= VERSION_CODES.O_MR1) {
            mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        } else {
            mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
            mSrcPath = new Path();
        }

        calculateRadii();
        clearInnerBorderWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        initBorderRectF();
        initSrcRectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 使用图形混合模式来显示指定区域的图片
        canvas.saveLayer(mSrcRectF, null, Canvas.ALL_SAVE_FLAG);
        if (!isCoverSrc) {
            float sx = 1.0f * (mWidth - 2 * mBorderWidth - 2 * mInnerBorderWidth) / mWidth;
            float sy = 1.0f * (mHeight - 2 * mBorderWidth - 2 * mInnerBorderWidth) / mHeight;
            // 缩小画布，使图片内容不被borders覆盖
            canvas.scale(sx, sy, mWidth / 2.0f, mHeight / 2.0f);
        }
        super.onDraw(canvas);
        mPaint.reset();
        mPath.reset();
        if (isCircle) {
            mPath.addCircle(mWidth / 2.0f, mHeight / 2.0f, mRadius, Path.Direction.CCW);
        } else {
            mPath.addRoundRect(mSrcRectF, mSrcRadii, Path.Direction.CCW);
        }

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(mXfermode);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            canvas.drawPath(mPath, mPaint);
        } else {
            mSrcPath.addRect(mSrcRectF, Path.Direction.CCW);
            // 计算tempPath和path的差集
            mSrcPath.op(mPath, Path.Op.DIFFERENCE);
            canvas.drawPath(mSrcPath, mPaint);
        }
        mPaint.setXfermode(null);

        // 绘制遮罩
        if (mMaskColor != 0) {
            mPaint.setColor(mMaskColor);
            canvas.drawPath(mPath, mPaint);
        }
        // 恢复画布
        canvas.restore();
        // 绘制边框
        drawBorders(canvas);
    }

    private void drawBorders(Canvas canvas) {
        if (isCircle) {
            if (mBorderWidth > 0) {
                drawCircleBorder(canvas, mBorderWidth, mBorderColor, mRadius - mBorderWidth / 2.0f);
            }
            if (mInnerBorderWidth > 0) {
                drawCircleBorder(canvas, mInnerBorderWidth, mInnerBorderColor, mRadius - mBorderWidth - mInnerBorderWidth / 2.0f);
            }
        } else {
            if (mBorderWidth > 0) {
                drawRectFBorder(canvas, mBorderWidth, mBorderColor, mBorderRectF, mBorderRadii);
            }
        }
    }

    private void drawCircleBorder(Canvas canvas, int borderWidth, int borderColor, float radius) {
        initBorderPaint(borderWidth, borderColor);
        mPath.addCircle(mWidth / 2.0f, mHeight / 2.0f, radius, Path.Direction.CCW);
        canvas.drawPath(mPath, mPaint);
    }

    private void drawRectFBorder(Canvas canvas, int borderWidth, int borderColor, RectF rectF, float[] radii) {
        initBorderPaint(borderWidth, borderColor);
        mPath.addRoundRect(rectF, radii, Path.Direction.CCW);
        canvas.drawPath(mPath, mPaint);
    }

    private void initBorderPaint(int borderWidth, int borderColor) {
        mPath.reset();
        mPaint.setStrokeWidth(borderWidth);
        mPaint.setColor(borderColor);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 计算外边框的RectF
     */
    private void initBorderRectF() {
        if (!isCircle) {
            mBorderRectF.set(mBorderWidth / 2.0f, mBorderWidth / 2.0f, mWidth - mBorderWidth / 2.0f, mHeight - mBorderWidth / 2.0f);
        }
    }

    /**
     * 计算图片原始区域的RectF
     */
    private void initSrcRectF() {
        if (isCircle) {
            mRadius = Math.min(mWidth, mHeight) / 2.0f;
            mSrcRectF.set(mWidth / 2.0f - mRadius, mHeight / 2.0f - mRadius, mWidth / 2.0f + mRadius, mHeight / 2.0f + mRadius);
        } else {
            mSrcRectF.set(0, 0, mWidth, mHeight);
            if (isCoverSrc) {
                mSrcRectF = mBorderRectF;
            }
        }
    }

    /**
     * 计算RectF的圆角半径
     */
    private void calculateRadii() {
        if (isCircle) {
            return;
        }
        if (mCornerRadius > 0) {
            for (int i = 0; i < mBorderRadii.length; i++) {
                mBorderRadii[i] = mCornerRadius;
                mSrcRadii[i] = mCornerRadius - mBorderWidth / 2.0f;
            }
        } else {
            mBorderRadii[0] = mBorderRadii[1] = mCornerTopLeftRadius;
            mBorderRadii[2] = mBorderRadii[3] = mCornerTopRightRadius;
            mBorderRadii[4] = mBorderRadii[5] = mCornerBottomRightRadius;
            mBorderRadii[6] = mBorderRadii[7] = mCornerBottomLeftRadius;

            mSrcRadii[0] = mSrcRadii[1] = mCornerTopLeftRadius - mBorderWidth / 2.0f;
            mSrcRadii[2] = mSrcRadii[3] = mCornerTopRightRadius - mBorderWidth / 2.0f;
            mSrcRadii[4] = mSrcRadii[5] = mCornerBottomRightRadius - mBorderWidth / 2.0f;
            mSrcRadii[6] = mSrcRadii[7] = mCornerBottomLeftRadius - mBorderWidth / 2.0f;
        }
    }

    private void calculateRadiiAndRectF(boolean reset) {
        if (reset) {
            mCornerRadius = 0;
        }
        calculateRadii();
        initBorderRectF();
        invalidate();
    }

    /**
     * 目前圆角矩形情况下不支持inner_border，需要将其置0
     */
    private void clearInnerBorderWidth() {
        if (!isCircle) {
            this.mInnerBorderWidth = 0;
        }
    }

    public void isCoverSrc(boolean isCoverSrc) {
        this.isCoverSrc = isCoverSrc;
        initSrcRectF();
        invalidate();
    }

    public void isCircle(boolean isCircle) {
        this.isCircle = isCircle;
        clearInnerBorderWidth();
        initSrcRectF();
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.mBorderWidth = DisplayUtils.dip2px(mContext, borderWidth);
        calculateRadiiAndRectF(false);
    }

    public void setBorderColor(@ColorInt int borderColor) {
        this.mBorderColor = borderColor;
        invalidate();
    }

    public void setInnerBorderWidth(int innerBorderWidth) {
        this.mInnerBorderWidth = DisplayUtils.dip2px(mContext, innerBorderWidth);
        clearInnerBorderWidth();
        invalidate();
    }

    public void setInnerBorderColor(@ColorInt int innerBorderColor) {
        this.mInnerBorderColor = innerBorderColor;
        invalidate();
    }

    public void setCornerRadius(int cornerRadius) {
        this.mCornerRadius = DisplayUtils.dip2px(mContext, cornerRadius);
        calculateRadiiAndRectF(false);
    }

    public void setCornerTopLeftRadius(int cornerTopLeftRadius) {
        this.mCornerTopLeftRadius = DisplayUtils.dip2px(mContext, cornerTopLeftRadius);
        calculateRadiiAndRectF(true);
    }

    public void setCornerTopRightRadius(int cornerTopRightRadius) {
        this.mCornerTopRightRadius = DisplayUtils.dip2px(mContext, cornerTopRightRadius);
        calculateRadiiAndRectF(true);
    }

    public void setCornerBottomLeftRadius(int cornerBottomLeftRadius) {
        this.mCornerBottomLeftRadius = DisplayUtils.dip2px(mContext, cornerBottomLeftRadius);
        calculateRadiiAndRectF(true);
    }

    public void setCornerBottomRightRadius(int cornerBottomRightRadius) {
        this.mCornerBottomRightRadius = DisplayUtils.dip2px(mContext, cornerBottomRightRadius);
        calculateRadiiAndRectF(true);
    }

    public void setMaskColor(@ColorInt int maskColor) {
        this.mMaskColor = maskColor;
        invalidate();
    }
}
