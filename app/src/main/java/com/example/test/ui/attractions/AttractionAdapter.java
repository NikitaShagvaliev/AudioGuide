package com.example.test.ui.attractions;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.AttractionActivity;
import com.example.test.R;

import java.util.List;
import java.util.zip.Inflater;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {

    String data1[], data2[], data3[];
    int images[], gallery_images1[], gallery_images2[], gallery_images3[], audioguides[];
    Context context;
    public AttractionAdapter(Context ct, String[] s1, String[] s2, String[] descriptions, int img[],
                             int[] gallery_img1, int[] gallery_img2, int[] gallery_img3, int[] audios)
    {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = descriptions;
        images = img;
        gallery_images1 = gallery_img1;
        gallery_images2 = gallery_img2;
        gallery_images3 = gallery_img3;
        audioguides = audios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.attraction_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.myImage.setImageResource(images[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AttractionActivity.class);
                intent.putExtra("data1", data1[position]);
                intent.putExtra("data2", data2[position]);
                intent.putExtra("data3", data3[position]);
                intent.putExtra("myImage", images[position]);
                intent.putExtra("gallery_image1", gallery_images1[position]);
                intent.putExtra("gallery_image2", gallery_images2[position]);
                intent.putExtra("gallery_image3", gallery_images3[position]);
                intent.putExtra("audioguide", audioguides[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2;
        ImageView myImage;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.attraction_name);
            myText2 = itemView.findViewById(R.id.attraction_address);
            myImage = itemView.findViewById(R.id.attraction_image);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
