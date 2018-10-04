package com.example.eko8757.Myskripsi.MyData;

/**
 * Created by eko8757 on 02/08/18.
 */

public class Filename {

    private String filename;

    public Filename(String filename) {
        super();

        // remove www. from domain name
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
