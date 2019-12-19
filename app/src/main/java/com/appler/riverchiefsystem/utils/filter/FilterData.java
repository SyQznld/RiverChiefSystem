package com.appler.riverchiefsystem.utils.filter;

import java.io.Serializable;
import java.util.List;


public class FilterData implements Serializable {

    private List<FilterTwoEntity> category;
    private List<FilterEntity> sorts;
    private List<FilterEntity> filters;

    private List<FilterEntity> orgFilter;
    private List<FilterEntity> types;

    public List<FilterEntity> getTypes() {
        return types;
    }

    public void setTypes(List<FilterEntity> types) {
        this.types = types;
    }


    public List<FilterTwoEntity> getCategory() {
        return category;
    }

    public void setCategory(List<FilterTwoEntity> category) {
        this.category = category;
    }

    public List<FilterEntity> getSorts() {
        return sorts;
    }

    public void setSorts(List<FilterEntity> sorts) {
        this.sorts = sorts;
    }

    public List<FilterEntity> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterEntity> filters) {
        this.filters = filters;
    }


    public List<FilterEntity> getOrgFilter() {
        return orgFilter;
    }

    public void setOrgFilter(List<FilterEntity> orgFilter) {
        this.orgFilter = orgFilter;
    }
}
