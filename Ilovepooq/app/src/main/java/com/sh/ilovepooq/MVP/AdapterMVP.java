package com.sh.ilovepooq.MVP;

import com.sh.ilovepooq.MVP.model.ContentInfoModel;

import java.util.List;

public interface AdapterMVP {

    interface View {
        void refresh();
    }

    interface Model {

        void addAll(List<ContentInfoModel> modelList);

    }

}