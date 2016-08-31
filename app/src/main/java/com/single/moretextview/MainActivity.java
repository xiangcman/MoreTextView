package com.single.moretextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.library.moretextview.MoreTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoreTextView text1 = ((MoreTextView) findViewById(R.id.more));
        text1.setText(getResources().getString(R.string.text));
        text1.refreshText();

        MoreTextView text2 = ((MoreTextView) findViewById(R.id.short_text));
        text2.setText(getResources().getString(R.string.text_short));
        text2.refreshText();
    }
}
