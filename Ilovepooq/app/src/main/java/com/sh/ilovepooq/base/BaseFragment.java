package com.sh.ilovepooq.base;

import android.widget.Toast;

import com.sh.ilovepooq.di.scopes.FragmentScope;

import dagger.android.DaggerFragment;

@FragmentScope
public class BaseFragment extends DaggerFragment {

    public void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int id) {
        Toast.makeText(getActivity(), getString(id), Toast.LENGTH_SHORT).show();
    }

}
