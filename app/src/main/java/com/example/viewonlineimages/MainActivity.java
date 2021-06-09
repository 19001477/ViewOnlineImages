package com.example.viewonlineimages;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ImageView im;
    final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im = (ImageView) findViewById(R.id.imgViewPhoto);
    }

    public void ImageButton(View view) {
        Thread thread = new Thread((new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap temp = getBitmapFromURL("https://picsum.photos/400/600");
                    mHandler.post(new Runnable() {
                        public void run() {
                            im.setImageBitmap(temp);
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
        thread.start();
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}