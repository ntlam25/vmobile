package com.example.btl.model;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartHelper {

    private static final String PREFS_NAME = "cart_prefs";
    private static final String CART_KEY = "cart_items";

    public static void addToCart(Context context, CartModel product) {
        ArrayList<CartModel> cart = getCartItems(context);
        boolean productExists = false;

        for (CartModel item : cart) {
            if (item.getId() == product.getId()) {
                int currentQuantity = item.getQuantity();
                item.setQuantity(currentQuantity + product.getQuantity());
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            cart.add(product);
        }

        saveCartItems(context, cart);
    }

    public static void updateCartItemQuantity(Context context, int productId, int quantity) {
        ArrayList<CartModel> cart = getCartItems(context);
        boolean isUpdated = false;

        // Tìm sản phẩm trong giỏ hàng dựa trên id và cập nhật số lượng
        for (CartModel item : cart) {
            if (item.getId() == productId) {
                item.setQuantity(quantity);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            // Lưu lại danh sách giỏ hàng mới vào SharedPreferences
            saveCartItems(context, cart);
        }
    }
    public static ArrayList<CartModel> getCartItems(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(CART_KEY, "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<CartModel>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    private static void saveCartItems(Context context, ArrayList<CartModel> cart) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CART_KEY, new Gson().toJson(cart));
        editor.apply();
    }
    public static void removeFromCart(Context context, int productId) {
        ArrayList<CartModel> cart = getCartItems(context);
        // Tìm vị trí của sản phẩm trong giỏ hàng dựa trên ID sản phẩm
        int positionToRemove = -1;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId() == productId) {
                positionToRemove = i;
                break;
            }
        }
        if (positionToRemove != -1) {
            cart.remove(positionToRemove);
            saveCartItems(context, cart);
        }
    }
    public static long calculateTotal(ArrayList<CartModel> cartItems) {
        long total = 0;
        for (CartModel item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
    public static void clearCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
