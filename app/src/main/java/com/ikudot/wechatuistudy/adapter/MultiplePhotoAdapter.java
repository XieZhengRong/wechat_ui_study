package com.ikudot.wechatuistudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ikudot.wechatuistudy.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;

import java.util.Arrays;
import java.util.List;

public class MultiplePhotoAdapter extends RecyclerView.Adapter<MultiplePhotoAdapter.ViewHolder> {
    private Context context;
    private List<Object> dataList;

    public MultiplePhotoAdapter(Context context, List<Object> dataList) {
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
        Glide.with(holder.imageView).load(dataList.get(position)).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round)
                .override(Target.SIZE_ORIGINAL))
                .into(holder.imageView);

        holder.imageView.setOnClickListener(v ->
                new XPopup.Builder(holder.itemView.getContext()).asImageViewer(holder.imageView, position, dataList, (popupView, position1) -> {
            RecyclerView rv  = (RecyclerView) holder.itemView.getParent();
            popupView.updateSrcView(rv.getChildAt(position1).findViewById(R.id.multiple_photo_item));

        }, new FriendCircleAdapter.ImageLoader())
                .isShowSaveButton(false).show());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.multiple_photo_item);
        }
    }

    public void update(List<Object> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
