package com.capstone.fashionboomerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.capstone.fashionboomerandroid.retrofit.DataModel;
import com.capstone.fashionboomerandroid.retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostDetailActivity extends AppCompatActivity {

    private DataModel.PageCommentData pageCommentData;
    private static final String BASE_URL = "http://fashionboomer.tk:8080";
    private CommentAdapter adapter;

    private TextView detailTitle;
    private TextView detailUserName;
    private TextView detailCreatedAt;
    private TextView detailContents;
    private Button detailLike;
    private Button detailDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // ID
        detailTitle = (TextView) findViewById(R.id.detailTitle);
        detailUserName = (TextView) findViewById(R.id.detailUserName);
        detailCreatedAt = (TextView) findViewById(R.id.detailCreatedAt);
        detailContents = (TextView) findViewById(R.id.detailContents);
        detailLike = (Button) findViewById(R.id.detailLike);
        detailDislike = (Button) findViewById(R.id.detailDislike);

        ListView listView = (ListView) findViewById(R.id.commentListView);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);

        adapter = new CommentAdapter();

        // 게시글 상세
        Intent intent = getIntent();
        DataModel.Post post = (DataModel.Post) intent.getSerializableExtra("Post");

        detailTitle.setText(post.getPost_title());
        detailUserName.setText(post.getUser_name());
        detailCreatedAt.setText(post.getCreated_at().substring(0, 10));
        detailContents.setText(post.getPost_content());
        detailLike.setText("좋아요\n" + post.getPost_like_count());
        detailDislike.setText("싫어요\n" + post.getPost_dislike_count());

        // 댓글 리스트
        Retrofit retrofitListJson = builder.addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterfaces = retrofitListJson.create(RetrofitInterface.class);

        retrofitInterfaces.getComments(post.getId(), 1, 20).enqueue(new Callback<DataModel.PageCommentData>() {
            @Override
            public void onResponse(Call<DataModel.PageCommentData> call, Response<DataModel.PageCommentData> response) {
                if(response.isSuccessful()) {
                    Log.d("Comments", "Start!");
                    pageCommentData = new DataModel.PageCommentData(response.body());

                    for (DataModel.Comment comment : pageCommentData.getData()) {
                        adapter.addItem(comment);
                        Log.d("Comment", comment.getComment());
                    }
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataModel.PageCommentData> call, Throwable t) {

            }
        });
    }

    class CommentAdapter extends BaseAdapter {
        List<DataModel.Comment> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(DataModel.Comment item) {
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
            SinglerCommentView view = null;
            if (convertView == null) {
                view = new SinglerCommentView(getApplicationContext());
            } else {
                view = (SinglerCommentView) convertView;
            }

            DataModel.Comment item = items.get(position);

            view.setCommentContents(item.getComment());
            view.setCommentUserName(item.getUser_name());
            view.setCommentCreatedAt(item.getCreated_at());

            return view;
        }
    }
}