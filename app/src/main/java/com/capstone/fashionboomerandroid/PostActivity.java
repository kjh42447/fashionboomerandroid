package com.capstone.fashionboomerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.capstone.fashionboomerandroid.retrofit.DataModel;
import com.capstone.fashionboomerandroid.retrofit.RetrofitInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {
    private DataModel.PostPageData postPageData = new DataModel.PostPageData();
    private static final String BASE_URL = "http://fashionboomer.tk:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);

        // 게시글 리스트
        Retrofit retrofitListJson = builder.addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterfaces = retrofitListJson.create(RetrofitInterface.class);

        retrofitInterfaces.getPosts(1, 30).enqueue(new Callback< DataModel.PostPageData>() {

            @Override
            public void onResponse(Call<DataModel.PostPageData> call, Response<DataModel.PostPageData> response) {
                if(response.isSuccessful()) {
                    Log.d("getMemberClosets", "getMemberClosets");
                    postPageData = new DataModel.PostPageData(response.body());
                    for (DataModel.Post post : postPageData.getData()) {
                        Log.d("PostTitle", post.getPost_title());
                    }
                }
            }

            @Override
            public void onFailure(Call<DataModel.PostPageData> call, Throwable t) {

            }
        });
    }

    class SingerAdapter extends BaseAdapter {
        List<DataModel.Post> items = postPageData.getData();

        // Generate > implement methods
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(DataModel.Post item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 뷰 객체 재사용
//            SingerItemView view = null;
//            if (convertView == null) {
//                view = new SingerItemView(getApplicationContext());
//            } else {
//                view = (SingerItemView) convertView;
//            }
//
//            DataModel.Post item = items.get(position);
//
//            view.setName(item.getName());
//            view.setMobile(item.getMobile());
//            view.setImage(item.getResId());
//
//
//            return view;
            return null;
        }
    }

}