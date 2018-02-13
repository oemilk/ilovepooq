package com.sh.ilovepooq.di.search;

import com.sh.ilovepooq.di.base.FragmentScope;
import com.sh.ilovepooq.search.view.SearchFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {SearchModule.class})
public interface SearchComponent {

    void inject(SearchFragment fragment);

}
