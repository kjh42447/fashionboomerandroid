package com.capstone.fashionboomerandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SingerPostView extends RelativeLayout {

    private TextView post_id;
    private TextView post_title;
    private TextView post_content;
    private TextView user_id;
    private TextView post_view;
    private TextView post_like_count;
    private TextView post_dislike_count;
    private TextView post_comment_count;

    public SingerPostView(Context context) {
        super(context);


    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.post, this, true);

        post_id = (TextView) findViewById(R.id.post_id);
        post_title = (TextView) findViewById(R.id.post_title);
        post_content = (TextView) findViewById(R.id.post_content);
        user_id = (TextView) findViewById(R.id.user_id);
        post_view = (TextView) findViewById(R.id.post_view);
        post_like_count = (TextView) findViewById(R.id.post_like_count);
        post_dislike_count = (TextView) findViewById(R.id.post_dislike_count);
        post_comment_count = (TextView) findViewById(R.id.post_comment_count);
    }

    public void setPost_id(TextView post_id) {
        this.post_id = post_id;
    }

    public void setPost_title(TextView post_title) {
        this.post_title = post_title;
    }

    public void setPost_content(TextView post_content) {
        this.post_content = post_content;
    }

    public void setUser_id(TextView user_id) {
        this.user_id = user_id;
    }

    public void setPost_view(TextView post_view) {
        this.post_view = post_view;
    }

    public void setPost_like_count(TextView post_like_count) {
        this.post_like_count = post_like_count;
    }

    public void setPost_dislike_count(TextView post_dislike_count) {
        this.post_dislike_count = post_dislike_count;
    }

    public void setPost_comment_count(TextView post_comment_count) {
        this.post_comment_count = post_comment_count;
    }
}
