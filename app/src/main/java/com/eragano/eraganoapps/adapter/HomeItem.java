package com.eragano.eraganoapps.adapter;

/**
 * Created by M Dimas Faizin on 8/10/2016.
 */
public class HomeItem {
    private int backimage;
    private int iconimage;
    private String header1;
    private String header2;

    public HomeItem(int backimage, int iconimage, String header1, String header2) {
        this.backimage = backimage;
        this.iconimage = iconimage;
        this.header1 = header1;
        this.header2 = header2;
    }

    public Integer getBackimage() {
        return backimage;
    }

    public void setBackimage(Integer backimage) {
        this.backimage = backimage;
    }

    public Integer getIconimage() {
        return iconimage;
    }

    public void setIconimage(Integer iconimage) {
        this.iconimage = iconimage;
    }

    public String getHeader1() {
        return header1;
    }

    public void setHeader1(String header1) {
        this.header1 = header1;
    }

    public String getHeader2() {
        return header2;
    }

    public void setHeader2(String header2) {
        this.header2 = header2;
    }
}
