package com.example.yuanyc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yuanyc.volumedmeo.R;

/**
 * Created by yuanyc on 2016/2/23.
 * 自定义的组合控件
 */
public class MyLayoutView extends LinearLayout {
    private MyVolumeView myVolumeView;
    private ImageView icon;

    public MyLayoutView(Context context) {
        this(context, null);
    }

    public MyLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_layout_view, this);
        myVolumeView = (MyVolumeView) view.findViewById(R.id.volume);
        icon = (ImageView) view.findViewById(R.id.img_volume);
    }

    /**
     * 设置图片
     *
     * @param resId
     */
    public void setIcon(int resId) {
        icon.setImageResource(resId);
    }

    /**
     * 增加音量
     */
    public void addVolume() {
        myVolumeView.addVolume();
    }

    /**
     * 减少音量
     */
    public void lowVolume() {
        myVolumeView.lowVolume();
    }
}
