package vn.graybee.common.dto;

public class SortInfo {

    private String sortBy;

    private String order;

    public SortInfo(String sortBy, String order) {
        this.sortBy = sortBy;
        this.order = order;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
