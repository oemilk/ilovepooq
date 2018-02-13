package com.sh.ilovepooq.di.main;

import com.sh.ilovepooq.di.base.FragmentScope;
import com.sh.ilovepooq.main.view.MainGridFragment;
import com.sh.ilovepooq.main.view.MainListFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {

    void inject(MainGridFragment fragment);

    void inject(MainListFragment fragment);

}
