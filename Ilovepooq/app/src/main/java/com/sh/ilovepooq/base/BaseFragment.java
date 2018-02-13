package com.sh.ilovepooq.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.sh.ilovepooq.di.scopes.FragmentScope;

import butterknife.Unbinder;
import dagger.android.DaggerFragment;

@FragmentScope
public class BaseFragment extends DaggerFragment {

    private Unbinder unbinder;

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    protected void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    protected void showToast(@NonNull String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int id) {
        Toast.makeText(getActivity(), getString(id), Toast.LENGTH_SHORT).show();
    }

}
