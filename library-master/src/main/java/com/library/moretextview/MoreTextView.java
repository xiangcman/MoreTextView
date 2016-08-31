package com.library.moretextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import toast.share.com.library_master.R;

public class MoreTextView extends LinearLayout {
    private TextView contentView;
    private ImageView expandView;

    private int textColor;
    private float textSize;
    //默认是否是展开的
    private boolean isExpand;
    private int maxLine;

    private int defaultTextColor = Color.BLACK;
    private int defaultTextSize = 18;
    private int defaultLine = 3;

    private Paint paint;

    public MoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initalize();
        initWithAttrs(context, attrs);

    }

    protected void initWithAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MoreTextStyle);
        int textColor = a.getColor(R.styleable.MoreTextStyle_mytextColor,
                defaultTextColor);
        textSize = a.getDimensionPixelSize(R.styleable.MoreTextStyle_mytextSize, defaultTextSize);
        maxLine = a.getInt(R.styleable.MoreTextStyle_maxLine, defaultLine);
        isExpand = a.getBoolean(R.styleable.MoreTextStyle_expand, false);
        bindTextView(textColor, textSize);
        a.recycle();
    }

    protected void initalize() {
        setOrientation(VERTICAL);
        setGravity(Gravity.RIGHT);
        contentView = new TextView(getContext());
        addView(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        expandView = new ImageView(getContext());
        int padding = dip2px(getContext(), 5);
        expandView.setPadding(padding, padding, padding, padding);
        expandView.setImageResource(R.drawable.buttom_smart);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addView(expandView, llp);

    }

    private void initExpand() {
        //只有当测量的行数大于最大的行数的时候
        if (measureTextLength() > maxLine) {
            int height;
            if (isExpand) {
                //展开
                RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(0);
                animation.setFillAfter(true);
                expandView.startAnimation(animation);
            } else {
                height = contentView.getLineHeight() * maxLine;
                contentView.setHeight(height);
            }
        }
    }

    @SuppressLint("NewApi")
    protected void bindTextView(int color, float size) {
        contentView.setTextColor(color);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void refreshText() {
        final float measureTextLength = measureTextLength();
        if (measureTextLength <= maxLine) {
            expandView.setVisibility(View.GONE);
        } else {
            initExpand();
            bindListener();
        }
    }

    /**
     * 判断textview的字数占几行
     *
     * @return
     */
    private float measureTextLength() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setTextSize(contentView.getTextSize());
        float tvW = getContext().getResources().getDisplayMetrics().widthPixels;
        return (paint.measureText(contentView.getText() + "") + 0.5f) / tvW;
    }

    protected void bindListener() {
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                contentView.clearAnimation();
                final int deltaValue;
                final int startValue = contentView.getHeight();
                int durationMillis = 350;
                if (isExpand) {
                    //展开
                    deltaValue = contentView.getLineHeight() * contentView.getLineCount() - startValue;
                    RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    expandView.startAnimation(animation);
                } else {
                    //缩进
                    deltaValue = contentView.getLineHeight() * maxLine - startValue;
                    RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    expandView.startAnimation(animation);
                }
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        contentView.setHeight((int) (startValue + deltaValue * interpolatedTime));
                    }

                };
                animation.setDuration(durationMillis);
                contentView.startAnimation(animation);
            }
        });
    }

    public TextView getTextView() {
        return contentView;
    }

    public void setText(CharSequence charSequence) {
        contentView.setText(charSequence);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
