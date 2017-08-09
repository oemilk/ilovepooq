package com.sh.ilovepooq.main.repository;

import com.sh.ilovepooq.main.MainGridContract;
import com.sh.ilovepooq.main.MainListContract;
import com.sh.ilovepooq.remote.HTMLParser;
import com.sh.ilovepooq.model.ContentInfoModel;

import java.util.List;

import io.reactivex.Single;

public class MainRepository implements MainGridContract.Repository, MainListContract.Repository {

    private HTMLParser parser;

    public MainRepository(HTMLParser parser) {
        this.parser = parser;
    }

    @Override
    public Single<List<ContentInfoModel>> parsing() {
        return parser.getContentInfoModelArrayList();
    }

}
