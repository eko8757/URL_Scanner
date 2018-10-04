package com.example.eko8757.Myskripsi.Check;

import com.example.eko8757.Myskripsi.MyData.Url;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eko8757 on 21/07/18.
 */

public class CheckURL {

    Url url;
    private final static Logger logger = Logger.getLogger(CheckURL.class);

    public CheckURL(Url url) {
        this.url = url;
    }

    public double checkURL() {

        //cek https
        int has_http = 0;
        if (hashttp()) {
            has_http = 1;
        }

        //suspiciousURL
        int suspiciousURL = suspiciousURL();

        //cek IP dalam URL
        int has_IP = 0;
        if (hasIP()) {
            has_IP = 1;
        }

        double cek = has_http * 1 + suspiciousURL * 1 + has_IP * 1;
        logger.debug("~" + has_http + "~" + suspiciousURL + "~" + has_IP + "~");
        return cek;
    }

    boolean hashttp() {
        String _url = url.getUrl();
        Pattern p = Pattern.compile("(.*http://.*)");
        Matcher m = p.matcher(_url);
        return m.find();
    }

    int suspiciousURL() {
        int ctr = 0;
        String _url = url.getUrl();
        for (int i = 0; i < _url.length(); i++) {
            if (_url.charAt(i) == '@') {
                ctr += 1;
            }
        }
        return ctr;
    }

    boolean hasIP() {
        String _url = url.getUrl();
        Pattern p = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        Matcher m = p.matcher(_url);
        return m.find();
    }
}
