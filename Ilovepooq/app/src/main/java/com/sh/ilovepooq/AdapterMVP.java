package com.sh.ilovepooq;

import com.sh.ilovepooq.model.ContentInfoModel;

import java.util.List;

public interface AdapterMVP {

    interface View {
        void refresh();
    }

    interface Model {

        void addAll(List<ContentInfoModel> modelList);

    }

}