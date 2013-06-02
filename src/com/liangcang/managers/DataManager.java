package com.liangcang.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangcang.mode.Good;
import com.liangcang.mode.GoodDetail;
import com.liangcang.mode.User;
import com.liangcang.util.MyLog;
import com.liangcang.util.Util;
import com.liangcang.webUtil.FileItem;
import com.liangcang.webUtil.WebDataManager;

public class DataManager {

	String TAG = "TaobaokeDataManager";
	private static DataManager mTaobaokeDataManager = null;
	private Context mContext;

	public static synchronized DataManager getInstance(Context mContext) {
		if (mTaobaokeDataManager == null) {
			mTaobaokeDataManager = new DataManager(mContext);
		}
		return mTaobaokeDataManager;
	}

	private DataManager(Context mContext) {
		this.mContext = mContext.getApplicationContext();
	}

	public void getGoodComments(final String goods_id, final int page,
			final int count, final DataCallBack<String> mDataCallBack) {

		GetDataTask task = new GetDataTask("goods/comments") {
			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure("没有新的内容");
					return;
				}

				mDataCallBack.success(mDataStatus.getValue());

			}

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				try {
					Map<String, String> params = WebDataManager.getInsance()
							.generateApiParams();
					params.put("goods_id", goods_id);
					params.put("page", page + "");
					params.put("count", count + "");
					resultMSG = WebDataManager.getInsance().doGet(url, params);
				} catch (IOException e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public void getNotificationcount(final String user_id,
			final DataCallBack<String> mDataCallBack) {

		GetDataTask task = new GetDataTask("notificationcount") {
			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure("没有新的内容");
					return;
				}

				mDataCallBack.success(mDataStatus.getValue());

			}

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				try {
					Map<String, String> params = WebDataManager.getInsance()
							.generateApiParams();
					params.put("user_id", user_id);

					resultMSG = WebDataManager.getInsance().doGet(url, params);
				} catch (IOException e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public void doPost(String path, final Map<String, String> params,
			final DataCallBack<String> mDataCallBack) {

		GetDataTask task = new GetDataTask(path) {

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				Map<String, String> fileParams = WebDataManager.getInsance()
						.generateApiParams();
				if (params != null) {
					fileParams.putAll(params);
				}
				try {
					
					resultMSG = WebDataManager.getInsance().doPost(url,
							fileParams);
				} catch (Exception e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure(mDataStatus.getValue());
					return;
				}
				mDataCallBack.success(mDataStatus.getValue());

			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public void doPostFile(String path, final Map<String, FileItem> fileParams,
			final DataCallBack<String> mDataCallBack) {

		GetDataTask task = new GetDataTask(path) {

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				Map<String, String> params = WebDataManager.getInsance()
						.generateApiParams();

				try {
					resultMSG = WebDataManager.getInsance().doPost(url, params,
							fileParams);
				} catch (Exception e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure(mDataStatus.getValue());
					return;
				}
				mDataCallBack.success(mDataStatus.getValue());

			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public void doGet(String path, final Map<String, String> params,
			final DataCallBack<String> mDataCallBack) {

		GetDataTask task = new GetDataTask(path) {

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				Map<String, String> fileParams = WebDataManager.getInsance()
						.generateApiParams();
				if (params != null) {
					fileParams.putAll(params);
				}
				try {
					// TODO 暂时用get 后面将要改成get
					resultMSG = WebDataManager.getInsance().doGet(url,
							fileParams);
				} catch (Exception e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure(mDataStatus.getValue());
					return;
				}
				mDataCallBack.success(mDataStatus.getValue());

			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public void doRegister(final String email, final String password,
			final String user_name, final String user_image,
			final DataCallBack<User> mDataCallBack) {

		GetDataTask task = new GetDataTask("reg") {

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				try {
					Map<String, String> params = WebDataManager.getInsance()
							.generateApiParams();
					params.put("email", email);
					params.put("password", password);
					params.put("user_name", user_name);
					// params.put("email", email);
					if (TextUtils.isEmpty(user_image)) {
						resultMSG = WebDataManager.getInsance().doPost(url,
								params);
					} else {
						Map<String, FileItem> fileParams = new HashMap<String, FileItem>();
						FileItem item = new FileItem(user_image);
						fileParams.put("email", item);
						resultMSG = WebDataManager.getInsance().doPost(url,
								params, fileParams);
					}

				} catch (Exception e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure(mDataStatus.getValue());
					return;
				}
				mDataCallBack.success(JSON.parseObject(mDataStatus.getValue(),
						User.class));

			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public void Login(final String user_name, final String password,
			final DataCallBack<User> mDataCallBack) {

		GetDataTask task = new GetDataTask("login") {
			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure(mDataStatus.getValue());
					return;
				}
				User date = com.alibaba.fastjson.JSONArray.parseObject(
						mDataStatus.getValue(), User.class);
				mDataCallBack.success(date);

			}

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				try {
					Map<String, String> params = WebDataManager.getInsance()
							.generateApiParams();
					params.put("user_name", user_name);
					params.put("password", Util.getMD5Str(password).trim());
					// 10000, true );
					resultMSG = WebDataManager.getInsance().doPost(url, params);
				} catch (Exception e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public <T> void getListData(String path, final Map<String, String> params,
			final boolean isDoGet, final Class<T> clazz,
			final DataCallBack<T> mDataCallBack) {

		GetDataTask task = new GetDataTask(path) {
			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure("没有新的内容");
					return;
				}

				List<T> list = com.alibaba.fastjson.JSON.parseArray(
						mDataStatus.getValue(), clazz);
				mDataCallBack.success(list);

			}

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				try {
					Map<String, String> myParams = WebDataManager.getInsance()
							.generateApiParams();
					if (params != null)
						myParams.putAll(params);
					resultMSG = isDoGet ? (WebDataManager.getInsance().doGet(
							url, myParams)) : (WebDataManager.getInsance()
							.doPost(url, myParams));
				} catch (Exception e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public void getGoodsDetail(final String goods_id,
			final DataCallBack<GoodDetail> mDataCallBack) {
		GetDataTask task = new GetDataTask("goods") {
			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure("没有新的内容");
					return;
				}
				GoodDetail mGoodDetail = JSON.parseObject(
						mDataStatus.getValue(), GoodDetail.class);
				mDataCallBack.success(mGoodDetail);
			}

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				try {
					Map<String, String> myParams = WebDataManager.getInsance()
							.generateApiParams();

					myParams.put("goods_id", goods_id);
					resultMSG = WebDataManager.getInsance()
							.doGet(url, myParams);
				} catch (Exception e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);

	}

	public void getListData(String path, final Map<String, String> params,
			final boolean isDoGet, final DataCallBack<String> mDataCallBack) {

		GetDataTask task = new GetDataTask(path) {
			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure("没有新的内容");
					return;
				}
				mDataCallBack.success(mDataStatus.getValue());
			}

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				try {
					Map<String, String> myParams = WebDataManager.getInsance()
							.generateApiParams();
					if (params != null)
						myParams.putAll(params);
					resultMSG = isDoGet ? (WebDataManager.getInsance().doGet(
							url, myParams)) : (WebDataManager.getInsance()
							.doPost(url, myParams));
				} catch (Exception e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public void getData(String path, final Map<String, String> params,
			final boolean isDoGet, final DataCallBack<String> mDataCallBack) {

		GetDataTask task = new GetDataTask(path) {
			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure("没有新的内容");
					return;
				}
				mDataCallBack.success(mDataStatus.getValue());
			}

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				try {
					Map<String, String> myParams = WebDataManager.getInsance()
							.generateApiParams();
					if (params != null)
						myParams.putAll(params);
					resultMSG = isDoGet ? (WebDataManager.getInsance().doGet(
							url, myParams)) : (WebDataManager.getInsance()
							.doPost(url, myParams));
				} catch (Exception e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public void getRecomends(final int page, final int count,
			final DataCallBack<Good> mDataCallBack) {

		GetDataTask task = new GetDataTask("login") {
			public void onPost(String result) {
				DataStatus mDataStatus = getDataType(result);

				if (DataStatus.OK != mDataStatus) {
					mDataCallBack.failure("没有新的内容");
					return;
				}

				List<Good> listData = JSON.parseArray(mDataStatus.getValue(),
						Good.class);
				mDataCallBack.success(listData);

			}

			@Override
			public String doInBground(String url) {
				String resultMSG = null;
				try {
					Map<String, String> params = WebDataManager.getInsance()
							.generateApiParams();
					params.put("page", page + "");
					params.put("password", count + "");
					resultMSG = WebDataManager.getInsance().doGet(url, params);
				} catch (IOException e) {
					resultMSG = e.getMessage();
				}
				return resultMSG;
			}

			@Override
			public void cancel(String msg) {
				mDataCallBack.failure(msg);

			}
		};
		addNewTask(task);
	}

	public String getData(String text) {
		do {
			if (text == null)
				break;
			try {
				JSONObject obj = JSONObject.parseObject(text);
				return obj.getString("data");
			} catch (Exception e) {
			}
		} while (false);
		return null;
	}

	public String getStrDataByFiled(String text, String filed) {
		do {
			if (text == null)
				break;
			JSONObject obj = JSONObject.parseObject(text);
			return obj.getString(filed);
		} while (false);
		return null;
	}

	public int getIntDataByFiled(String text, String filed) {
		do {
			if (text == null)
				break;
			JSONObject obj = JSONObject.parseObject(text);
			return obj.getInteger(filed);
		} while (false);
		return 0;
	}

	public boolean getStatuse(String text) {
		try {
			do {
				if (text == null)
					break;

				JSONObject obj = JSONObject.parseObject(text);
				String status = obj.getString("op_status");

				return (status != null && status.equalsIgnoreCase("ok"));
			} while (false);
		} catch (Exception e) {

		}
		return false;
	}

	private static ArrayList<Task> taskUrl = new ArrayList<Task>();

	private class Task {
		public String url;
		public GetDataTask mGetDataTask;

		public Task(String url, GetDataTask mGetDataTask) {
			this.url = url;
			this.mGetDataTask = mGetDataTask;
		}

	}

	private synchronized void addNewTask(GetDataTask task) {

		if (taskUrl == null)
			return;

		if (currentRunningTask != null) {
			synchronized (currentRunningTask) {
				if (System.currentTimeMillis() - startTime > 3 * 1000) {
					currentRunningTask.cancel(true);
					// MyLog.e( TAG, "------cancel---------siz=" +
					// currentRunningTask.getUrl( ) );
					currentRunningTask = null;
				}
			}

		}
		MyLog.e(TAG, "------addNewTask---------siz=" + taskUrl.size());
		for (int i = taskUrl.size() - 1; i >= 0; i--) {
			synchronized (taskUrl) {
				if (task.getUrl().equals(taskUrl.get(i).url)) {
					taskUrl.remove(i);
					taskUrl.add(i, new Task(task.getUrl(), task));
					MyLog.e(TAG, "-----------------");
					MyLog.e(TAG, "---------已经包含了替换以前的任务任务，不能重复添加-----");
					MyLog.e(TAG, "-----------------");
					return;
				}
			}

		}
		if (currentRunningTask != null/* currentRuningTaskNum >= MAXTASKNUM */) {
			taskUrl.add(0, new Task(task.getUrl(), task));
			MyLog.e(TAG,
					"-asfdas---有正在执行的任务  故先添加到执行队列中--addNewTask---------siz="
							+ taskUrl.size());
		} else {
			MyLog.e(TAG,
					"-asfdas-----开始执行execute---------siz=" + taskUrl.size());
			task.execute();
		}

	}

	private static long startTime = 0;
	private GetDataTask currentRunningTask = null;

	private synchronized void checkTask() {
		if (currentRunningTask != null/* currentRuningTaskNum >= MAXTASKNUM */) {
			// MyLog.e( TAG, "---------正在执行的zhong--------currentRunningTask" +
			// currentRunningTask.getUrl( ) );
		} else {
			if (taskUrl.size() > 0) {
				Task task = taskUrl.remove(taskUrl.size() - 1);
				task.mGetDataTask.execute();
				// MyLog.e( TAG, "---------缓存有需要执行的任务，现在去执行--------url=" +
				// task.url );
			} else {
				// MyLog.e( TAG, "---------任务队列已经没有任务需要执行了--------" );
			}
		}

	}

	// private byte currentRuningTaskNum = 0;

	public abstract class GetDataTask extends AsyncTask<Void, Void, String> {
		private String url;

		public String getUrl() {
			return url;
		}

		public abstract String doInBground(String url);

		public abstract void onPost(String result);

		public abstract void cancel(String msg);

		public GetDataTask(String url) {
			this.url = url;

		}

		@Override
		protected synchronized void onCancelled() {
			// currentRuningTaskNum--;
			currentRunningTask = null;
			checkTask();
			// MyLog.e( TAG,
			// "---------因为有新的任务，并且此任务耗时超过最大UI时间，故onCancelled-------url=" + url
			// );
		}

		@Override
		protected void onPreExecute() {
			// currentRuningTaskNum++;
			startTime = System.currentTimeMillis();
			currentRunningTask = this;
			// MyLog.e( TAG, "---------准备执行onPreExecute-------url=" + url );
		}

		@Override
		protected String doInBackground(Void... arg0) {
			// MyLog.e( TAG, "---------后台执行doInBackground-------url=" + url );
			return doInBground(url);
		}

		@Override
		protected synchronized void onPostExecute(String result) {
			onPost(result);
			currentRunningTask = null;
			// MyLog.e( TAG, "---------执行完成onPostExecute-------url=" + url );
			// currentRuningTaskNum--;
			checkTask();
		}

	}

	public synchronized void cancelAllTask() {
		// MyLog.e( TAG, "----取消正在执行的任务-----cancelAllTask-------" );
		taskUrl.clear();
		if (currentRunningTask != null) {
			currentRunningTask.cancel(false);
		}
		currentRunningTask = null;
	}

	public void clear() {
		taskUrl.clear();
		currentRunningTask = null;
		mTaobaokeDataManager = null;
	}

	public enum DataStatus {
		OK, NG;
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	public DataStatus getDataType(String result) {
		DataStatus mDataStatus = DataStatus.NG;
		if (TextUtils.isEmpty(result)) {
			if (!Util.isConnectedNetWork(mContext)) {
				mDataStatus.setValue("亲，您的网络好像有问题，请先连接到互联网");
			} else {
				mDataStatus.setValue("获取数据失败");
			}
		}
		try {
			JSONObject obj = JSONObject.parseObject(result);
			int status = obj.getIntValue("status");
			if (status == 200) {
				mDataStatus = DataStatus.OK;
				mDataStatus.setValue(obj.getString("data"));

			} else {
				mDataStatus = DataStatus.NG;
				mDataStatus.setValue(obj.getString("msg"));
			}
		} catch (Exception e) {
			if (!Util.isConnectedNetWork(mContext)) {
				mDataStatus.setValue("您的网络没连接，请先连接到互联网  然后刷新试试");
			} else {
				mDataStatus.setValue("数据加载失败,请稍后重新刷新试一试");
			}

		}

		return mDataStatus;
	}

}
