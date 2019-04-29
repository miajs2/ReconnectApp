package com.example.reconnect;

import android.app.AlertDialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private ArrayList<Communication> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    TimelineAdapter(Context context, ArrayList<Communication> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.timeline_interaction, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String date = mData.get(position).date;
        holder.dateView.setText(date);
        String duration = mData.get(position).duration;
        holder.durationView.setText(duration);
        String mode = mData.get(position).type;
        int modeIcon = Helper.getModeIcon(mode);
        holder.modeView.setImageResource(modeIcon);
        holder.commentView.setImageResource(R.drawable.ic_comment_black_24dp);
        holder.myLine.setBackgroundResource(R.drawable.line_bg);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dateView;
        TextView durationView;
        ImageView modeView;
        ImageView commentView;
        FrameLayout myLine;

        ViewHolder(View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.timeline_date);
            durationView = itemView.findViewById(R.id.timeline_duration);
            modeView = itemView.findViewById(R.id.interaction_mode);
            commentView = itemView.findViewById(R.id.interaction_comment);
            myLine = itemView.findViewById(R.id.item_line);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Communication getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
