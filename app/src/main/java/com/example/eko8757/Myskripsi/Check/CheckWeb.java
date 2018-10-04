package com.example.eko8757.Myskripsi.Check;

import com.example.eko8757.Myskripsi.MyData.Filename;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Created by eko8757 on 02/08/18.
 */

public class CheckWeb {

    Filename fileName;
    private final static Logger logger = Logger.getLogger(CheckWeb.class);

    public CheckWeb(Filename filename) {
        this.fileName = filename;
    }

    // cek webpage source code
    public double checkWeb() {
        //cek script
        int has_script = 0;
        if (hasScript()) {
            has_script = 1;
        }
        //cek iframe
        int has_iframe = 0;
        if (hasIframe()) {
            has_iframe = 1;
        }
        //cek image
        int has_image = 0;
        if (hasImage()) {
            has_image = 1;
        }
        //cek mailto
        int has_mailto = 0;
        if (hasmail()) {
            has_mailto = 1;
        }
//        //cek popup
        int has_Popup = 0;
        if (hasPopup()) {
            has_Popup = 1;
        }

        double cek = has_script * 1 + has_iframe * 1 + has_image * 1 + has_mailto * 1  + has_Popup * 1 ;
        logger.debug("~" + has_script + "~" + has_iframe + "~" + "~" + has_image + "~" + has_mailto + "~" + has_Popup + "~");
        return cek;
    }

    //cek regex script
    boolean hasScript() {
        String _filename = fileName.getFilename();
        Pattern p = Pattern.compile("<script[^>]+src(.*?)[^>]*>");
        Matcher m = p.matcher(_filename);
        return m.find();
    }

    //cek regex iframe
    boolean hasIframe() {
        String _filename = fileName.getFilename();
        Pattern p = Pattern.compile("<iframe[^>]");
        Matcher m = p.matcher(_filename);
        return m.find();
    }

    //cek regex image
    boolean hasImage() {
        String _filename = fileName.getFilename();
        Pattern p = Pattern.compile("<img[^>]+src(.*?)[^>]*>");
        Matcher m = p.matcher(_filename);
        return m.find();
    }

//    //cek regex mailto
    boolean hasmail() {
        String _filename = fileName.getFilename();
        Pattern p = Pattern.compile("([a-z0-9_.-]+)@([da-z.-]+)([a-z.]{2,6})");
        Matcher m = p.matcher(_filename);
        return m.find();
    }

    //cek regex Popup
    boolean hasPopup() {
        String _filename = fileName.getFilename();
        Pattern p = Pattern.compile("<a[^>]+onclick=[\"javascript:]+[window.open](.*?)+[style][\"][^>]*>");
        Matcher m = p.matcher(_filename);
        return m.find();
    }
}
