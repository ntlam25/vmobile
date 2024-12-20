package com.example.btl.adapter;

import static com.example.btl.Cart.textViewTongTien;
import static com.example.btl.CartFragment.textViewTongTien1;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.Cart;
import com.example.btl.CartFragment;
import com.example.btl.R;
import com.example.btl.model.CartHelper;
import com.example.btl.model.CartModel;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    Context context;
    ArrayList<CartModel> gioHangList;

    public CartItemAdapter(Context context, ArrayList<CartModel> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel currentItem = gioHangList.get(position);
        String imageName = currentItem.getImage();
        int imageId = context.getResources().getIdentifier(imageName.substring(0,imageName.lastIndexOf('.')), "drawable", context.getPackageName());
        holder.imageProduct.setImageResource(imageId);
        holder.textViewTitle.setText(currentItem.getName());
        holder.textViewPrice.setText(String.valueOf(currentItem.getPrice()));
        holder.textViewQuantity.setText(String.valueOf(currentItem.getQuantity()));

        holder.imageButtonDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa khỏi giỏ hàng");
            builder.setMessage("Bạn muốn xóa sản phẩm khỏi giỏ hàng?");

            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        gioHangList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);

                        long total = CartHelper.calculateTotal(gioHangList);
                        textViewTongTien.setText(String.valueOf(total) + " vnd");
                        textViewTongTien1.setText(String.valueOf(total) + " vnd");
                        CartHelper.removeFromCart(context,currentItem.getId());
                    }
                }
            });

            builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            // Tạo và hiển thị AlertDialog:
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        holder.buttonMinus.setOnClickListener(view -> {
            if (currentItem.getQuantity() > 1)
            {
                int newQuantity = currentItem.getQuantity() - 1;
                currentItem.setQuantity(newQuantity);
                CartHelper.updateCartItemQuantity(context,currentItem.getId(),newQuantity);
                holder.textViewQuantity.setText(String.valueOf(newQuantity));
                long total = CartHelper.calculateTotal(gioHangList);
                if(context instanceof Cart) {
                    textViewTongTien.setText(String.valueOf(total) + " vnd");
                }else {
                    textViewTongTien1.setText(String.valueOf(total)+ " vnd");
                }
                notifyItemChanged(position);
            }
        });

        holder.buttonPlus.setOnClickListener(view -> {
            int newQuantity = currentItem.getQuantity() + 1;
            currentItem.setQuantity(newQuantity);
            CartHelper.updateCartItemQuantity(context,currentItem.getId(),newQuantity);
            holder.textViewQuantity.setText(String.valueOf(newQuantity));
            long total = CartHelper.calculateTotal(gioHangList);
            if(context instanceof Cart) {
                textViewTongTien.setText(String.valueOf(total) + " vnd");
            }
            else {
                textViewTongTien1.setText(String.valueOf(total) + " vnd");
            }
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textViewTitle, textViewPrice, textViewQuantity;
        ImageButton buttonMinus, buttonPlus,imageButtonDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            buttonMinus = itemView.findViewById(R.id.buttonMinus);
            buttonPlus = itemView.findViewById(R.id.buttPlus);
            imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);
        }
    }
}
