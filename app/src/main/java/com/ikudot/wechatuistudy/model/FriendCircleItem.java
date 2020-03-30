package com.ikudot.wechatuistudy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FriendCircleItem {
    public FriendCircleItem(){}
    public FriendCircleItem(String userName, String content, String headUrl, int type, String singlePhotoUrl, String publicTime, String sharePlatform) {
        this.userName = userName;
        this.content = content;
        this.headUrl = headUrl;
        this.type = type;
        this.singlePhotoUrl = singlePhotoUrl;
        this.publicTime = publicTime;
        this.sharePlatform = sharePlatform;
    }

    private String userName;//用户名
    private String content;//文字内容
    private String headUrl;//用户头像Url
    /**
     * 朋友圈类型
     * 1:纯文字
     * 2:单图加文字
     * 3:四张图加文字
     * 4:2~9张图加文字（除4张图外）
     * 5:音乐加文字
     * 6:视频加文字
     */
    private int type;
    private String singlePhotoUrl;//单图url
    private String publicTime;//发布时间
    private String sharePlatform;//分享平台

    public static List<FriendCircleItem> generateItemList(){
        List<FriendCircleItem> list = new ArrayList<>();
        while (list.size() < 20) {
            int size = list.size();
            list.add(new FriendCircleItem("用户"+size,"今天天气很好"+size,"https://img-blog.csdnimg.cn/20190918140145169.png",2,"https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg","一小时前","网易云音乐"));
        }
        return list;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSinglePhotoUrl() {
        return singlePhotoUrl;
    }

    public void setSinglePhotoUrl(String singlePhotoUrl) {
        this.singlePhotoUrl = singlePhotoUrl;
    }

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    public String getSharePlatform() {
        return sharePlatform;
    }

    public void setSharePlatform(String sharePlatform) {
        this.sharePlatform = sharePlatform;
    }

    @Override
    public String toString() {
        return "FriendCircleItem{" +
                "userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", type=" + type +
                ", singlePhotoUrl='" + singlePhotoUrl + '\'' +
                ", publicTime='" + publicTime + '\'' +
                ", sharePlatform='" + sharePlatform + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendCircleItem that = (FriendCircleItem) o;
        return type == that.type &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(content, that.content) &&
                Objects.equals(headUrl, that.headUrl) &&
                Objects.equals(singlePhotoUrl, that.singlePhotoUrl) &&
                Objects.equals(publicTime, that.publicTime) &&
                Objects.equals(sharePlatform, that.sharePlatform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, content, headUrl, type, singlePhotoUrl, publicTime, sharePlatform);
    }
}
