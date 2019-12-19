package com.appler.riverchiefsystem.utils.filter;

import java.io.Serializable;


public class OperationEntity implements Serializable {

    private String title;
    private String subtitle;
    private String image_url;

    public OperationEntity() {
    }

    public OperationEntity(String title, String subtitle, String image_url) {
        this.title = title;
        this.subtitle = subtitle;
        this.image_url = image_url;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
