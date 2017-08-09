package com.sh.ilovepooq.MVC.controller;

import android.content.Context;
import android.test.AndroidTestCase;

import com.sh.ilovepooq.base.Constants;
import com.sh.ilovepooq.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.InputStream;

public class TestHTMLParsingAsyncTask extends AndroidTestCase {

    private Context context;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context = context;
    }

    public void testGetDocument() throws Exception {
        Connection connection = Jsoup.connect(Constants.PARSE_URL);
        Document document = connection.get();
        assertNotNull(document);
    }

    public void testParsing() throws Exception {
        InputStream inputStream = context.getResources().openRawResource(R.raw.test_html_parsing);
        Document document = Jsoup.parse(inputStream, null, "");
        Elements elements = document.select(Constants.PARSE_SBSMD_MCMPT_W);

        assertNotNull(elements);

        Node node = elements.get(0).childNode(1);
        String imageURL = node.childNode(1).attr(Constants.PARSE_SRC);
        String alt = node.childNode(1).attr(Constants.PARSE_ALT);
        String title = node.attr(Constants.PARSE_TITLE);
        String hyperlink = node.attr(Constants.PARSE_HREF);

        assertEquals(imageURL, "image_url_01");
        assertEquals(alt, "alt_01");
        assertEquals(title, "title_01");
        assertEquals(hyperlink, "hyperlink_01");

        node = elements.get(1).childNode(1);
        imageURL = node.childNode(1).attr(Constants.PARSE_SRC);
        title = node.attr(Constants.PARSE_TITLE);
        hyperlink = node.attr(Constants.PARSE_HREF);

        assertEquals(imageURL, "image_url_02");
        assertEquals(alt, "alt_01");
        assertEquals(title, "title_02");
        assertEquals(hyperlink, "hyperlink_02");
    }

}
