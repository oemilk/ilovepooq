package com.sh.ilovepooq.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResouce());
    }

    @LayoutRes
    protected abstract int getContentViewResouce();

    protected void showToast(@NonNull String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int id) {
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show();
    }

}
