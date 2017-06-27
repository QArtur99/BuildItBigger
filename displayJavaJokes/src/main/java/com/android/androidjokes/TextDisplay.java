package com.android.androidjokes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by ART_F on 2017-06-23.
 */

public class TextDisplay extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_display);
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(getIntent().getStringExtra("joke"));
    }
}
