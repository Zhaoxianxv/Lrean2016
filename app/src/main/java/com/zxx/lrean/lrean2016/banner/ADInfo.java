package com.zxx.lrean.lrean2016.banner;

/**
 * 描述：广告信息</br>
 */
public class ADInfo {

    long id;
    long liveId;
    String imageUrl;
    String title;
    int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLiveId() {
        return liveId;
    }

    public void setLiveId(long liveId) {
        this.liveId = liveId;
    }





    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
