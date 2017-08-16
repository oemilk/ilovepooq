package com.sh.ilovepooq.dagger.search;

import com.sh.ilovepooq.search.view.SearchFragment;

import dagger.Subcomponent;

@SearchScope
@Subcomponent(modules = {SearchModule.class})
public interface SearchComponent {

    void inject(SearchFragment fragment);

}
