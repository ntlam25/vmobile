package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btl.R;
import com.example.btl.model.Data;
import com.example.btl.model.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {


    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
    }

    public ProductAdapter(@NonNull Context context, int resource, @NonNull Product[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.product_item,null);
        }
        Product pr = getItem(position);
        if (pr != null)
        {
            ImageView img = (ImageView) view.findViewById(R.id.product_image);
            TextView tv1 = (TextView) view.findViewById(R.id.product_title);
            TextView tv2 = (TextView) view.findViewById(R.id.product_price);
            TextView tv3 = (TextView) view.findViewById(R.id.product_description);
            String imageName = pr.getImage();
            int imageId = getContext().getResources().getIdentifier(imageName.substring(0,imageName.lastIndexOf('.')), "drawable", getContext().getPackageName());
            img.setImageResource(imageId);
            tv1.setText(pr.getName());
            tv2.setText("Giá: "+String.valueOf(pr.getPrice()));
            tv3.setText(pr.getDescription());
        }
        return view;
    }
}
