package com.originalstocks.ilmtask.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.originalstocks.ilmtask.Activities.DetailsActivity;
import com.originalstocks.ilmtask.R;
import com.originalstocks.ilmtask.model.UsersModel;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder> {

    private Context mContext;
    private List<UsersModel> usersList;

    public UserRecyclerAdapter(Context mContext, List<UsersModel> usersList) {
        this.mContext = mContext;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.users_list_item, null);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, final int position) {

        final UsersModel usersModel = usersList.get(position);

        holder.userNameTextView.setText(usersModel.getLogin());
        holder.urlTextView.setText(usersModel.getUserURL());
        holder.typeTextView.setText(usersModel.getUserType());

        // setting up image via Glide
        Glide.with(mContext)
                .load(usersModel.getUserImageLink())
                .placeholder(R.drawable.m_one_grade)
                .into(holder.userImageView);

        holder.userCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked on position = " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mContext, DetailsActivity.class);
                i.putExtra("url", usersModel.getUserURL());
                mContext.startActivity(i);
            }
        });

    }



    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        CircularImageView userImageView;
        TextView userNameTextView, urlTextView, typeTextView;
        MaterialCardView userCardView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userImageView = itemView.findViewById(R.id.user_image_view);
            userNameTextView = itemView.findViewById(R.id.user_name_text_view);
            typeTextView = itemView.findViewById(R.id.type_text_view);
            urlTextView = itemView.findViewById(R.id.url_text_view);
            userCardView = itemView.findViewById(R.id.user_card_view);

        }
    }

}
