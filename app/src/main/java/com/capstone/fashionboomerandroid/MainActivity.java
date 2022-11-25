package com.capstone.fashionboomerandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
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

    private final int GET_GALLERY_IMAGE = 200;

    private DataModel.PageData closets;
    private final List<MatrixImage> matrixImages = new ArrayList<>();
    private final List<ImageView> myImageViews = new ArrayList<>();
    private final List<MatrixImage> myNukkiImageViews = new ArrayList<>();
    private final List<ImageView> likeImageViews = new ArrayList<>();
    private final List<MatrixImage> likeNukkiImageViews = new ArrayList<>();
    private ImageView maleImage;
    private ImageView femaleImage;
    private ImageView myBodyImage;

    private ConstraintLayout closetNukkiLayout;
    private LinearLayout likeClosetLayout;
    private LinearLayout myClosetLayout;

    private static final String BASE_URL = "http://fashionboomer.tk:8080";
    // AbsolutePath : /storage/emulated/0/
    private final String rawPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FashionBoomer/raw";
    private final String nukkiPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FashionBoomer/nukki";

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
        myBodyImage = (ImageView) findViewById(R.id.myBodyImage);

        Button button1 = (Button) findViewById(R.id.button1);
        Button maleButton = (Button) findViewById(R.id.maleButton);
        Button femaleButton = (Button) findViewById(R.id.femaleButton);
        Button myBodyButton = (Button) findViewById(R.id.myBodyButton);
        Button myClosetButton = (Button) findViewById(R.id.myClosetButton);
        Button likeClosetButton = (Button) findViewById(R.id.likeClosetButton);

        closetNukkiLayout = (ConstraintLayout) findViewById(R.id.closetNukkiLayout);
        myClosetLayout = (LinearLayout) findViewById(R.id.myClosetLayout);
        likeClosetLayout = (LinearLayout) findViewById(R.id.likeClosetLayout);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);

        // 옷장 리스트
        Retrofit retrofitListJson = builder.addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterfaces = retrofitListJson.create(RetrofitInterface.class);

        // 내 옷 이미지 먼저 보여줌
        myClosetLayout.setVisibility(View.VISIBLE);
        likeClosetLayout.setVisibility(View.GONE);

        retrofitInterfaces.getMemberClosets(memberId.intValue(), 1, 30).enqueue(new Callback<DataModel.PageData>() {
            @Override
            public void onResponse(@NonNull Call<DataModel.PageData> call, @NonNull Response<DataModel.PageData> response) {
//                myToast.show();
                if (response.isSuccessful()) {
                    Log.d("getMemberClosets", "getMemberClosets");
                    closets = new DataModel.PageData(response.body());

                    for (int i = 0; i < closets.getData().size(); i++) {
                        // 누끼 이미지 뷰
                        likeNukkiImageViews.add(new MatrixImage());

                        likeNukkiImageViews.get(i).setMatrix(new Matrix());
                        likeNukkiImageViews.get(i).setSavedMatrix(new Matrix());

                        likeNukkiImageViews.get(i).setImageView(new ImageView(getBaseContext()));
                        likeNukkiImageViews.get(i).getImageView().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1600));
                        Glide.with(mainActivity).load(BASE_URL + "/v11/closets/images/nukki/" + closets.getData().get(i).getId()).into(likeNukkiImageViews.get(i).getImageView());

                        // 누끼 이미지 터치 이벤트
                        likeNukkiImageViews.get(i).getImageView().setOnTouchListener(onTouch);
                        likeNukkiImageViews.get(i).getImageView().setScaleType(ImageView.ScaleType.MATRIX); // 스케일 타입을 매트릭스로 해줘야 움직인다.

                        likeNukkiImageViews.get(i).getImageView().setVisibility(View.INVISIBLE);

                        // 뷰 추가
                        closetNukkiLayout.addView(likeNukkiImageViews.get(i).getImageView());


                        // 일반 이미지 뷰
                        // 좋아요 누를때 보임
                        likeClosetLayout.setVisibility(View.GONE);

                        likeImageViews.add(new ImageView(getBaseContext()));
                        likeImageViews.get(i).setLayoutParams(new ViewGroup.LayoutParams(400, 400));
                        Glide.with(mainActivity).load(BASE_URL + "/v11/closets/images/" + closets.getData().get(i).getId()).into(likeImageViews.get(i));
                        int finalI = i;

                        // 일반 이미지 클릭 이벤트
                        likeImageViews.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recentlyImage = likeNukkiImageViews.get(finalI);
                                recentlyImage.getImageView().setVisibility(View.VISIBLE);
                                recentlyImage.getImageView().bringToFront();
                            }
                        });

                        likeClosetLayout.addView(likeImageViews.get(i));
                    }
                    // 뷰 추가
                    recentlyImage = likeNukkiImageViews.get(0);
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
                // 임시로 액티비 이동 버튼으로 사용
