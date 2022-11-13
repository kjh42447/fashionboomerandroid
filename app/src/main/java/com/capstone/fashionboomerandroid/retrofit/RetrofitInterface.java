package com.capstone.fashionboomerandroid.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface RetrofitInterface {
    @GET("/v11/closets/images/{closet_id}")
    @Streaming
    Call<ResponseBody> downloadImage(
            @Path("closet_id") int closetId);

    @GET("/v11/closets/images/nukki/{closet_id}")
    @Streaming
    Call<ResponseBody> downloadNukkiImage(
            @Path("closet_id") int closetId);

    @GET("/v11/closets/{closet_id}")
    @Streaming
    Call<DataModel.Data> getCloset(
            @Path("closet_id") int closetId);

    @GET("/v11/closets/members/{member_id}")
    @Streaming
    Call<DataModel.PageData> getMemberClosets(
            @Path("member_id") int memberId,
            @Query("page") int page,
            @Query("size") int size);
}
