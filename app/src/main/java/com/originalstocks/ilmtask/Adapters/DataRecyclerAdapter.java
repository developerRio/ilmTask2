package com.originalstocks.ilmtask.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.originalstocks.ilmtask.R;
import com.originalstocks.ilmtask.model.DataModel;
import com.originalstocks.ilmtask.model.UsersModel;

import java.util.List;

public class DataRecyclerAdapter extends RecyclerView.Adapter<DataRecyclerAdapter.DataViewHolder> {

    private Context mContext;
    private List<DataModel> usersList;

    public DataRecyclerAdapter(Context mContext, List<DataModel> usersList) {
        this.mContext = mContext;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.data_list, null);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        DataModel dataModel = usersList.get(position);
        holder.tittleTextView.setText(dataModel.getTittle());
        holder.bodyTextView.setText(dataModel.getBody());

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        TextView tittleTextView, bodyTextView;
        MaterialCardView userCardView;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            userCardView = itemView.findViewById(R.id.user_card_view);
            tittleTextView = itemView.findViewById(R.id.tittle_text_view);
            bodyTextView = itemView.findViewById(R.id.body_text_view);

        }
    }
}
