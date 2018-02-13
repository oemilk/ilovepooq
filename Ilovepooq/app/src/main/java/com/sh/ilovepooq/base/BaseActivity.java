package com.sh.ilovepooq.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.di.base.AppComponent;

public class BaseActivity extends AppCompatActivity {

    private AppComponent appComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        appComponent = ((App) getApplication()).getAppComponent();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int id) {
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show();
    }

}
