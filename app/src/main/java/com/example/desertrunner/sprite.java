package com.example.desertrunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class sprite {
    private Bitmap image;
    public int x;
    public int y;
    public int width;
    public int height;
    public int xR;
    public sprite(Bitmap img) {
        image = img;
        width=image.getWidth();
        height=image.getHeight();



    }

    public int xL() {
        return x-(width/2);
    }
    public int xR() {
        return x+(width/2);
    }
    public int yT() {
        return y-(height/2);
    }
    public int yB() {
        return y+(height/2);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

    }
    public void scale(int s) {
        image = Bitmap.createScaledBitmap(image,image.getWidth()*s,image.getHeight()*s,false);
        width=image.getWidth();
        height=image.getHeight();
    }
    public void y_scale(int s) {
        image = Bitmap.createScaledBitmap(image,image.getWidth(),image.getHeight()*s,false);
        width=image.getWidth();
        height=image.getHeight();
    }
    public void x_scale(int s) {
        image = Bitmap.createScaledBitmap(image,image.getWidth()*s,image.getHeight(),false);
        width=image.getWidth();
        height=image.getHeight();
    }


}

