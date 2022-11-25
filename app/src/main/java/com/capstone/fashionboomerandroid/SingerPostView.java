package com.capstone.fashionboomerandroid;

import android.content.Context;
import android.util.AttributeSet;
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

        init(context);
    }

    public SingerPostView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
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

    public void setPost_id(int postId) {
        post_id.setText(Integer.toString(postId));
    }

    public void setPost_title(String postTitle) {
        post_title.setText(postTitle);
    }

    public void setPost_content(String postContent) {
        post_content.setText(postContent);
    }

    public void setUser_id(String userName) {
        user_id.setText(userName);
    }

    public void setPost_view(int postView) {
        post_view.setText("조회수 : " + Integer.toString(postView));
    }

    public void setPost_like_count(int postLikeCount) {
        post_like_count.setText("좋아요 : " + Integer.toString(postLikeCount));
    }

    public void setPost_dislike_count(int postDislikeCount) {
        post_dislike_count.setText("싫어요 : " + Integer.toString(postDislikeCount));
    }

    public void setPost_comment_count(int postCommentCount) {
        post_comment_count.setText("댓글수 : " + Integer.toString(postCommentCount));
    }
}
