package com.appler.riverchiefsystem.utils.filter;

import java.io.Serializable;
import java.util.List;


public class FilterTwoEntity implements Serializable {

    private String type;
    private List<FilterEntity> list;
    private boolean isSelected;

    public FilterTwoEntity() {
    }

    public FilterTwoEntity(String type, List<FilterEntity> list) {
        this.type = type;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<FilterEntity> getList() {
        return list;
    }

    public void setList(List<FilterEntity> list) {
        this.list = list;
    }
}
