package com.sh.ilovepooq.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.sh.ilovepooq.Constants;
import com.sh.ilovepooq.model.ContentInfoModel;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HTMLParsingAsyncTask extends AsyncTask<Void, Void, ArrayList<ContentInfoModel>> {

    private final String TAG = "HTMLParsingAsyncTask";

    private HTMLParsingCallback mCallback;

    public HTMLParsingAsyncTask(HTMLParsingCallback callback) {
        mCallback = callback;
    }

    @Override
    protected ArrayList<ContentInfoModel> doInBackground(Void... params) {
        if (isCancelled()) {
            return null;
        }

        Document document = getDocument();
        if (document != null) {
            Log.d(TAG, "Document is not null.");
            return getContentInfoModelArrayList(document);
        }

        Log.d(TAG, "Document is null.");
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<ContentInfoModel> list) {
        super.onPostExecute(list);
        if (list == null) {
            Log.d(TAG, "Parsing failed");
            mCallback.onHTMLParsingFailed();
        } else {
            Log.d(TAG, "Parsing succeed");
            mCallback.onHTMLParsingSucceed(list);
        }
    }

    /**
     *  Get a parsing result as Document object.
     *
     * @return an objcet of Document.
     */
    private Document getDocument() {
        try {
            Connection connection = Jsoup.connect(Constants.PARSE_URL);
            return connection.get();
        } catch (Exception e) {
            e.printStackTrace();
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
            Elements elements = document.select(Constants.PARSE_LI);
            ArrayList<ContentInfoModel> list = new ArrayList<>();
            ContentInfoModel model;
            Node node;
            String imageURL, alt, title, hyperlink;

            Log.d(TAG, "size : " + elements.size());

            for (Element element : elements) {
                node = element.childNode(1);
                imageURL = node.childNode(3).attr(Constants.PARSE_SRC);
                alt = node.childNode(3).attr(Constants.PARSE_ALT);
                title = node.childNode(1).childNode(0).toString();
                hyperlink = node.attr(Constants.PARSE_HREF).replace("\r", "").replace("\n", "").replace("\t", "");

                model = new ContentInfoModel(imageURL, alt, title, hyperlink, Constants.SUCCESS);
                if (!imageURL.isEmpty() && !alt.isEmpty() && !title.isEmpty() && !hyperlink.isEmpty()) {
                    Log.d(TAG, "url : " + imageURL + ", alt : " + alt + ", title : " + title + ", hyperlink : " + hyperlink);
                    list.add(model);
                }
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
