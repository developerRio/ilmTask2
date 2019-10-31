package com.originalstocks.ilmtask.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.originalstocks.ilmtask.R;
import com.originalstocks.ilmtask.model.ImageModel;

import java.util.List;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder> {

    private Context mContext;
    private List<ImageModel> imageModelList;

    public ImageRecyclerAdapter(Context mContext, List<ImageModel> imageModelList) {
        this.mContext = mContext;
        this.imageModelList = imageModelList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.image_list_item, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageModel imageModel = imageModelList.get(position);
        // setting up image via Glide
        Glide.with(mContext)
                .load(imageModel.getImgLink())
                .placeholder(R.drawable.m_one_grade)
                .into(holder.userImageView);

    }

    @Override
    public int getItemCount() {
        return imageModelList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        CircularImageView userImageView;
        MaterialCardView userCardView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            userCardView = itemView.findViewById(R.id.user_card_view);
            userImageView = itemView.findViewById(R.id.user_image_view);
        }
    }

}


