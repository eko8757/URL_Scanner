package com.example.eko8757.Myskripsi.Rating;

import com.example.eko8757.Myskripsi.Check.CheckURL;
import com.example.eko8757.Myskripsi.Check.CheckWeb;
import com.example.eko8757.Myskripsi.MyData.Filename;
import com.example.eko8757.Myskripsi.MyData.Url;

import org.apache.log4j.Logger;

/**
 * Created by eko8757 on 21/07/18.
 */

public class GiveFinalRating {

    private Url url;
    private Filename filename;

    private final static Logger logger = Logger.getLogger(GiveFinalRating.class);

    public GiveFinalRating(String _url, String _filename) {
        url = new Url(_url);
        filename = new Filename(_filename);
    }

    public int run() {
        double match = 0;
        try {
            logger.debug("\n");
            CheckURL url_utilities = new CheckURL(url);
            CheckWeb web_utilities = new CheckWeb(filename);

            double url_utilities_score = url_utilities.checkURL();
            match += url_utilities_score;
            double web_utilities_score = web_utilities.checkWeb();
            match += web_utilities_score;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(match);

        return (int) match;
    }
}
