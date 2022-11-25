package com.capstone.fashionboomerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.capstone.fashionboomerandroid.retrofit.DataModel;
import com.capstone.fashionboomerandroid.retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {

    private final PostActivity postActivity = this;

    public DataModel.PostPageData postPageData;
    private DataModel.Post post;
    private static final String BASE_URL = "http://fashionboomer.tk:8080";
    private SingerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ListView listView = (ListView) findViewById(R.id.postListView);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);

        adapter = new SingerAdapter();

        // 게시글 리스트
        Retrofit retrofitListJson = builder.addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterfaces = retrofitListJson.create(RetrofitInterface.class);

        retrofitInterfaces.getPosts(1, 30).enqueue(new Callback< DataModel.PostPageData>() {

            @Override
            public void onResponse(Call<DataModel.PostPageData> call, Response<DataModel.PostPageData> response) {
                if(response.isSuccessful()) {
                    Log.d("getMemberClosets", "getMemberClosets");
                    postPageData = new DataModel.PostPageData(response.body());

                    for (int i = 0; i < postPageData.getData().size(); i++) {
                        adapter.addItem(postPageData.getData().get(i));
                    }

                }

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // 게시글 열기
                        retrofitInterfaces.getPost(postPageData.getData().get(i).getId()).enqueue(new Callback<DataModel.Data>() {
                            @Override
                            public void onResponse(Call<DataModel.Data> call, Response<DataModel.Data> response) {
                                // 게시글 정보 받고 이동
                                if(response.isSuccessful()) {
                                    post = postPageData.getData().get(i);  // 일단 이거 사용
                                    Intent intent = new Intent(postActivity, PostDetailActivity.class);
                                    intent.putExtra("Post", post);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<DataModel.Data> call, Throwable t) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<DataModel.PostPageData> call, Throwable t) {

            }
        });

    }

    class SingerAdapter extends BaseAdapter {
        List<DataModel.Post> items = new ArrayList<>();

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
            SingerPostView view = null;
            if (convertView == null) {
                view = new SingerPostView(getApplicationContext());
            } else {
                view = (SingerPostView) convertView;
            }

            DataModel.Post item = items.get(position);

            view.setPost_id(item.getId());
            view.setPost_title(item.getPost_title());
            view.setPost_content(item.getPost_content());
            view.setUser_id(item.getUser_name());
            view.setPost_view(item.getPost_view());
            view.setPost_like_count(item.getPost_like_count());
            view.setPost_dislike_count(item.getPost_dislike_count());
            view.setPost_comment_count(item.getPost_comment_count());

            return view;
        }
    }

}