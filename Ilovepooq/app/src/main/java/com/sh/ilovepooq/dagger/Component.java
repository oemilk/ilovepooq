package com.sh.ilovepooq.dagger;

import com.sh.ilovepooq.MainActivity;

@dagger.Component(modules = Module.class)
public interface Component {

    void inject(MainActivity activity);

}
