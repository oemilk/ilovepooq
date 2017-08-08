package com.sh.ilovepooq;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sh.ilovepooq.MVC.view.MVCMainActivity;
import com.sh.ilovepooq.MVP.view.MVPMainActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button mvcButton;
    private Button mvpButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        mvcButton = (Button) findViewById(R.id.mvc_button);
        mvpButton = (Button) findViewById(R.id.mvp_button);

        mvcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MVCMainActivity.class);
                startActivity(intent);
            }
        });

        mvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MVPMainActivity.class);
                startActivity(intent);
            }
        });
    }

}
