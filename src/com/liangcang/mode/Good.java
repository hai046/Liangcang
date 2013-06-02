package com.liangcang.mode;

import java.util.Date;

public class Good {

	@Override
	public String toString() {
		return "Good [goods_id=" + goods_id + ", goods_image=" + goods_image
				+ ", goods_name=" + goods_name + ", price=" + price
				+ ", owner_id=" + owner_id + ", comment_count=" + comment_count
				+ ", like_count=" + like_count + ", liked=" + liked + "]";
	}

	private String notification_id;
	private String goods_id;
	private String goods_image;
	private String goods_name;
	private String price;
	private String owner_id;
	private String owner_name;
	private String owner_image;
	private String owner_desc;
	private String user_image;
	private String user_desc;
	private String user_id, user_name;
	private String comment_count;
	private String like_count;
	private String liked;
	private String activity_id;
	private Date create_time;
	private int type;
	private String msg;
	private String message_id;
	private String msg_count;

	public String getUser_desc() {
		return user_desc;
	}

	public void setUser_desc(String user_desc) {
		this.user_desc = user_desc;
	}

	public String getMsg_count() {
		return msg_count;
	}

	public void setMsg_count(String msg_count) {
		this.msg_count = msg_count;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(String notification_id) {
		this.notification_id = notification_id;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getOwner_image() {
		return owner_image;
	}

	public void setOwner_image(String owner_image) {
		this.owner_image = owner_image;
	}

	public String getOwner_desc() {
		return owner_desc;
	}

	public void setOwner_desc(String owner_desc) {
		this.owner_desc = owner_desc;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_image() {
		return goods_image;
	}

	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getComment_count() {
		return comment_count;
	}

	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}

	public String getLike_count() {
		return like_count;
	}

	public void setLike_count(String like_count) {
		this.like_count = like_count;
	}

	public String getLiked() {
		return liked;
	}

	public void setLiked(String liked) {
		this.liked = liked;
	}

}
