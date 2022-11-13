package com.capstone.fashionboomerandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.capstone.fashionboomerandroid.retrofit.DataModel;
import com.capstone.fashionboomerandroid.retrofit.DataModel.Closet;
import com.capstone.fashionboomerandroid.retrofit.RetrofitInterface;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivImage;
    private ImageView ivImage2;
    private Button button1;
    private static final String BASE_URL = "http://fashionboomer.tk:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivImage = (ImageView) findViewById(R.id.iv_image);
        ivImage2 = (ImageView) findViewById(R.id.iv_image2);
        button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(this);

//        // Glide로 이미지 표시하기
//        String imageUrl = "http://fashionboomer.tk:8080/v11/closets/images/9";
//        Glide.with(this).load(imageUrl).into(ivImage);

        // 이미지
        new Thread() {
            public void run() {
                Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);

                Retrofit retrofit = builder.build();
                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

                Call<ResponseBody> call = retrofitInterface.downloadImage(9);

                call.enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        InputStream is = response.body().byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        ivImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        }.start();

    }

    @Override
    public void onClick(View view) {
        Toast myToast = Toast.makeText(this.getApplicationContext(),"토스트테스트", Toast.LENGTH_SHORT);

        // json
//        Retrofit retrofitJson = builder.addConverterFactory(GsonConverterFactory.create()).build();
//        RetrofitInterface retrofitInterface = retrofitJson.create(RetrofitInterface.class);
//        Log.d("Test", "Log Test");
//
//        retrofitInterface.getCloset(8).enqueue(new Callback< DataModel.Data>() {
//            @Override
//            public void onResponse(@NonNull Call< DataModel.Data > call, @NonNull Response< DataModel.Data > response) {
////                myToast.show();
//                if(response.isSuccessful()) {
//                    DataModel.Data data = response.body();
//                    Log.d("id", Integer.toString(data.getData().getId()));
//                    Log.d("user_id", Long.toString(data.getData().getUser_id()));
//                    Log.d("cloth_id", Integer.toString(data.getData().getCloth_id()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataModel.Data> call, Throwable t) {
//                myToast.show();
//                t.printStackTrace();
//            }
//        });

        // 누끼
        new Thread() {
            public void run() {
                // 레트로핏
                Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);

                Retrofit retrofit = builder.build();
                RetrofitInterface retrofitInterface2 = retrofit.create(RetrofitInterface.class);
                Call<ResponseBody> call2 = retrofitInterface2.downloadNukkiImage(9);

                call2.enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response) {
                        InputStream is = response.body().byteStream();
                        Bitmap bitmap2 = BitmapFactory.decodeStream(is);
                        ivImage2.setImageBitmap(bitmap2);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call2, Throwable t) {

                    }
                });
            }
        }.start();


        // 옷장 리스트
//        Retrofit retrofitListJson = builder.addConverterFactory(GsonConverterFactory.create()).build();
//        RetrofitInterface retrofitInterfaces = retrofitListJson.create(RetrofitInterface.class);
//
//        retrofitInterfaces.getMemberClosets(1, 1, 10).enqueue(new Callback< DataModel.PageData>() {
//            @Override
//            public void onResponse(@NonNull Call< DataModel.PageData > call, @NonNull Response< DataModel.PageData > response) {
////                myToast.show();
//                if(response.isSuccessful()) {
//                    Log.d("getMemberClosets", "getMemberClosets");
//                    DataModel.PageData data = response.body();
//                    for (Closet closet : data.getData()) {
//                        Log.d("id", Integer.toString(closet.getId()));
//                        Log.d("user_id", Long.toString(closet.getUser_id()));
//                        Log.d("cloth_id", Integer.toString(closet.getCloth_id()));
//                    }
//                    Log.d("page", Integer.toString(data.getPageInfo().getPage()));
//                    Log.d("size", Integer.toString(data.getPageInfo().getSize()));
//                    Log.d("totalElements", Integer.toString(data.getPageInfo().getTotalElements()));
//                    Log.d("totalPages", Integer.toString(data.getPageInfo().getTotalPages()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataModel.PageData> call, Throwable t) {
//                myToast.show();
//                t.printStackTrace();
//            }
//        });

    }

}