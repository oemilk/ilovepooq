package com.sh.ilovepooq.remote;

import com.sh.ilovepooq.base.Constants;
import com.sh.ilovepooq.model.ContentInfoModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import io.reactivex.Single;

public class HTMLParser {

    /**
     * Get the Image's info as ImageInfoModel object.
     *
     * @return an ArrayList of ImageInfoModels.
     */
    public Single<List<ContentInfoModel>> getContentInfoModelArrayList() {
        return getDocument()
                .map(document -> document.select(Constants.PARSE_LI))
                .toObservable()
                .flatMapIterable(elements -> elements)
                .filter(element -> element.childNodeSize() > 1)
                .map(element -> element.childNode(1))
                .filter(node -> node.childNodeSize() > 3)
                .map(
                        node -> {
                            String imageURL = node.childNode(3).attr(Constants.PARSE_SRC);
                            String alt = node.childNode(3).attr(Constants.PARSE_ALT);
                            String title = node.childNode(1).childNode(0).toString();
                            String hyperlink = node.attr(Constants.PARSE_HREF)
                                    .replace(Constants.PARSE_CARRIAGE_RETURN, "")
                                    .replace(Constants.PARSE_LINE_FEED, "")
                                    .replace(Constants.PARSE_TAP, "");
                            return new ContentInfoModel(imageURL, alt, title, hyperlink);
                        }
                )
                .filter(contentInfoModel -> !contentInfoModel.getImageURL().isEmpty() &&
                        !contentInfoModel.getAlt().isEmpty() && !contentInfoModel.getTitle()
                        .isEmpty() && !contentInfoModel.getHyperlink().isEmpty())
                .toList();
    }

    private Single<Document> getDocument() {
        return Single.just(Constants.PARSE_URL)
                .map(s -> Jsoup.connect(s).get());
    }

}
