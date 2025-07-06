package com.example.myapplication3;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    String[] items;
    String[] prices;

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


        items = getResources().getStringArray(R.array.items);
        prices = getResources().getStringArray(R.array.prices);

        Intent in = getIntent();
        int position = in.getIntExtra("com.example.ITEM_POSITION", -1);

        if (position > -1) {
            int pic = getImage(position);
            ImageView img = (ImageView) findViewById(R.id.imageView);
            scaleImg(img, pic);


            TextView nameView = findViewById(R.id.productName);
            TextView priceView = findViewById(R.id.productPrice);

            nameView.setText(items[position]);
            priceView.setText(prices[position]);

            Button addBtn = findViewById(R.id.addToCartButton);
            addBtn.setOnClickListener(v -> {
                double price = Double.parseDouble(prices[position].replace("$", ""));
                Product p = new Product(items[position], price);
                Cart.addItem(p, 1);
                Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();

            });
        }

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private int getImage(int position) {
        switch (position) {
            case 0: return R.drawable.matcha;
            case 1: return R.drawable.tea;
            case 2: return R.drawable.mocha;
            case 3: return R.drawable.espresso;
            case 4: return R.drawable.brownie;

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