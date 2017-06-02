package com.sh.ilovepooq.MVP.dagger;

import com.sh.ilovepooq.MVP.view.MVPMainActivity;

@dagger.Component(modules = Module.class)
public interface Component {

    void inject(MVPMainActivity activity);

}
