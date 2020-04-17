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
     * 7.文字加网页链接
     */
    private int type;
    private String singlePhotoUrl;//单图url
    private String publicTime;//发布时间
    private String sharePlatform;//分享平台
    private String[] multiplePhotoUrls;
    private Music music;

    public LinkItem getLinkItem() {
        return linkItem;
    }

    public void setLinkItem(LinkItem linkItem) {
        this.linkItem = linkItem;
    }

    private LinkItem linkItem;

    public static List<FriendCircleItem> generateItemList() {
        List<FriendCircleItem> list = new ArrayList<>();
        list.add(new FriendCircleItem("李白（国服）", "今天不小心喝醉了，大招没有摁出来。", "https://wx4.sinaimg.cn/thumbnail/006jldApgy1gdi7cs1fbyj30cv0dh75v.jpg", 2, "https://ww1.sinaimg.cn/orj360/005JOToagy1gdvvbebk88j31jk0s97jw.jpg", "两分钟前", "王者荣耀"));
        String[] multiplePhotoUrls = new String[]{"https://ww4.sinaimg.cn/thumb150/005Nkwnyly1gdwrqjhy0fj30ro0x6dsa.jpg", "https://ww2.sinaimg.cn/thumb150/005Nkwnyly1gdwrqjukvej30ro0x6dt5.jpg", "https://ww1.sinaimg.cn/thumb150/005Nkwnyly1gdwrqk6n63j30ro0urnc9.jpg", "https://ww1.sinaimg.cn/thumb150/005Nkwnyly1gdwrqkik9uj30ro0s24do.jpg", "https://ww1.sinaimg.cn/thumb150/005Nkwnyly1gdwrql2gfcj30ro0shq9n.jpg", "https://ww3.sinaimg.cn/thumb150/005Nkwnyly1gdwrqldly0j30ro0ufn3u.jpg", "https://ww2.sinaimg.cn/thumb150/005Nkwnyly1gdwrqlm0b4j30u00u647m.jpg", "https://ww3.sinaimg.cn/thumb150/005Nkwnyly1gdwrqlxhwgj30u00u67d2.jpg", "https://ww4.sinaimg.cn/thumb150/005Nkwnyly1gdwrv7eco2j30u00u6wk9.jpg"};

        list.add(new FriendCircleItem("李白（国服）", "王者荣耀cp情侣头像\n" +
                "[心]我李白王昭君\n" +
                "[心]瑶云中君\n" +
                "[心]刘备孙尚香\n" +
                "[心]娜可露露明世隐\n" +
                "以你之名，冠我之姓，一生一世，不离不弃。", "https://wx4.sinaimg.cn/thumbnail/006jldApgy1gdi7cs1fbyj30cv0dh75v.jpg", 4, multiplePhotoUrls, "两分钟前", "王者荣耀"));
        list.add(new FriendCircleItem("鲁班（村服）", "刚刚对面兰陵王太过分了气得我直接举报", "https://ww1.sinaimg.cn/thumb150/0086J84Mgy1gdi1yhkxxtj30fq0fqq5o.jpg", 1, "", "四分钟前", "王者荣耀"));
        multiplePhotoUrls = new String[]{"https://ww1.sinaimg.cn/orj360/006dCke7gy1gdlftjhuh9j31my0u0kjn.jpg", "https://ww3.sinaimg.cn/thumb150/006dCke7gy1gdlftidvugj30ku4ptb2c.jpg", "https://ww2.sinaimg.cn/thumb150/006dCke7gy1gdlftd16e0g30hs0a0npk.jpg", "https://ww3.sinaimg.cn/thumb150/006dCke7gy1gdlftgy7ndg30qo0f0kjs.jpg", "https://ww4.sinaimg.cn/thumb150/006dCke7gy1gdlftgspk9g30qo0f0kjr.jpg", "https://ww2.sinaimg.cn/thumb150/006dCke7gy1gdlftjoidrg30qo0f0b2g.jpg", "https://ww4.sinaimg.cn/thumb150/006dCke7gy1gdlftb8nt7g30qo0f0hdz.jpg", "https://ww3.sinaimg.cn/orj360/006ZBbNLgy1gdvnumys8zj30u01g0kjo.jpg", "https://ww2.sinaimg.cn/thumb150/006dCke7gy1gdlft826w5g30qo0f0npj.jpg",};
        list.add(new FriendCircleItem("橘右京", "起就是踏破黄泉为寻究极之花的修罗刀客", "https://ww1.sinaimg.cn/thumb150/e0ce6197gy1fin34deyhdj21hc0u0qcb.jpg", 4, multiplePhotoUrls, "八分钟前", "王者荣耀"));
        multiplePhotoUrls = new String[]{"https://ww3.sinaimg.cn/orj360/6de747a8ly1gcfskuokc3j20u016gb2l.jpg", "https://wx3.sinaimg.cn/thumb180/006cTsYMly1gdq27ugps6j30hu0huab2.jpg", "https://wx2.sinaimg.cn/thumb180/006o9tEvly1gdl43wirh0j31o00u0qv5.jpg", "https://wx1.sinaimg.cn/thumb180/0080SsU5gy1gdl0ffhomuj30jm0a3dgd.jpg", "https://wx2.sinaimg.cn/thumb180/005KQk7xgy1gddr1f54vdj30m80m8jtb.jpg", "https://wx4.sinaimg.cn/thumb180/baea142fgy1gdh4gw0bqbj208u0j0jsr.jpg", "https://wx2.sinaimg.cn/thumb180/0082yMRPly1gdncl8g2qyj30bo09q74k.jpg"};
        list.add(new FriendCircleItem("东方·耀", "我能把李白和韩信按在地上摩擦。", "https://ww2.sinaimg.cn/thumb150/8cc7f76fgy1g3t01ljj2nj207r0bjgmd.jpg", 4, multiplePhotoUrls, "九分钟前", "王者荣耀"));
        multiplePhotoUrls = new String[]{"https://ww2.sinaimg.cn/thumb150/0060jOSgly1gdvksyxxxmj30zk0gekcc.jpg", "https://ww1.sinaimg.cn/thumb150/0060jOSgly1gdvkt7fqqdj30zk0ge4qp.jpg", "https://ww3.sinaimg.cn/orj360/006ux2k3ly1gdvh2vjz05j31hc0omac5.jpg", "https://ww2.sinaimg.cn/orj360/006ux2k3ly1gdvh2xm3p4j31hc0omwi6.jpg"};
        list.add(new FriendCircleItem("扁鹊（最菜）", "刚刚拖住对面的最终偷塔成功哈哈哈", "https://ww2.sinaimg.cn/orj360/88c90051ly1flv431ge9aj20qo0qowjc.jpg", 3, multiplePhotoUrls, "五分钟前", "王者荣耀"));
        Music music = new Music("请笃信一个梦", "周深", "https://wx3.sinaimg.cn/thumbnail/ec8082cbly1gdir55bonnj20u00u04qp.jpg", "");
        FriendCircleItem musicItem = new FriendCircleItem("姜子牙", "今天钓到条大鱼，听首歌庆祝一下哈哈哈。", "https://ww1.sinaimg.cn/thumb150/006pdw5Hgy1flixfigmepj30u00u0dok.jpg", 5, music, "两小时前", "网易云音乐");
        musicItem.setMusic(music);
        list.add(musicItem);
        FriendCircleItem linkItem = new FriendCircleItem("吕布", "刚刚辣那波团我大中了三个人，快来围观吧！", "https://ww3.sinaimg.cn/orj360/c043d4e1ly1gd2u56bgg4j20wt0wttf7.jpg", 7, music, "十分钟前", "王者营地");
        LinkItem link = new LinkItem("https://ww2.sinaimg.cn/orj360/d6782af1ly1gdtosppn1jj21rc0u01kx.jpg", "https://s.weibo.com/weibo/%25E7%258E%258B%25E8%2580%2585%25E5%2590%2595%25E5%25B8%2583%25E5%25A4%25B4%25E5%2583%258F?topnav=1&wvr=6&b=1", "三星吕布直接打到对面投降");
        linkItem.setLinkItem(link);
        list.add(linkItem);
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
