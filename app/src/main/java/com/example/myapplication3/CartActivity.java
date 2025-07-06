package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ListView listView = findViewById(R.id.cartListView);
        TextView totalView = findViewById(R.id.totalTextView);
        ImageButton backButton = findViewById(R.id.backButton);


        if (Cart.getItems().isEmpty()) {
            totalView.setText("Shopping cart is empty");


        } else {
            ArrayList<String> cartLines = new ArrayList<>();
            for (Map.Entry<Product, Integer> entry : Cart.getItems().entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                cartLines.add(p.name + " x" + qty + " = " + String.format("%.2f", qty * p.price) + " €");
            }

            listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cartLines));

            double total = Cart.getTotal();
            String formattedTotal = String.format("%.2f €", total);
            totalView.setText(formattedTotal);


        }

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Product productToRemove = (Product) Cart.getItems().keySet().toArray()[position];


            new AlertDialog.Builder(CartActivity.this)
                    .setTitle("Delete item")
                    .setMessage("Delete \"" + productToRemove.name + "\" from cart?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Cart.decreaseItem(productToRemove);
                        recreate();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

}

