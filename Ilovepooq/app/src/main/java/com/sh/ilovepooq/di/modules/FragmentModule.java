package com.sh.ilovepooq.di.modules;

import com.sh.ilovepooq.di.scopes.FragmentScope;
import com.sh.ilovepooq.main.view.MainGridFragment;
import com.sh.ilovepooq.main.view.MainListFragment;
import com.sh.ilovepooq.search.view.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainListFragment mainListFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainGridFragment mainGridFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchFragment searchFragment();

}
