package com.sh.ilovepooq.remote;

import com.sh.ilovepooq.base.Constants;
import com.sh.ilovepooq.model.ContentInfoModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class HTMLParser {

    /**
     * Get the Image's info as ImageInfoModel object.
     *
     * @return an ArrayList of ImageInfoModels.
     */
    public Single<List<ContentInfoModel>> getContentInfoModelArrayList() {
        return getDocument()
                .map(new Function<Document, Elements>() {
                    @Override
                    public Elements apply(@NonNull Document document) throws Exception {
                        return document.select(Constants.PARSE_LI);
                    }
                })
                .toObservable()
                .flatMapIterable(new Function<Elements, Iterable<Element>>() {
                    @Override
                    public Iterable<Element> apply(@NonNull Elements elements) throws Exception {
                        return elements;
                    }
                })
                .filter(new Predicate<Element>() {
                    @Override
                    public boolean test(@NonNull Element element) throws Exception {
                        return element.childNodeSize() > 1;
                    }
                })
                .map(new Function<Element, Node>() {
                    @Override
                    public Node apply(@NonNull Element element) throws Exception {
                        return element.childNode(1);
                    }
                })
                .filter(new Predicate<Node>() {
                    @Override
                    public boolean test(@NonNull Node node) throws Exception {
                        return node.childNodeSize() > 3;
                    }
                })
                .map(new Function<Node, ContentInfoModel>() {
                    @Override
                    public ContentInfoModel apply(@NonNull Node node) throws Exception {
                        String imageURL = node.childNode(3).attr(Constants.PARSE_SRC);
                        String alt = node.childNode(3).attr(Constants.PARSE_ALT);
                        String title = node.childNode(1).childNode(0).toString();
                        String hyperlink = node.attr(Constants.PARSE_HREF)
                                .replace(Constants.PARSE_CARRIAGE_RETURN, "")
                                .replace(Constants.PARSE_LINE_FEED, "")
                                .replace(Constants.PARSE_TAP, "");

                        return new ContentInfoModel(imageURL, alt, title, hyperlink);
                    }
                })
                .filter(new Predicate<ContentInfoModel>() {
                    @Override
                    public boolean test(@NonNull ContentInfoModel model) throws Exception {
                        return !model.getImageURL().isEmpty() && !model.getAlt().isEmpty()
                                && !model.getTitle().isEmpty() && !model.getHyperlink().isEmpty();
                    }
                })
                .toList();
    }

    private Single<Document> getDocument() {
        return Single.just(Constants.PARSE_URL)
                .map(new Function<String, Document>() {
                    @Override
                    public Document apply(@NonNull String url) throws Exception {
                        return Jsoup.connect(url).get();
                    }
                });
    }

}
