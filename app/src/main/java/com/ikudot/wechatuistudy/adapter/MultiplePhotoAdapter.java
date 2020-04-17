package com.ikudot.wechatuistudy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ikudot.wechatuistudy.R;
import com.ikudot.wechatuistudy.Util.Utils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiplePhotoAdapter extends RecyclerView.Adapter<MultiplePhotoAdapter.ViewHolder> {
    private Context context;
    private List<Object> dataList;
    private float downX;
    private float downY;

    public MultiplePhotoAdapter(Context context, List<Object> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_photo_item, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.imageView).load(dataList.get(position)).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round)
                .override(Target.SIZE_ORIGINAL))
                .into(holder.imageView);

        holder.imageView.setOnClickListener(v ->
                new XPopup.Builder(holder.itemView.getContext()).asImageViewer(holder.imageView, position, dataList, (popupView, position1) -> {
                    RecyclerView rv = (RecyclerView) holder.itemView.getParent();
                    popupView.updateSrcView(rv.getChildAt(position1).findViewById(R.id.multiple_photo_item));

                }, new FriendCircleAdapter.ImageLoader())
                        .isShowSaveButton(false).show());
        holder.imageView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    downY = event.getRawY();
                    downX = event.getRawX();
                }
                break;
            }
            return false;
        });
        List<String> dataList = new ArrayList<>();
        dataList.add("收藏");
        dataList.add("编辑");
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupWindow(v);

                return true;
            }

            private void showPopupWindow(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.long_click_popupwindow, null, false);
                RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new RecyclerView.Adapter() {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        return new PopupHolder(LayoutInflater.from(context).inflate(R.layout.popup_text, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                        PopupHolder popupHolder = (PopupHolder) holder;
                        popupHolder.textView.setText(dataList.get(position));
                    }

                    @Override
                    public int getItemCount() {
                        return dataList.size();
                    }

                    class PopupHolder extends RecyclerView.ViewHolder {
                        TextView textView;

                        PopupHolder(@NonNull View itemView) {
                            super(itemView);
                            textView = itemView.findViewById(R.id.text);
                        }
                    }
                });

                PopupWindow popupWindow = new PopupWindow(view, Utils.dpTpPx(90, context), Utils.dpTpPx(100, context));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.white_bg));
                popupWindow.setElevation(30);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true); // pop 显示时， 不让外部 view 响应点击事件
                popupWindow.setOnDismissListener(() -> v.setForeground(null));
                v.setForeground(context.getResources().getDrawable(R.drawable.height_black_mask_bg));
                Point point = Utils.getScreenSize(v.getContext());
                //计算手指按下右边是否够空间显示
                if (downX + popupWindow.getWidth() >= point.x) {
                    popupWindow.setAnimationStyle(R.style.LongClickLeftPopUpWindow);
                    popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (int) downX - popupWindow.getWidth(), (int) downY - popupWindow.getHeight() / 5 * 6);
                } else {
                    popupWindow.setAnimationStyle(R.style.LongClickPopUpWindow);
                    popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (int) downX, (int) downY - popupWindow.getHeight() / 5 * 6);
                }
            }
        });
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

    public void update(List<Object> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
