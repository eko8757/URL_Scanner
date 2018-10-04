package com.example.eko8757.Myskripsi.MyData;

/**
 * Created by eko8757 on 21/07/18.
 */

public class Url {

    private String url;

    public Url(String url) {
        super();
        System.out.println("URL" + url);
        // remove www. from domain name
        this.url = url.replaceAll("\\\\", "").replaceFirst("://www.", "://");
    }

    public String getUrl() {
        return url;
    }
}
