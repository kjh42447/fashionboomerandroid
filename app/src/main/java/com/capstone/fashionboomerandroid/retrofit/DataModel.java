package com.capstone.fashionboomerandroid.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {
    public static class PageData {
        @SerializedName("data")
        private List<Closet> data;

        @SerializedName("pageInfo")
        private PageInfo pageInfo;

        public PageData(PageData pageData) {
            for (Closet closet : pageData.getData()) {
                this.data.add(new Closet(closet.id, closet.user_id, closet.cloth_id));
            }

            this.pageInfo = new PageInfo(pageData.getPageInfo().page, pageData.getPageInfo().size, pageData.getPageInfo().totalElements, pageData.getPageInfo().totalPages);
        }

        public List<Closet> getData() {
            return data;
        }

        public PageInfo getPageInfo() {
            return pageInfo;
        }

        public void setData(List<Closet> data) {
            this.data = data;
        }

        public void setPageInfo(PageInfo pageInfo) {
            this.pageInfo = pageInfo;
        }
    }

    public static class Data {
        @SerializedName("data")
        private Closet data;

        public Closet getData() {
            return data;
        }

        public void setData(Closet data) {
            this.data = data;
        }
    }

    public static class Closet {
        @SerializedName("id")
        private int id;

        @SerializedName("user_id")
        private Long user_id;

        @SerializedName("cloth_id")
        private int cloth_id;

        public Closet(int id, Long user_id, int cloth_id) {
            this.id = id;
            this.user_id = user_id;
            this.cloth_id = cloth_id;
        }

        public int getId() {
            return id;
        }

        public Long getUser_id() {
            return user_id;
        }

        public int getCloth_id() {
            return cloth_id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setUser_id(Long user_id) {
            this.user_id = user_id;
        }

        public void setCloth_id(int cloth_id) {
            this.cloth_id = cloth_id;
        }
    }

    public static class PageInfo {
        @SerializedName("page")
        private int page;

        @SerializedName("size")
        private int size;

        @SerializedName("totalElements")
        private int totalElements;

        @SerializedName("totalPages")
        private int totalPages;

        public PageInfo(int page, int size, int totalElements, int totalPages) {
            this.page = page;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }

        public int getPage() {
            return page;
        }

        public int getSize() {
            return size;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
    }

    @SerializedName("body")
    @Expose
    private byte[] image;

    public byte[] getImage() {
        return image;
    }


}
