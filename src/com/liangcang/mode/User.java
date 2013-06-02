package com.liangcang.mode;

import java.io.Serializable;

public class User implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String user_id,user_name,user_image,user_desc,sig;
	private byte template_id;
	private Platform platform;
	private String email;
	
	
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
