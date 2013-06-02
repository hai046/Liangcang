package com.liangcang.mode;

import java.util.List;

public class GoodDetail extends Good {

	private String goods_url;
	private String goods_desc;
	private String owner_name;
	private String owner_desc;
	private String owner_image;
	private List<String>images_item;
	
	public List<String> getImages_item() {
		return images_item;
	}
	public void setImages_item(List<String> images_item) {
		this.images_item = images_item;
	}
	public String getGoods_url() {
		return goods_url;
	}
	public void setGoods_url(String goods_url) {
		this.goods_url = goods_url;
	}
	public String getGoods_desc() {
		return goods_desc;
	}
	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getOwner_desc() {
		return owner_desc;
	}
	public void setOwner_desc(String owner_desc) {
		this.owner_desc = owner_desc;
	}
	public String getOwner_image() {
		return owner_image;
	}
	public void setOwner_image(String owner_image) {
		this.owner_image = owner_image;
	}
	
}
