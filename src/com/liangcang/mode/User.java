package com.liangcang.mode;

import java.io.Serializable;

public class User implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String user_id,user_name,user_image,user_desc,sig;
	private String followed_count;
	private String following_count;
	private String friend;
	private String is_success;
	private String like_count;
	private String recommendation;

	private byte template_id;
	private Platform platform;
	private String email;
	
	
	public String getFollowed_count() {
		return followed_count;
	}
	public void setFollowed_count(String followed_count) {
		this.followed_count = followed_count;
	}
	public String getFollowing_count() {
		return following_count;
	}
	public void setFollowing_count(String following_count) {
		this.following_count = following_count;
	}
	public String getFriend() {
		return friend;
	}
	public void setFriend(String friend) {
		this.friend = friend;
	}
	public String getIs_success() {
		return is_success;
	}
	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}
	public String getLike_count() {
		return like_count;
	}
	public void setLike_count(String like_count) {
		this.like_count = like_count;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public String getUser_desc() {
		return user_desc;
	}
	public void setUser_desc(String user_desc) {
		this.user_desc = user_desc;
	}
	public String getSig() {
		return sig;
	}
	public void setSig(String sig) {
		this.sig = sig;
	}
	public byte getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(byte template_id) {
		this.template_id = template_id;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name
				+ ", user_image=" + user_image + ", user_desc=" + user_desc
				+ ", sig=" + sig + ", template_id=" + template_id
				+ ", platform=" + platform + "]";
	}
	
	
	
}
