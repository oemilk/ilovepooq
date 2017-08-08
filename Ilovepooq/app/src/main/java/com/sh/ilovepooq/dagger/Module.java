package com.sh.ilovepooq.dagger;

import com.sh.ilovepooq.AdapterMVP;
import com.sh.ilovepooq.MVP;
import com.sh.ilovepooq.model.MVPModel;
import com.sh.ilovepooq.presenter.MVPPresenter;
import com.sh.ilovepooq.view.RecyclerViewAdapter;

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
