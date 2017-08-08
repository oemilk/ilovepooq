package com.sh.ilovepooq.MVC.controller;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sh.ilovepooq.Constants;
import com.sh.ilovepooq.MVC.model.ContentInfoModel;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HTMLParsingThread extends Thread {

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    private static final String TAG = "HTMLParsingThread";
    private Handler handler;

    public HTMLParsingThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        Message message = new Message();
        try {
            Document document = getDocument();
            if (document != null) {
                Log.d(TAG, "Document is not null.");
                ArrayList<ContentInfoModel> list = getContentInfoModelArrayList(document);
                message.what = SUCCESS;
                message.obj = list;
            } else {
                message.what = FAIL;
            }
        } catch (Exception e) {
            message.what = FAIL;
            Log.e(TAG, e.getMessage());
        } finally {
            handler.sendMessage(message);
        }
    }

    /**
     * Get a parsing result as Document object.
     *
     * @return an objcet of Document.
     */
    private Document getDocument() {
        try {
            Connection connection = Jsoup.connect(Constants.PARSE_URL);
            return connection.get();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Get the Image's info as ImageInfoModel object.
     *
     * @param document Document object from Jsoup connection.
     * @return an ArrayList of ImageInfoModels.
     */
    private ArrayList<ContentInfoModel> getContentInfoModelArrayList(Document document) {
        try {
            Log.d(TAG, "getContentInfoModelArrayList");
            Elements elements = document.getElementsByTag(Constants.PARSE_LI);
            ArrayList<ContentInfoModel> list = new ArrayList<>();
            ContentInfoModel model;
            Node node;
            String imageURL;
            String alt;
            String title;
            String hyperlink;

            Log.d(TAG, "size : " + elements.size());

            for (Element element : elements) {
                try {
                    node = element.childNode(1);
                    imageURL = node.childNode(3).attr(Constants.PARSE_SRC);
                    alt = node.childNode(3).attr(Constants.PARSE_ALT);
                    title = node.childNode(1).childNode(0).toString();
                    hyperlink = node.attr(Constants.PARSE_HREF).replace("\r", "").replace("\n", "")
                            .replace("\t", "");

                    model = new ContentInfoModel(imageURL, alt, title, hyperlink,
                            Constants.SUCCESS);
                    if (!imageURL.isEmpty() && !alt.isEmpty() && !title.isEmpty()
                            && !hyperlink.isEmpty()) {
                        Log.d(TAG, "url : " + imageURL + ", alt : " + alt + ", title : " + title
                                + ", hyperlink : " + hyperlink);
                        list.add(model);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            return list;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
