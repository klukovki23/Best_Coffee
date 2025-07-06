package com.example.myapplication3;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private static final HashMap<Product, Integer> items = new HashMap<>();

    public static void addItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        int currentQty = items.getOrDefault(product, 0);
        items.put(product, currentQty + quantity);
    }

    public static void decreaseItem(Product product) {
        if (items.containsKey(product)) {
            int qty = items.get(product);
            if (qty > 1) {
                items.put(product, qty - 1);
            } else {
                items.remove(product);
            }
        }
    }


    public static HashMap<Product, Integer> getItems() {
        return items;
    }

    public static double getTotal() {
        double sum = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            sum += entry.getKey().price * entry.getValue();
        }
        return sum;
    }
}
