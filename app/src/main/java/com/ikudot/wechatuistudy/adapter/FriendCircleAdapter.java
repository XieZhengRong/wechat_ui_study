package com.ikudot.wechatuistudy.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ikudot.wechatuistudy.FriendCircleActivity;
import com.ikudot.wechatuistudy.R;
import com.ikudot.wechatuistudy.Util.Utils;
import com.ikudot.wechatuistudy.model.FriendCircleItem;
import com.ikudot.wechatuistudy.model.Music;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FriendCircleAdapter extends RecyclerView.Adapter<FriendCircleAdapter.FriendCircleItemHolder> {
    private FriendCircleActivity activity;
    private List<FriendCircleItem> friendCircleItems;

    public FriendCircleAdapter(FriendCircleActivity activity, List<FriendCircleItem> friendCircleItems) {
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
        FriendCircleItem friendCircleItem = friendCircleItems.get(position);
        Glide.with(activity).load(friendCircleItems.get(position).getHeadUrl()).centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(Utils.dpTpPx(6, activity))))//圆角半径
                .into(holder.userHead);

        holder.userName.setText(friendCircleItem.getUserName());
        holder.content.setText(friendCircleItem.getContent());
        holder.publicTime.setText(friendCircleItem.getPublicTime());
        holder.sharePlatform.setText(friendCircleItem.getSharePlatform());
        switch (friendCircleItem.getType()) {
            case 1: {
                //纯文字朋友圈
                holder.singlePhoto.setVisibility(View.GONE);
                holder.multipleRecyclerView.setVisibility(View.GONE);
                holder.musicLayout.setVisibility(View.GONE);
            }
            break;
            case 2: {
                //单图朋友圈
                Glide.with(activity).load(friendCircleItem.getSinglePhotoUrl()).into(holder.singlePhoto);
                holder.singlePhoto.setVisibility(View.VISIBLE);
                holder.multipleRecyclerView.setVisibility(View.GONE);
                holder.musicLayout.setVisibility(View.GONE);
            }
            break;
            case 3: {
                //四张图朋友圈
                //1. 加载图片, 由于ImageView是centerCrop，必须指定Target.SIZE_ORIGINAL，禁止Glide裁剪图片；
                // 这样我就能拿到原始图片的Matrix，才能有完美的过渡效果

                Object[] multiplePhotoUrls = friendCircleItem.getMultiplePhotoUrls();
                List<Object> list = Arrays.asList(multiplePhotoUrls);
                GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
                MultiplePhotoAdapter adapter = new MultiplePhotoAdapter(activity, list);
                holder.multipleRecyclerView.setLayoutManager(layoutManager);
                holder.multipleRecyclerView.setAdapter(adapter);


                holder.singlePhoto.setVisibility(View.GONE);
                holder.multipleRecyclerView.setVisibility(View.VISIBLE);
                holder.musicLayout.setVisibility(View.GONE);


            }
            break;
            case 4: {
                //多图图朋友圈（2~9张除4张外）
                Object[] multiplePhotoUrls = friendCircleItem.getMultiplePhotoUrls();
                List<Object> list = Arrays.asList(multiplePhotoUrls);
                GridLayoutManager layoutManager = new GridLayoutManager(activity, 3);
                MultiplePhotoAdapter adapter = new MultiplePhotoAdapter(activity, list);
                holder.multipleRecyclerView.setLayoutManager(layoutManager);
                holder.multipleRecyclerView.setAdapter(adapter);

                holder.multipleRecyclerView.setOnTouchListener(activity);
                holder.singlePhoto.setVisibility(View.GONE);
                holder.multipleRecyclerView.setVisibility(View.VISIBLE);
                holder.musicLayout.setVisibility(View.GONE);
            }
            break;
            case 5: {
                //音乐+文字朋友圈
                TextView musicName = holder.musicLayout.findViewById(R.id.music_name);
                TextView musicAuthor = holder.musicLayout.findViewById(R.id.music_author);
                ImageView musicImage = holder.musicLayout.findViewById(R.id.music_image);
                Music music = friendCircleItem.getMusic();
                musicName.setText(music.getName());
                musicAuthor.setText(music.getAuthor());
                Glide.with(musicImage).load(music.getImageUrl()).into(musicImage);


                holder.musicLayout.setOnTouchListener(activity);
                holder.singlePhoto.setVisibility(View.GONE);
                holder.multipleRecyclerView.setVisibility(View.GONE);
                holder.musicLayout.setVisibility(View.VISIBLE);
            }
            break;

        }

        holder.more.setOnTouchListener(activity);
        holder.userHead.setOnTouchListener(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.more_popup_window, null, false);
        PopupWindow popupWindow = new PopupWindow(view, Utils.dpTpPx(170, activity), Utils.dpTpPx(43, activity));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.FriendCirclePopUpWindow);
        Point point = Utils.getScreenSize(holder.itemView.getContext());
        holder.more.setOnClickListener(v -> popupWindow.showAsDropDown(holder.itemView, point.x - Utils.dpTpPx(210 + 8, holder.itemView.getContext()), -Utils.dpTpPx(46f, holder.itemView.getContext())));
    }

    static class FriendCircleItemHolder extends RecyclerView.ViewHolder {
        ImageView userHead;
        TextView userName;
        TextView content;
        ImageView singlePhoto;
        TextView publicTime;
        TextView sharePlatform;
        LinearLayout more;
        LinearLayout musicLayout;
        RecyclerView multipleRecyclerView;

        public FriendCircleItemHolder(@NonNull View itemView) {
            super(itemView);
            userHead = itemView.findViewById(R.id.friend_item_user_head);
            userName = itemView.findViewById(R.id.friend_item_user_name);
            content = itemView.findViewById(R.id.friend_item_content);
            singlePhoto = itemView.findViewById(R.id.friend_item_single_photo);
            publicTime = itemView.findViewById(R.id.friend_item_public_time);
            sharePlatform = itemView.findViewById(R.id.friend_item_share_platform);
            multipleRecyclerView = itemView.findViewById(R.id.multiple_photo_recycler_view);
            more = itemView.findViewById(R.id.friend_item_more_button);
            musicLayout = itemView.findViewById(R.id.music_layout);
        }
    }

    public void updateList(List<FriendCircleItem> friendCircleItems) {
        this.friendCircleItems = friendCircleItems;
        notifyDataSetChanged();
    }

    public static class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView) {
            //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
            Glide.with(imageView).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round).override(Target.SIZE_ORIGINAL)).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            try {
                return Glide.with(context).downloadOnly().load(uri).submit().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
