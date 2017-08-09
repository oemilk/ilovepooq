package com.sh.ilovepooq.dagger.main;

import com.sh.ilovepooq.main.view.MainGridFragment;
import com.sh.ilovepooq.main.view.MainListFragment;

import dagger.Subcomponent;

@MainScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {

    void inject(MainGridFragment fragment);

    void inject(MainListFragment fragment);

}
