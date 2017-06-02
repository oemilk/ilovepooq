package com.sh.ilovepooq.MVP.dagger;

import com.sh.ilovepooq.MVP.AdapterMVP;
import com.sh.ilovepooq.MVP.MVP;
import com.sh.ilovepooq.MVP.model.MVPModel;
import com.sh.ilovepooq.MVP.presenter.MVPPresenter;
import com.sh.ilovepooq.MVP.view.RecyclerViewAdapter;

import dagger.Provides;

@dagger.Module
public class Module {

    private RecyclerViewAdapter recyclerViewAdapter;
    private MVP.View view;

    public Module(RecyclerViewAdapter recyclerViewAdapter, MVP.View view) {
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.view = view;
    }

    @Provides
    MVP.Presenter providePresenter(MVP.Model model, MVP.View view, AdapterMVP.Model adapterModel) {
        return new MVPPresenter(model, view, adapterModel);
    }

    @Provides
    MVP.Model provideModel() {
        return new MVPModel();
    }

    @Provides
    MVP.View provideView() {
        return view;
    }

    @Provides
    RecyclerViewAdapter provideRecyclerViewAdapter() {
        return recyclerViewAdapter;
    }

    @Provides
    AdapterMVP.View provideAdapterView() {
        return recyclerViewAdapter;
    }

    @Provides
    AdapterMVP.Model provideAdapterModel() {
        return recyclerViewAdapter;
    }

}
