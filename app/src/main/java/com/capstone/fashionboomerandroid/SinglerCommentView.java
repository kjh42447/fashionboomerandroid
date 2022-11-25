package com.capstone.fashionboomerandroid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SinglerCommentView extends RelativeLayout {

    private TextView commentUserName;
    private TextView commentCreatedAt;
    private TextView commentContents;

    public SinglerCommentView(Context context) {
        super(context);

        init(context);
    }

    public SinglerCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.comment, this, true);

        commentUserName = (TextView) findViewById(R.id.commentUserName);
        commentCreatedAt = (TextView) findViewById(R.id.commentCreatedAt);
        commentContents = (TextView) findViewById(R.id.commentContents);
    }

    public void setCommentUserName(String userName) {
        commentUserName.setText(userName);
    }

    public void setCommentCreatedAt(String createdAt) {
        commentCreatedAt.setText(createdAt.substring(0, 10));
    }

    public void setCommentContents(String contents) {
        commentContents.setText(contents);
    }
}
