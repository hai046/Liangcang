package com.liangcang.mode;

public class TopicHolder {

    private String content,name,pic;
    private int id;
    private int type;
    private String numiid;
    private String url;
    
    public String getNumiid() {
        return numiid;
    }
    public void setNumiid(String numiid) {
        this.numiid = numiid;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "TopicHolder [content=" + content + ", name=" + name + ", pic=" + pic + ", id=" + id + "]";
    }
    
    
}
