package com.liangcang.mode;

import java.util.List;

import com.liangcang.util.MyLog;

public class Comment {

	private String comment_id;
	private String reply_id;
	private String user_id;
	private String user_name;
	private String user_image;
	private String msg;
	private String create_time;
	
//	private String parents_id;
	private String parent_uid;
			
	
	public boolean isReply = false;

	public String getParent_uid() {
		return parent_uid;
	}

	public void setParent_uid(String parent_uid) {
		this.parent_uid = parent_uid;
	}

	private List<Comment> reply;

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	public String getReply_id() {
		return reply_id;
	}

	public void setReply_id(String reply_id) {
		this.reply_id = reply_id;
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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public List<Comment> getReply() {
		return reply;
	}

	public void setReply(List<Comment> reply) {

//		isReply = (reply != null && reply.size()>0);
//		MyLog.e("setReply", "reply="+reply);
		this.reply = reply;
	}

}
