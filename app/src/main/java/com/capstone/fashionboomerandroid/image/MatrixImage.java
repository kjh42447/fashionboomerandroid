package com.capstone.fashionboomerandroid.image;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.widget.ImageView;

import com.capstone.fashionboomerandroid.MainActivity;

public class MatrixImage {

    private ImageView imageView;

    private TOUCH_MODE touchMode;
    private Matrix matrix;      //기존 매트릭스
    private Matrix savedMatrix; //작업 후 이미지에 매핑할 매트릭스

    private PointF startPoint;  //한손가락 터치 이동 포인트

    private PointF midPoint;    //두손가락 터치 시 중신 포인트
    private float oldDistance;  //터치 시 두손가락 사이의 거리

    private double oldDegree = 0; // 두손가락의 각도

    public ImageView getImageView() {
        return imageView;
    }

    public TOUCH_MODE getTouchMode() {
        return touchMode;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public Matrix getSavedMatrix() {
        return savedMatrix;
    }

    public PointF getStartPoint() {
        return startPoint;
    }

    public PointF getMidPoint() {
        return midPoint;
    }

    public float getOldDistance() {
        return oldDistance;
    }

    public double getOldDegree() {
        return oldDegree;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setTouchMode(TOUCH_MODE touchMode) {
        this.touchMode = touchMode;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public void setSavedMatrix(Matrix savedMatrix) {
        this.savedMatrix = savedMatrix;
    }

    public void setStartPoint(PointF startPoint) {
        this.startPoint = startPoint;
    }

    public void setMidPoint(PointF midPoint) {
        this.midPoint = midPoint;
    }

    public void setOldDistance(float oldDistance) {
        this.oldDistance = oldDistance;
    }

    public void setOldDegree(double oldDegree) {
        this.oldDegree = oldDegree;
    }
}
