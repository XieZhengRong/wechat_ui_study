package com.ikudot.wechatuistudy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FriendCircleItem {
    public FriendCircleItem() {
    }

    public FriendCircleItem(String userName, String content, String headUrl, int type, String singlePhotoUrl, String publicTime, String sharePlatform) {
        this.userName = userName;
        this.content = content;
        this.headUrl = headUrl;
        this.type = type;
        this.singlePhotoUrl = singlePhotoUrl;
        this.publicTime = publicTime;
        this.sharePlatform = sharePlatform;
    }

    public FriendCircleItem(String userName, String content, String headUrl, int type, String[] fourPhotoUrls, String publicTime, String sharePlatform) {
        this.userName = userName;
        this.content = content;
        this.headUrl = headUrl;
        this.type = type;
        this.multiplePhotoUrls = fourPhotoUrls;
        this.publicTime = publicTime;
        this.sharePlatform = sharePlatform;
    }
    public FriendCircleItem(String userName, String content, String headUrl, int type, Music music, String publicTime, String sharePlatform) {
        this.userName = userName;
        this.content = content;
        this.headUrl = headUrl;
        this.type = type;
        this.music = music;
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
    private String[] multiplePhotoUrls;
    private Music music;

    public static List<FriendCircleItem> generateItemList() {
        List<FriendCircleItem> list = new ArrayList<>();
        list.add(new FriendCircleItem("李白（国服）", "今天不小心喝醉了，大招没有摁出来。", "https://img-blog.csdnimg.cn/20190918140145169.png", 2, "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "两分钟前", "王者荣耀"));
        list.add(new FriendCircleItem("鲁班（村服）", "刚刚对面兰陵王太过分了气得我直接举报", "https://img-blog.csdnimg.cn/20190918140145169.png", 1, "", "四分钟前", "王者荣耀"));
        String[] multiplePhotoUrls = new String[]{"https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg"};
        list.add(new FriendCircleItem("扁鹊（最菜）", "刚刚拖住对面的最终偷塔成功哈哈哈", "https://img-blog.csdnimg.cn/20190918140145169.png", 3, multiplePhotoUrls, "五分钟前", "王者荣耀"));
        multiplePhotoUrls = new String[]{"https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg",};
        list.add(new FriendCircleItem("橘右京", "被李信追得我_(:з」∠)_刀都不见了", "https://img-blog.csdnimg.cn/20190918140145169.png", 4, multiplePhotoUrls, "八分钟前", "王者荣耀"));
        multiplePhotoUrls = new String[]{"https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg",  "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg", "https://mtshop1.meitudata.com/5bc9e7f959be891140.jpg"};
        list.add(new FriendCircleItem("东方·耀", "我能把李白和韩信按在地上摩擦。", "https://img-blog.csdnimg.cn/20190918140145169.png", 4, multiplePhotoUrls, "九分钟前", "王者荣耀"));
        Music music = new Music("甜甜的","汪苏泷、By2","https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1586084379&di=849d0097e022fe031b1f4db5acb9063d&src=http://img.jk51.com/img_jk51/341689302.jpeg","");
        FriendCircleItem musicItem = new FriendCircleItem("老夫子", "今天钓到条大鱼，听首歌庆祝一下哈哈哈。", "https://img-blog.csdnimg.cn/20190918140145169.png", 5, music, "两小时前", "网易云音乐");
        musicItem.setMusic(music);
        list.add(musicItem);

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


    public String[] getMultiplePhotoUrls() {
        return multiplePhotoUrls;
    }

    public void setMultiplePhotoUrls(String[] multiplePhotoUrls) {
        this.multiplePhotoUrls = multiplePhotoUrls;
    }


    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