//                recentlyImage.getImageView().setVisibility(View.GONE);
                Intent intent = new Intent(mainActivity, PostActivity.class);
                startActivity(intent);
            }
        });
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                femaleImage.setVisibility(View.GONE);
                myBodyImage.setVisibility(View.GONE);
                maleImage.setVisibility(View.VISIBLE);
            }
        });
        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maleImage.setVisibility(View.GONE);
                myBodyImage.setVisibility(View.GONE);
                femaleImage.setVisibility(View.VISIBLE);
            }
        });
        myBodyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maleImage.setVisibility(View.GONE);
                femaleImage.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                myBodyImage.setVisibility(View.VISIBLE);
            }
        });
        myClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeClosetLayout.setVisibility(View.GONE);
                myClosetLayout.setVisibility(View.VISIBLE);
            }
        });

        likeClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClosetLayout.setVisibility(View.GONE);
                likeClosetLayout.setVisibility(View.VISIBLE);
            }
        });

        // permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        // 내 옷 이미지 목록 뷰, 이벤트
        List<String> rawImageNames = getFileNameList(rawPath);
        List<String> nukkiImageNames = getFileNameList(nukkiPath);

        for (int i = 0; i < rawImageNames.size(); i++) {
            // 누끼 이미지 뷰
            myNukkiImageViews.add(new MatrixImage());

            myNukkiImageViews.get(i).setMatrix(new Matrix());
            myNukkiImageViews.get(i).setSavedMatrix(new Matrix());

            myNukkiImageViews.get(i).setImageView(new ImageView(getBaseContext()));
            myNukkiImageViews.get(i).getImageView().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1600));
            myNukkiImageViews.get(i).getImageView().setImageBitmap(BitmapFactory.decodeFile(nukkiPath + "/" + nukkiImageNames.get(i)));

            // 누끼 이미지 터치 이벤트
            myNukkiImageViews.get(i).getImageView().setOnTouchListener(onTouch);
            myNukkiImageViews.get(i).getImageView().setScaleType(ImageView.ScaleType.MATRIX); // 스케일 타입을 매트릭스로 해줘야 움직인다.

            myNukkiImageViews.get(i).getImageView().setVisibility(View.INVISIBLE);

            // 뷰 추가
            closetNukkiLayout.addView(myNukkiImageViews.get(i).getImageView());


            // 일반 이미지 뷰
            myImageViews.add(new ImageView(getBaseContext()));
            myImageViews.get(i).setLayoutParams(new ViewGroup.LayoutParams(400, 400));
            myImageViews.get(i).setImageBitmap(BitmapFactory.decodeFile(rawPath + "/" + rawImageNames.get(i)));
            int finalI = i;

            // 일반 이미지 클릭 이벤트
            myImageViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recentlyImage = myNukkiImageViews.get(finalI);
                    recentlyImage.getImageView().setVisibility(View.VISIBLE);
                    recentlyImage.getImageView().bringToFront();
                }
            });

            myClosetLayout.addView(myImageViews.get(i));
        }

    }

    // 파일 목록 불러오기
    private File[] getFileList(String path) {
        File directory = new File(path);

        return directory.listFiles();
    }

    // 파일 이름 목록 불러오기
    private List<String> getFileNameList(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();

        List<String> filesNameList = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            filesNameList.add(files[i].getName());
        }

        return filesNameList;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    myBodyImage.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

