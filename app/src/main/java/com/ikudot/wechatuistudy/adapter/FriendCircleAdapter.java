package com.ikudot.wechatuistudy.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ikudot.wechatuistudy.FriendCircleActivity;
import com.ikudot.wechatuistudy.R;
import com.ikudot.wechatuistudy.Util.Utils;
import com.ikudot.wechatuistudy.model.FriendCircleItem;
import java.util.List;

public class FriendCircleAdapter extends RecyclerView.Adapter<FriendCircleAdapter.FriendCircleItemHolder> {
    private FriendCircleActivity activity;
    private List<FriendCircleItem> friendCircleItems;
    public FriendCircleAdapter(FriendCircleActivity activity,List<FriendCircleItem> friendCircleItems){
        this.activity = activity;
        this.friendCircleItems = friendCircleItems;
    }
    @NonNull
    @Override
    public FriendCircleItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_circle_item, parent, false);
        return new FriendCircleItemHolder(view);
    }

    @Override
    public int getItemCount() {
        return friendCircleItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FriendCircleItemHolder holder, int position) {
        Glide.with(activity).load(friendCircleItems.get(position).getHeadUrl())  .centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(Utils.dpTpPx(4,activity))))//圆角半径
                .into(holder.userHead);
        Glide.with(activity).load(friendCircleItems.get(position).getSinglePhotoUrl()).into(holder.singlePhoto);
        holder.userName.setText(friendCircleItems.get(position).getUserName());
        holder.content.setText(friendCircleItems.get(position).getContent());
        holder.publicTime.setText(friendCircleItems.get(position).getPublicTime());
        holder.sharePlatform.setText(friendCircleItems.get(position).getSharePlatform());

        holder.more.setOnTouchListener(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.more_popup_window, null, false);
        PopupWindow popupWindow = new PopupWindow(view, Utils.dpTpPx(170,activity), Utils.dpTpPx(50,activity));
        popupWindow.setOutsideTouchable(true);
        int[] location = new int[2];
        holder.more.getLocationInWindow(location);
        holder.more.setOnClickListener(v-> popupWindow.showAsDropDown(v,Gravity.NO_GRAVITY,location[0], location[1  ]));
    }

    static class FriendCircleItemHolder extends RecyclerView.ViewHolder{
        ImageView userHead;
        TextView userName;
        TextView content;
        ImageView singlePhoto;
        TextView publicTime;
        TextView sharePlatform;
        LinearLayout more;

        public FriendCircleItemHolder(@NonNull View itemView) {
            super(itemView);
            userHead = itemView.findViewById(R.id.friend_item_user_head);
            userName = itemView.findViewById(R.id.friend_item_user_name);
            content = itemView.findViewById(R.id.friend_item_content);
            singlePhoto = itemView.findViewById(R.id.friend_item_single_photo);
            publicTime = itemView.findViewById(R.id.friend_item_public_time);
            sharePlatform = itemView.findViewById(R.id.friend_item_share_platform);
            more = itemView.findViewById(R.id.friend_item_more_button);
        }
    }

    public void updateList(List<FriendCircleItem> friendCircleItems){
        this.friendCircleItems = friendCircleItems;
        notifyDataSetChanged();
    }
}
