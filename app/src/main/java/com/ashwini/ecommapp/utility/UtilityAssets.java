package com.ashwini.ecommapp.utility;

import android.content.Context;
import android.util.Log;

import com.ashwini.ecommapp.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UtilityAssets {
    private static final String TAG = "UtilityAssets";
    public static ArrayList<Product> loadProductsFromAsset(Context context) {
        ArrayList<Product> productList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = context.getAssets().open("ecom_items.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray event_jArry = obj.getJSONArray("ecom_items");
            productList.clear();
            for (int i = 0; i < event_jArry.length(); i++) {
                JSONObject jsonObject = event_jArry.getJSONObject(i);
                Product event = new Product();
                event.setCategory(jsonObject.getString("Category"));
                event.setItemName(jsonObject.getString("Itemname"));
                event.setQuantity(jsonObject.getString("quantity"));
                event.setPrice(jsonObject.getString("price"));
                event.setImageName(jsonObject.getString("imagename"));
                //Add your values in your `ArrayList` as below:
                productList.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productList;
    }


    public static ArrayList<Product> loadCategoryProducts(Context context, String category) {
        ArrayList<Product> productList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = context.getAssets().open("ecom_items.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray event_jArry = obj.getJSONArray("ecom_items");
            productList.clear();
            for (int i = 0; i < event_jArry.length(); i++) {
                JSONObject jsonObject = event_jArry.getJSONObject(i);
                //Log.i(TAG, "loadCategoryProducts: "+jsonObject.getString("category"));
                if(category.equals(jsonObject.getString("category"))){
                    Product event = new Product();
                    event.setCategory(jsonObject.getString("category"));
                    event.setItemName(jsonObject.getString("Itemname"));
                    event.setQuantity(jsonObject.getString("quantity"));
                    event.setPrice(jsonObject.getString("price"));
                    event.setImageName(jsonObject.getString("imagename"));
                    //Add your values in your `ArrayList` as below:
                    productList.add(event);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
