package com.example.myapplication3;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView myListView;
    String[] items;
    String[] prices;
    String[] descriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.myListView);
        items = res.getStringArray(R.array.items);
        prices = res.getStringArray(R.array.prices);
        descriptions = res.getStringArray(R.array.descriptions);


        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {
            double price = Double.parseDouble(prices[i].replace("$", ""));
            productList.add(new Product(items[i], price));
        }

        ItemAdapter itemAdapter = new ItemAdapter(this, productList, descriptions);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showDetailActivity
                        = new Intent(getApplicationContext(), DetailActivity.class);
                showDetailActivity.putExtra("com.example.ITEM_POSITION", position);
                startActivity(showDetailActivity);
            }
        });

        ImageButton cartButton = findViewById(R.id.showCartButton);
        
        cartButton.setOnClickListener(v -> {
            Intent showCartActivity = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(showCartActivity);
        });
    }

}