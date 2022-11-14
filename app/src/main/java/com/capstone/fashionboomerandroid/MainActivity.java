package com.capstone.fashionboomerandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.capstone.fashionboomerandroid.image.MatrixImage;
import com.capstone.fashionboomerandroid.image.TOUCH_MODE;
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

    private MatrixImage matrixImage;
    private ImageView ivImage11;
    private ImageView ivImage2;
    private Button button1;
    private Button button2;
    private Button button3;
    private static final String BASE_URL = "http://fashionboomer.tk:8080";

    // 터치 관련 필드
//    private TOUCH_MODE touchMode;
//    private Matrix matrix;      //기존 매트릭스
//    private Matrix savedMatrix; //작업 후 이미지에 매핑할 매트릭스
//
//    private PointF startPoint;  //한손가락 터치 이동 포인트
//
//    private PointF midPoint;    //두손가락 터치 시 중신 포인트
//    private float oldDistance;  //터치 시 두손가락 사이의 거리
//
//    private double oldDegree = 0; // 두손가락의 각도

    private MatrixImage recentlyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matrixImage.setMatrix(new Matrix());
        matrixImage.setSavedMatrix(new Matrix());

        matrixImage.setImageView((ImageView) findViewById(R.id.iv_image));
        ivImage11 = (ImageView) findViewById(R.id.iv_image11);
        ivImage2 = (ImageView) findViewById(R.id.iv_image2);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);


        matrixImage.getImageView().setOnTouchListener(onTouch);
        matrixImage.getImageView().setScaleType(ImageView.ScaleType.MATRIX); // 스케일 타입을 매트릭스로 해줘야 움직인다.

        ivImage11.setOnTouchListener(onTouch);
        ivImage11.setScaleType(ImageView.ScaleType.MATRIX); // 스케일 타입을 매트릭스로 해줘야 움직인다.

        button1.setOnClickListener(this);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recentlyImage = matrixImage;
                recentlyImage.getImageView().bringToFront();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recentlyImage = ivImage11;
                recentlyImage.bringToFront();
            }
        });

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
                        matrixImage.getImageView().setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        }.start();

        new Thread() {
            public void run() {
                Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);

                Retrofit retrofit = builder.build();
                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

                Call<ResponseBody> call = retrofitInterface.downloadImage(8);

                call.enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        InputStream is = response.body().byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        ivImage11.setImageBitmap(bitmap);
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

    // 이미지 컨트롤
    private View.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.equals(recentlyImage)) {
                int action = event.getAction();
                switch (action & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        matrixImage.setTouchMode(TOUCH_MODE.SINGLE);
                        downSingleEvent(event);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (event.getPointerCount() == 2) { // 두손가락 터치를 했을 때
                            touchMode = TOUCH_MODE.MULTI;
                            downMultiEvent(event);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (touchMode == TOUCH_MODE.SINGLE) {
                            moveSingleEvent(event);
                        } else if (touchMode == TOUCH_MODE.MULTI) {
                            moveMultiEvent(event);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        touchMode = TOUCH_MODE.NONE;
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
        savedMatrix.set(matrix);
        startPoint = new PointF(event.getX(), event.getY());
    }

    private void downMultiEvent(MotionEvent event) {
        oldDistance = getDistance(event);
        if (oldDistance > 5f) {
            savedMatrix.set(matrix);
            midPoint = getMidPoint(event);
            double radian = Math.atan2(event.getY() - midPoint.y, event.getX() - midPoint.x);
            oldDegree = (radian * 180) / Math.PI;
        }
    }

    private void moveSingleEvent(MotionEvent event) {
        matrix.set(savedMatrix);
        matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
        recentlyImage.setImageMatrix(matrix);
    }

    private void moveMultiEvent(MotionEvent event) {
        float newDistance = getDistance(event);
        if (newDistance > 5f) {
            matrix.set(savedMatrix);
            float scale = newDistance / oldDistance;
            matrix.postScale(scale, scale, midPoint.x, midPoint.y);

            double nowRadian = Math.atan2(event.getY() - midPoint.y, event.getX() - midPoint.x);
            double nowDegress = (nowRadian * 180) / Math.PI;
            float degree = (float) (nowDegress - oldDegree);
            matrix.postRotate(degree, midPoint.x, midPoint.y);


            recentlyImage.setImageMatrix(matrix);

        }
    }

}