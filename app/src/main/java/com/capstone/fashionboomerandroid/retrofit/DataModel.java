package com.capstone.fashionboomerandroid.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataModel {
    public static class PostPageData {
        @SerializedName("data")
        private List<Post> data;

        @SerializedName("pageInfo")
        private PageInfo pageInfo;

        public PostPageData() {
            this.data = new ArrayList<>();
            this.pageInfo = new PageInfo();
        }

        public PostPageData(PostPageData postPageData) {
            this.data = new ArrayList<>();
            for (Post post : postPageData.getData()) {
                this.data.add(new Post(post));
            }

            this.pageInfo = new PageInfo(postPageData.getPageInfo().page, postPageData.getPageInfo().size, postPageData.getPageInfo().totalElements, postPageData.getPageInfo().totalPages);
        }

        public List<Post> getData() {
            return data;
        }

        public PageInfo getPageInfo() {
            return pageInfo;
        }

        public void setData(List<Post> data) {
            this.data = data;
        }

        public void setPageInfo(PageInfo pageInfo) {
            this.pageInfo = pageInfo;
        }
    }

    public static class Post {
        @SerializedName("id")
        private int id;

        @SerializedName("parentId")
        private int parentId;

        @SerializedName("post_title")
        private String post_title;

        @SerializedName("post_content")
        private String post_content;

        @SerializedName("user_id")
        private Long user_id;

        @SerializedName("post_view")
        private int post_view;

        @SerializedName("post_like_count")
        private int post_like_count;

        @SerializedName("post_dislike_count")
        private int post_dislike_count;

        @SerializedName("post_answer_count")
        private int post_answer_count;

        @SerializedName("post_comment_count")
        private int post_comment_count;

        @SerializedName("user_name")
        private String user_name;

        @SerializedName("created_at")
        private String created_at;

        @SerializedName("last_modified_at")
        private String last_modified_at;

        public Post(int id, int parentId, String post_title, String post_content, Long user_id, int post_view, int post_like_count, int post_dislike_count, int post_answer_count, int post_comment_count, String user_name, String created_at, String last_modified_at) {
            this.id = id;
            this.parentId = parentId;
            this.post_title = post_title;
            this.post_content = post_content;
            this.user_id = user_id;
            this.post_view = post_view;
            this.post_like_count = post_like_count;
            this.post_dislike_count = post_dislike_count;
            this.post_answer_count = post_answer_count;
            this.post_comment_count = post_comment_count;
            this.user_name = user_name;
            this.created_at = created_at;
            this.last_modified_at = last_modified_at;
        }

        public Post(Post post) {
            this.id = post.getId();
            this.parentId = post.getParentId();
            this.post_title = post.getPost_title();
            this.post_content = post.getPost_content();
            this.user_id = post.getUser_id();
            this.post_view = post.getPost_view();
            this.post_like_count = post.getPost_like_count();
            this.post_dislike_count = post.getPost_dislike_count();
            this.post_answer_count = post.getPost_answer_count();
            this.post_comment_count = post.getPost_comment_count();
            this.user_name = post.getUser_name();
            this.created_at = post.getCreated_at();
            this.last_modified_at = post.getLast_modified_at();
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setLast_modified_at(String last_modified_at) {
            this.last_modified_at = last_modified_at;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getLast_modified_at() {
            return last_modified_at;
        }

        public int getId() {
            return id;
        }

        public int getParentId() {
            return parentId;
        }

        public String getPost_title() {
            return post_title;
        }

        public String getPost_content() {
            return post_content;
        }

        public Long getUser_id() {
            return user_id;
        }

        public int getPost_view() {
            return post_view;
        }

        public int getPost_like_count() {
            return post_like_count;
        }

        public int getPost_dislike_count() {
            return post_dislike_count;
        }

        public int getPost_answer_count() {
            return post_answer_count;
        }

        public int getPost_comment_count() {
            return post_comment_count;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public void setPost_content(String post_content) {
            this.post_content = post_content;
        }

        public void setUser_id(Long user_id) {
            this.user_id = user_id;
        }

        public void setPost_view(int post_view) {
            this.post_view = post_view;
        }

        public void setPost_like_count(int post_like_count) {
            this.post_like_count = post_like_count;
        }

        public void setPost_dislike_count(int post_dislike_count) {
            this.post_dislike_count = post_dislike_count;
        }

        public void setPost_answer_count(int post_answer_count) {
            this.post_answer_count = post_answer_count;
        }

        public void setPost_comment_count(int post_comment_count) {
            this.post_comment_count = post_comment_count;
        }
    }

    public static class PageData {
        @SerializedName("data")
        private List<Closet> data;

        @SerializedName("pageInfo")
        private PageInfo pageInfo;

        public PageData(PageData pageData) {
            this.data = new ArrayList<>();
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

    public static class Data<T> {
        @SerializedName("data")
        private T data;

        public T getData() {
            return data;
        }

        public void setData(T data) {
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

        public PageInfo() {
            this.page = 0;
            this.size = 0;
            this.totalElements = 0;
            this.totalPages = 0;
        }

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
