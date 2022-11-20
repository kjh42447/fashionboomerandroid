package com.capstone.fashionboomerandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.capstone.fashionboomerandroid.image.ImageViewClass;
import com.capstone.fashionboomerandroid.image.MatrixImage;
import com.capstone.fashionboomerandroid.image.TOUCH_MODE;
import com.capstone.fashionboomerandroid.retrofit.DataModel;
import com.capstone.fashionboomerandroid.retrofit.DataModel.Closet;
import com.capstone.fashionboomerandroid.retrofit.RetrofitInterface;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final MainActivity mainActivity = this;

    private DataModel.PageData closets;
    private List<MatrixImage> matrixImages = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private List<MatrixImage> nukkiImageViews = new ArrayList<>();
    private  ImageView maleImage;
    private  ImageView femaleImage;

    private Button button1;
    private Button maleButton;
    private Button femaleButton;

    private ConstraintLayout closetNukkiLayout;
    private LinearLayout closetLayout;

    private static final String BASE_URL = "http://fashionboomer.tk:8080";

    private MatrixImage recentlyImage;

    // memberId
    private Long memberId = 1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ID
        maleImage = (ImageView) findViewById(R.id.maleImage);
        femaleImage = (ImageView) findViewById(R.id.femaleImage);
        button1 = (Button) findViewById(R.id.button1);
        maleButton = (Button) findViewById(R.id.maleButton);
        femaleButton = (Button) findViewById(R.id.femaleButton);

        closetNukkiLayout = (ConstraintLayout) findViewById(R.id.closetNukkiLayout);
        closetLayout = (LinearLayout) findViewById(R.id.closetLayout);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);

        // 옷장 리스트
        Retrofit retrofitListJson = builder.addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterfaces = retrofitListJson.create(RetrofitInterface.class);

        retrofitInterfaces.getMemberClosets(memberId.intValue(), 1, 10).enqueue(new Callback< DataModel.PageData>() {
            @Override
            public void onResponse(@NonNull Call< DataModel.PageData > call, @NonNull Response< DataModel.PageData > response) {
//                myToast.show();
                if(response.isSuccessful()) {
                    Log.d("getMemberClosets", "getMemberClosets");
                    closets = new DataModel.PageData(response.body());

                    for(int i = 0; i < closets.getData().size(); i++) {
                        // 누끼 이미지 뷰
                        nukkiImageViews.add(new MatrixImage());

                        nukkiImageViews.get(i).setMatrix(new Matrix());
                        nukkiImageViews.get(i).setSavedMatrix(new Matrix());

                        nukkiImageViews.get(i).setImageView(new ImageView(getBaseContext()));
                        nukkiImageViews.get(i).getImageView().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1600));
                        Glide.with(mainActivity).load(BASE_URL + "/v11/closets/images/nukki/" + closets.getData().get(i).getId()).into(nukkiImageViews.get(i).getImageView());
                        
                        // 누끼 이미지 터치 이벤트
                        nukkiImageViews.get(i).getImageView().setOnTouchListener(onTouch);
                        nukkiImageViews.get(i).getImageView().setScaleType(ImageView.ScaleType.MATRIX); // 스케일 타입을 매트릭스로 해줘야 움직인다.

                        nukkiImageViews.get(i).getImageView().setVisibility(View.INVISIBLE);

                        // 뷰 추가
                        closetNukkiLayout.addView(nukkiImageViews.get(i).getImageView());



                        // 일반 이미지 뷰
                        imageViews.add(new ImageView(getBaseContext()));
                        imageViews.get(i).setLayoutParams(new ViewGroup.LayoutParams(400, 400));
                        Glide.with(mainActivity).load(BASE_URL + "/v11/closets/images/" + closets.getData().get(i).getId()).into(imageViews.get(i));
                        int finalI = i;

                        // 일반 이미지 클릭 이벤트
                        imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recentlyImage = nukkiImageViews.get(finalI);
                                recentlyImage.getImageView().setVisibility(View.VISIBLE);
                                recentlyImage.getImageView().bringToFront();
                            }
                        });

                        closetLayout.addView(imageViews.get(i));
                    }
                    // 뷰 추가
                    recentlyImage = nukkiImageViews.get(0);
                }
            }

            @Override
            public void onFailure(Call<DataModel.PageData> call, Throwable t) {
                t.printStackTrace();
            }
        });


        maleImage.setImageResource(R.drawable.male);
        femaleImage.setImageResource(R.drawable.female);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recentlyImage.getImageView().setVisibility(View.GONE);
            }
        });
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                femaleImage.setVisibility(View.GONE);
                maleImage.setVisibility(View.VISIBLE);
            }
        });
        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maleImage.setVisibility(View.GONE);
                femaleImage.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onClick(View view) {

    }

    // 이미지 컨트롤
    private View.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.equals(recentlyImage.getImageView())) {
                int action = event.getAction();
                switch (action & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        recentlyImage.setTouchMode(TOUCH_MODE.SINGLE);
                        downSingleEvent(event);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (event.getPointerCount() == 2) { // 두손가락 터치를 했을 때
                            recentlyImage.setTouchMode(TOUCH_MODE.MULTI);
                            downMultiEvent(event);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (recentlyImage.getTouchMode() == TOUCH_MODE.SINGLE) {
                            moveSingleEvent(event);
                        } else if (recentlyImage.getTouchMode() == TOUCH_MODE.MULTI) {
                            moveMultiEvent(event);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        recentlyImage.setTouchMode(TOUCH_MODE.NONE);
                        break;
                }
            }


            return true;
        }
    };

    private PointF getMidPoint(MotionEvent e) {

        float x = (e.getX(0) + e.getX(1)) / 2;
        float y = (e.getY(0) + e.getY(1)) / 2;

        return new PointF(x, y);
    }

    private float getDistance(MotionEvent e) {
        float x = e.getX(0) - e.getX(1);
        float y = e.getY(0) - e.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void downSingleEvent(MotionEvent event) {
        recentlyImage.getSavedMatrix().set(recentlyImage.getMatrix());
        recentlyImage.setStartPoint(new PointF(event.getX(), event.getY()));
    }

    private void downMultiEvent(MotionEvent event) {
        recentlyImage.setOldDistance(getDistance(event));
        if (recentlyImage.getOldDistance() > 5f) {
            recentlyImage.getSavedMatrix().set(recentlyImage.getMatrix());
            recentlyImage.setMidPoint(getMidPoint(event));
            double radian = Math.atan2(event.getY() - recentlyImage.getMidPoint().y, event.getX() - recentlyImage.getMidPoint().x);
            recentlyImage.setOldDegree((radian * 180) / Math.PI);
        }
    }

    private void moveSingleEvent(MotionEvent event) {
        recentlyImage.getMatrix().set(recentlyImage.getSavedMatrix());
        recentlyImage.getMatrix().postTranslate(event.getX() - recentlyImage.getStartPoint().x, event.getY() - recentlyImage.getStartPoint().y);
        recentlyImage.getImageView().setImageMatrix(recentlyImage.getMatrix());
    }

    private void moveMultiEvent(MotionEvent event) {
        float newDistance = getDistance(event);
        if (newDistance > 5f) {
            recentlyImage.getMatrix().set(recentlyImage.getSavedMatrix());
            float scale = newDistance / recentlyImage.getOldDistance();
            recentlyImage.getMatrix().postScale(scale, scale, recentlyImage.getMidPoint().x, recentlyImage.getMidPoint().y);

            double nowRadian = Math.atan2(event.getY() - recentlyImage.getMidPoint().y, event.getX() - recentlyImage.getMidPoint().x);
            double nowDegress = (nowRadian * 180) / Math.PI;
            float degree = (float) (nowDegress - recentlyImage.getOldDegree());
            recentlyImage.getMatrix().postRotate(degree, recentlyImage.getMidPoint().x, recentlyImage.getMidPoint().y);


            recentlyImage.getImageView().setImageMatrix(recentlyImage.getMatrix());

        }
    }

}