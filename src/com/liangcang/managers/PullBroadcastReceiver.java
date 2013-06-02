package com.liangcang.managers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.Consts;

public class PullBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle bundle = intent.getExtras();
		Log.e("GexinSdkDemo", "onReceive() action=" + bundle.getInt("action"));
		switch (bundle.getInt(Consts.CMD_ACTION)) {
		case Consts.GET_MSG_DATA:
			// 获取透传（payload）数据
			//有延迟
			byte[] payload = bundle.getByteArray("payload");
			if (payload != null) {
				String data = new String(payload);
				Log.e("GexinSdkDemo", "Got Payload:" + data);
				// TODO:接收处理透传（payload）数据
			}
			break;
		case Consts.GET_CLIENTID:
			// 获取ClientID(CID)
			String cid = bundle.getString("clientid");
			Log.e("GexinSdkDemo", "Got ClientID:" + cid);//1fc3c5ed44c756e8fb357edbb71cb68d
			// TODO:
			/*
			 * 第三方应用需要将ClientID 上传到第三方服务器，并且将当前用户帐号和 ClientID
			 * 进行关联，以便以后通过用户帐号查找ClientID 进行消息推送 有些情况下ClientID
			 * 可能会发生变化，为保证获取最新的ClientID，请应用程序 在每次获取ClientID 广播后，都能进行一次关联绑定
			 */
			break;
		case Consts.BIND_CELL_STATUS:
			// 对于有号码绑定功能的SDK 版本，绑定成功后会发送该广播
			String cell = bundle.getString("cell");
			Log.e("GexinSdkDemo", "BIND_CELL_STATUS:" + cell);

			break;
		default:
			break;
		}

	}

}
