package com.liangcang.mode;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangcang.util.MyLog;

public class CommentMode {

	private List<User> like;
	private List<Comment> commentList;
	private String comments;

	public String getComments() {
		return null;
	}

	public void setComments(String comments) {
		if(TextUtils.isEmpty(comments))
		{
			return;
		}
		List<Comment>cms=new ArrayList<Comment>();
		JSONObject arrays=JSON.parseObject(comments);
		for(String key :arrays.keySet())
		{
			MyLog.e("hh", "key="+key+"  value=" +arrays.getString(key));
	
			Comment com=JSON.parseObject(arrays.getString(key), Comment.class);
			com.isReply=false;
			cms.add(com);
			if(com.getReply()!=null)
			{
				
				for(Comment cm:com.getReply())
				{
					cm.isReply=true;
					cm.setComment_id(com.getComment_id());
//					cm.setParents_user_id(com.getUser_id());
					//cm.setReply(true);
					cms.add(cm);
				}
				com.getReply().clear();
			}
			
		}
		setCommentList(cms);
		
//		this.comments = comments;
	}

	public List<User> getLike() {
		return like;
	}

	public void setLike(List<User> like) {
		this.like = like;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

}
