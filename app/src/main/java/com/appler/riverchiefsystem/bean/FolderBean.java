package com.appler.riverchiefsystem.bean;


import com.appler.riverchiefsystem.utils.ImageSelectUtils;

import java.util.ArrayList;

/**
 * 图片文件夹实体类
 */
public class FolderBean {

    private String name;
    private ArrayList<String> images;

    public FolderBean(String name) {
        this.name = name;
    }

    public FolderBean(String name, ArrayList<String> images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void addImage(String image) {
        if (image != null && ImageSelectUtils.isNotEmptyString(image)) {
            if (images == null) {
                images = new ArrayList<>();
            }
            images.add(image);
        }
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name='" + name + '\'' +
                ", images=" + images +
                '}';
    }
}
