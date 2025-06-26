package com.example.myapplication3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent in = getIntent();
        int position = in.getIntExtra("com.example.ITEM_POSITION", -1);

        if (position > -1) {
            int pic = getImage(position);
            ImageView img = (ImageView) findViewById(R.id.imageView);
            scaleImg(img, pic);
        }

    }

    private int getImage(int position) {
        switch (position) {
            case 0: return R.drawable.peach;
            case 1: return R.drawable.tomato;
            case 2: return R.drawable.carrot;
            default: return -1;
        }
    }

    private void scaleImg(ImageView img, int pic) {

      Display screen = getWindowManager().getDefaultDisplay();
      BitmapFactory.Options options = new BitmapFactory.Options();

      options.inJustDecodeBounds = true;
      BitmapFactory.decodeResource(getResources(), pic, options);

      int imgWidth = options.outWidth;
      int screenWidth = screen.getWidth();

      if (imgWidth > screenWidth) {
          int ratio = Math.round( (float)imgWidth / (float)screenWidth );
          options.inSampleSize = ratio;
      }
      options.inJustDecodeBounds = false;
       Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
       img.setImageBitmap(scaledImg);
    }
}