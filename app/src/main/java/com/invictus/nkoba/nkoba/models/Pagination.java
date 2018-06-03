
package com.invictus.nkoba.nkoba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("total_page")
    @Expose
    private Integer totalPage;
    @SerializedName("total_entries")
    @Expose
    private Integer totalEntries;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    @SerializedName("page_number")
    @Expose
    private Integer pageNumber;
    @SerializedName("link")
    @Expose
    private String link;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(Integer totalEntries) {
        this.totalEntries = totalEntries;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
