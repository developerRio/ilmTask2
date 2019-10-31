package com.originalstocks.ilmtask.model;

public class ImageModel {

    private String imgLink;
    private int pos;

    public ImageModel(int pos) {
        this.pos = pos;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
