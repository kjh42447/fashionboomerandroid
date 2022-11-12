package com.capstone.fashionboomerandroid.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {
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

    @SerializedName("body")
    @Expose
    private byte[] image;

    public byte[] getImage() {
        return image;
    }


}
