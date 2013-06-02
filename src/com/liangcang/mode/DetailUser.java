package com.liangcang.mode;

import java.util.List;

public class DetailUser {

	private String user_id;
	private String user_name,user_image,user_desc;
	private int friend,template_id;
	private String like_count,recommendation_count,following_count,followed_count;
	private List<Good>goods;
	private List<User>users;
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
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
	public int getFriend() {
		return friend;
	}
	public void setFriend(int friend) {
		this.friend = friend;
	}
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}
	public String getLike_count() {
		return like_count;
	}
	public void setLike_count(String like_count) {
		this.like_count = like_count;
	}
	public String getRecommendation_count() {
		return recommendation_count;
	}
	public void setRecommendation_count(String recommendation_count) {
		this.recommendation_count = recommendation_count;
	}
	public String getFollowing_count() {
		return following_count;
	}
	public void setFollowing_count(String following_count) {
		this.following_count = following_count;
	}
	public String getFollowed_count() {
		return followed_count;
	}
	public void setFollowed_count(String followed_count) {
		this.followed_count = followed_count;
	}
	public List<Good> getGoods() {
		return goods;
	}
	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}

	
	
}
