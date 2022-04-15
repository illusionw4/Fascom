package com.example.fascom.utills;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fascom.adapter.MyCartAdap;
import com.example.fascom.model.MyCartModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class prefConfig {

    public static ArrayList<MyCartModel> getFoodOrderList(Context context) {
        if (context == null)
            return new ArrayList<>();

        SharedPreferences preferences = context.getSharedPreferences("myDetails", Context.MODE_PRIVATE);
        String jsonString = preferences.getString("foodCartList", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MyCartModel>>() {}.getType();

        ArrayList<MyCartModel> list;
        list = gson.fromJson(jsonString, type);

        if (list == null)
            list = new ArrayList<>();
        return list;

    }
    public static void clearFoodOrderList(Context context) {
        List<MyCartModel> list = new ArrayList<>();


    }
}