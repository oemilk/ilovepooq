package com.sh.ilovepooq.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

public class BaseFragment extends Fragment {

    public void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int id) {
        Toast.makeText(getContext(), getString(id), Toast.LENGTH_SHORT).show();
    }

}