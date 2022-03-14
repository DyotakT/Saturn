package com.dyotakt.saturn;

import androidx.appcompat.app.AppCompatActivity;

import android.media.session.PlaybackState;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lLayout = findViewById(R.id.linearLayout);
        customViewSaturn saturnView = new customViewSaturn(this);
        lLayout.addView(saturnView);
    }
}