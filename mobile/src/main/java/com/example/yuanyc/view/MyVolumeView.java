package com.example.yuanyc.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.yuanyc.volumedmeo.R;

/**
 * 圆形音量控件
 * Created by yuanyc on 2016/2/23.
 */
public class MyVolumeView extends View {
    /**
     * 圆形半径
     */
    private int radius = 0;
    /**
     * 音量边框底色
     */
    private int primaryVolumeColor = 0;
    /**
     * 音量边框颜色
     */
    private int volumeColor = 0;
    /**
     * 圆形音量背景颜色
     */
    private int backgroundColor = 0;
    /**
     * 音量边框宽度
     */
    private int borderWidth = 0;
    /**
     * 动画百分比
     */
    private int fraction = 0;
    /**
     * 最大音量次数
     */
    private int maxVolume = 15;
    /**
     * 音量每增加一次，对应的角度
     */
    private float angle = 0;
    /**
     * 动画的最大值
     */
    private int maxAnimationValue = 10;
    /**
     * 音量每增加一次的单位角度
     */
    private float unitAngle = 0;
    /**
     * 当前音量的次数
     */
    private int volumeNum = 0;
    /**
     * 是否是加音量
     */
    private boolean iaAddVolume = true;

    private RectF rectF = null;
    private Paint paint = null;

    public MyVolumeView(Context context) {
        this(context,null);
    }

    public MyVolumeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyVolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化自定义属性和画笔
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        //初始化自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VolumeView);
        if (null != typedArray) {
            radius = typedArray.getDimensionPixelSize(R.styleable.VolumeView_radius, 60);
            backgroundColor = typedArray.getColor(R.styleable.VolumeView_backgroundColor, context.getResources().getColor(R.color.colorPrimary));
            volumeColor = typedArray.getColor(R.styleable.VolumeView_volumeColor, Color.WHITE);
            primaryVolumeColor = typedArray.getColor(R.styleable.VolumeView_primaryVolumeColor, context.getResources().getColor(R.color.colorAccent));
            borderWidth = typedArray.getDimensionPixelSize(R.styleable.VolumeView_borderWidth, 8);
            maxVolume = typedArray.getInt(R.styleable.VolumeView_maxVolume, 15);
            //用完之后释放资源
            typedArray.recycle();
        }
        //初始化画笔
        //音量增加一次，获取对应的角度
        angle = 360f / maxVolume;
        //获取一格音量的单位值
        unitAngle = angle / maxAnimationValue;
        //Paint.ANTI_ALIAS_FLAG是使位图抗锯齿的标志
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint = new Paint();
        /*Paint paint = new Paint();
        paint.setAntiAlias(true);          //防锯齿
        paint.setDither(true);            //防抖动
        paint.setStyle(Paint.Style.STROKE);          //画笔类型 STROKE空心 FILL 实心 FILL_AND_STROKE 用契形填充
        paint.setStrokeJoin(Paint.Join.ROUND);      //画笔接洽点类型 如影响矩形但角的外轮廓
        paint.setStrokeCap(Paint.Cap.ROUND);      //画笔笔刷类型 如影响画笔但始末端*/
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //只有圆的半径影响圆的大小，通过直径去测量
        setMeasuredDimension(radius * 2, radius * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景圆形
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        radius = getWidth() / 2;
        canvas.drawCircle(radius, radius, radius, paint);

        //绘制音量轨迹背景
        paint.setAntiAlias(true);
        paint.setColor(primaryVolumeColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        canvas.drawCircle(radius, radius, radius - borderWidth, paint);

        //根据角度绘制音量线圈
        paint.setAntiAlias(true);
        paint.setColor(volumeColor);
        rectF = new RectF(borderWidth, borderWidth, getWidth() - borderWidth, getHeight() - borderWidth);
        if (iaAddVolume) {
            //音量增加时
            canvas.drawArc(rectF, -90, angle * (volumeNum > 0 ? volumeNum - 1 : 0) + unitAngle * fraction, false, paint);
        } else {
            //音量减小时
            canvas.drawArc(rectF, -90, angle * (volumeNum + 1) - unitAngle * fraction, false, paint);
        }
    }


    /**
     * 控制音量增加减少时的动画效果
     */
    private void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, maxAnimationValue);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (int) animation.getAnimatedValue();
                //此方法一旦调用会导致onDraw方法的执行
                invalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 增加音量
     */
    public void addVolume() {
        iaAddVolume = true;
        if (volumeNum < maxVolume) {
            volumeNum++;
            startAnimation();
        }
    }

    /**
     * 减少音量
     */
    public void lowVolume() {
        iaAddVolume = false;
        if (volumeNum > 0) {
            volumeNum--;
            startAnimation();
        }
    }
}
