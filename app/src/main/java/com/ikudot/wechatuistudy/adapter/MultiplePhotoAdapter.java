package com.ikudot.wechatuistudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ikudot.wechatuistudy.R;

import java.util.List;

public class MultiplePhotoAdapter extends RecyclerView.Adapter<MultiplePhotoAdapter.ViewHolder> {
    private Context context;
    private List<String> dataList;

    public MultiplePhotoAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_photo_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.multiple_photo_item);
        }
    }

    public void update(List<String> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
