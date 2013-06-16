package com.liangcang.util;

import android.content.Context;
import android.content.Intent;
import cn.sharesdk.onekeyshare.ShareAllGird;
import cn.sharesdk.onekeyshare.SharePage;

import com.liangcang.R;
import com.liangcang.mode.Good;

public class ShareUtil {
	// 使用快捷分享完成图文分享
	public static void showGrid(Context mContext,  Good mGood) {
		Intent i = new Intent(mContext, ShareAllGird.class);
		// 分享时Notification的图标
		i.putExtra("notif_icon", com.liangcang.R.drawable.ic_launcher);
		// 分享时Notification的标题
		i.putExtra("notif_title", mContext.getString(R.string.app_name));

		// // address是接收人地址，仅在信息和邮件使用，否则可以不提供
		// i.putExtra("address", "12345678901");
		// title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供
		i.putExtra("title", "分享，正式发布没有该页面，直接发送");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用，否则可以不提供
		i.putExtra("titleUrl", "http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		i.putExtra("text", "良仓商品 "+mGood.getGoods_name()+" 不错哦  ，刚看来看看吧");
		// imagePath是本地的图片路径，所有平台都支持这个字段，不提供，则表示不分享图片

		i.putExtra("imagePath", BitmapUtil.getFileName(mGood.getGoods_image()));
		// url仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("url", "http://www.iliangcang.com");
		// thumbPath是缩略图的本地路径，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		// i.putExtra("thumbPath", MainActivity.TEST_IMAGE);
		// appPath是待分享应用程序的本地路劲，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		// i.putExtra("appPath", MainActivity.TEST_IMAGE);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
		// i.putExtra("comment", menu.getContext().getString(R.string.share));
		// site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供
		// i.putExtra("site", menu.getContext().getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供
		// i.putExtra("siteUrl", "http://sharesdk.cn");

		// 是否直接分享
		i.putExtra("silent", false);
		mContext.startActivity(i);
	}

	// 使用快捷分享完成直接分享
	public static void showShare(Context mContext, final String platform) {
		Intent i = new Intent(mContext, SharePage.class);
		// 分享时Notification的图标
		i.putExtra("notif_icon", R.drawable.ic_launcher);
		// 分享时Notification的标题
		i.putExtra("notif_title", mContext.getString(R.string.app_name));

		// address是接收人地址，仅在信息和邮件使用，否则可以不提供
		i.putExtra("address", "12345678901");
		// title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供
		i.putExtra("title", "分享title");
		// titleUrl是标题的网络链接，仅在QQ空间使用，否则可以不提供
		i.putExtra("titleUrl", "http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		i.putExtra("text", "分享内容");
		// imagePath是本地的图片路径，在豆瓣、Facebook、网易微博、新浪微博、腾讯微博、Twitter、邮箱、
		// 信息和微信（包括好友和朋友圈）图片分享中使用，否则可以不提供
		// i.putExtra("imagePath", MainActivity.TEST_IMAGE);
		// imageUrl是网络的图片路径，仅在人人网和QQ空间使用，否则可以不提供
		i.putExtra("imageUrl",
				"http://sharesdk.cn/Public/Frontend/images/logo.png");
		// url仅在人人网和微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("url", "http://sharesdk.cn");
		// thumbPath是缩略图的本地路径，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		// i.putExtra("thumbPath", MainActivity.TEST_IMAGE);
		// appPath是待分享应用程序的本地路劲，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		// i.putExtra("appPath", MainActivity.TEST_IMAGE);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
		// i.putExtra("comment", menu.getContext().getString(R.string.share));
		// site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供
		// i.putExtra("site", menu.getContext().getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供
		i.putExtra("siteUrl", "http://sharesdk.cn");

		// 是平台名称
		i.putExtra("platform", platform);
		mContext.startActivity(i);
	}
}
