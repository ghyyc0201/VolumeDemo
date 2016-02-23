package com.example.yuanyc.volumedmeo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yuanyc.view.MyLayoutView;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button add;
    private Button low;
    private MyLayoutView myLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button) findViewById(R.id.add);
        low = (Button) findViewById(R.id.low);
        myLayoutView = (MyLayoutView) findViewById(R.id.volumeView);
        add.setOnClickListener(this);
        low.setOnClickListener(this);
        myLayoutView.setIcon(R.mipmap.icon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                myLayoutView.addVolume();
                break;
            case R.id.low:
                myLayoutView.lowVolume();
                break;
        }
    }
}
