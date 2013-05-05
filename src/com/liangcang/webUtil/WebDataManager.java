package com.liangcang.webUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;

import com.liangcang.mode.User;
import com.liangcang.util.BitmapUtil;
import com.liangcang.util.MyLog;

public class WebDataManager {
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String METHOD_POST = "POST";
	private static final String METHOD_GET = "GET";
	private static final String USER_AGENT = "Android_brunjoy";
	String TAG = "WebUtils";
	private static WebDataManager mWebDataManager;
	public static final String HOST = "42.62.26.49"/*"api.iliangcang.com"*/;
	public static final String ROOTPATH = "http://" + HOST + "/";
	private int connectTimeout = 10000;// 10秒
	private int readTimeout = 10000;// 10秒

	public synchronized static WebDataManager getInsance() {
		if (mWebDataManager == null)
			mWebDataManager = new WebDataManager();
		return mWebDataManager;
	}

	private WebDataManager() {
	}

	/**
	 * 
	 * @return
	 */
	public String getIpAddress() {
		InetAddress mInetAddress;
		try {
			mInetAddress = InetAddress.getByName(WebDataManager.HOST);
			String host = mInetAddress.getHostAddress();
			return "http://" + host + "/";
		} catch (UnknownHostException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String getHostAddress() {

		return HOST;
	}

	// private final String SDK_CLIENT_SYSVERSION = "client-sysVersion";
	// private final String SDK_CLIENT_SYSNAME = "client-sysName";
	// private final String SDK_VERSION = "sdk-version";
	// private final String SYS_NAME = "Android";

	/**
	 * 得到传递的参数
	 * 
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> generateApiParams() {
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("v", "1.0");
		params.put("app_key", "Android");
		if (!TextUtils.isEmpty(sin))
			params.put("sig", sin);
		if (!TextUtils.isEmpty(user_id))
			params.put("user_id", user_id);
		return params;
	}

	private static String sin, user_id;

	public void initUser(User user) {
		sin = user.getSig();
		user_id = user.getUser_id();
	}

	// private Map<String, String> getProtocolParams() {
	// Map<String, String> params = new HashMap<String, String>( );
	// params.put( SDK_CLIENT_SYSNAME, SYS_NAME );
	// params.put( SDK_CLIENT_SYSVERSION, android.os.Build.VERSION.RELEASE );
	// params.put( SDK_VERSION, android.os.Build.VERSION.SDK );
	// return params;
	// }

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param context
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param headers
	 * 
	 * @param responseError
	 *            响应码非200时是否返回响应内容。false:会throw IOException
	 * @return 响应字符串
	 * @throws IOException
	 */
	public String doPost(String url, Map<String, String> params,
			Map<String, String> headers, int connectTimeout, int readTimeout,
			boolean responseError) throws Exception {
		return doPost(url, params, headers, DEFAULT_CHARSET, connectTimeout,
				readTimeout, responseError);
	}

	public String doPost(String url, Map<String, String> params)
			throws Exception {
		return doPost(url, params, null, DEFAULT_CHARSET, connectTimeout,
				readTimeout, true);
	}

	public String doPost(String url, Map<String, String> params, int timeOut)
			throws Exception {
		return doPost(url, params, null, DEFAULT_CHARSET, timeOut, timeOut,
				true);
	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param context
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param headers
	 * 
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @param responseError
	 * 
	 * @return 响应字符串
	 * @throws IOException
	 */
	public String doPost(String url, Map<String, String> params,
			Map<String, String> headers, String charset, int connectTimeout,
			int readTimeout, boolean responseError) throws Exception {
		// return doPostData(url, params, headers, charset, connectTimeout,
		// readTimeout);

		String ctype = "application/x-www-form-urlencoded;charset=" + charset;
		String query = buildQuery(params, charset);
		MyLog.d(TAG, "doPost  buildQuery= " + query);
		byte[] content = {};
		if (query != null) {
			content = query.getBytes(charset);
		}
		return doPost(url, ctype, content, headers, connectTimeout,
				readTimeout, responseError);
	}

	public String doPostData(String POST_URL, Map<String, String> params,
			Map<String, String> headers, String charset, int connectTimeout,
			int readTimeout) {
		String resultStr = "";
		HttpClient httpclient = new DefaultHttpClient();
		// 参数列表
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (params != null) {
			for (String key : params.keySet()) {
				nameValuePairs
						.add(new BasicNameValuePair(key, params.get(key)));
			}
		}

		HttpPost httppost = new HttpPost(ROOTPATH + POST_URL);
		MyLog.e(TAG, "" + httppost.getURI());
		if (headers != null) {
			for (String key : headers.keySet()) {
				httppost.addHeader(key, headers.get(key));
			}
		}

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response;
			response = httpclient.execute(httppost);
			resultStr = EntityUtils.toString(response.getEntity());
		} catch (UnsupportedEncodingException e) {
			MyLog.d(TAG, "UnsupportedEncodingException");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			MyLog.d(TAG, "ClientProtocolException");
			e.printStackTrace();
		} catch (IOException e) {
			MyLog.d(TAG, "IOException");
			e.printStackTrace();
		}
		return resultStr;

	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param context
	 * 
	 * @param url
	 *            请求地址
	 * @param ctype
	 *            请求类型
	 * @param content
	 *            请求字节数组
	 * @param headers
	 *            请求header
	 * @param responseError
	 * 
	 * @return 响应字符串
	 * @throws IOException
	 */
	public String doPost(String url, String ctype, byte[] content,
			Map<String, String> headers, int connectTimeout, int readTimeout,
			boolean responseError) throws Exception {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			try {
				conn = getConnection(
				/* context, */new URL(url.startsWith("http") ? url
						: (ROOTPATH + url)), METHOD_POST, ctype);
				if (headers != null) {
					Set<Entry<String, String>> set = headers.entrySet();
					// MyLog.d( TAG, "headers sets" );
					for (Entry<String, String> entry : set) {
						conn.addRequestProperty(entry.getKey(),
								entry.getValue());
						// MyLog.d( TAG, "key=" + entry.getKey( ) + ", value=" +
						// entry.getValue( ) );
					}
				}
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(readTimeout);
			} catch (IOException e) {

				throw new IOException(getResposeMsg(conn, e.getMessage()));
			}
			try {

				out = conn.getOutputStream();
				out.write(content);
				rsp = getResponseAsString(conn, responseError);
				MyLog.d(TAG, "rsp=" + rsp);
			} catch (Exception e) {

				throw e;
			}

		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		// MyLog.d( TAG, "date=" + rsp );
		return rsp;
	}

	/**
	 * 执行带文件上传的HTTP POST请求。
	 * 
	 * @param context
	 * 
	 * @param url
	 *            请求地址
	 * @param fileParams
	 *            文件请求参数
	 * @param textParams
	 *            文本请求参数
	 * 
	 * @return 响应字符串
	 * @throws IOException
	 */
	public String doPost(String url, Map<String, String> params,
			Map<String, String> headers, Map<String, FileItem> fileParams,
			int connectTimeout, int readTimeout) throws Exception {
		if (fileParams == null || fileParams.isEmpty()) {
			return doPost( /* context, */url, params, headers,
					DEFAULT_CHARSET, connectTimeout, readTimeout, false);
		} else {
			return doPost( /* context, */url, params, headers, fileParams,
					DEFAULT_CHARSET, connectTimeout, readTimeout);
		}
	}

	public String doPost(String url, Map<String, String> params,
			Map<String, FileItem> fileParams) throws Exception {
		if (fileParams == null || fileParams.isEmpty()) {
			return doPost( /* context, */url, params, null, DEFAULT_CHARSET,
					connectTimeout, readTimeout, false);
		} else {
			return doPost( /* context, */url, params, null, fileParams,
					DEFAULT_CHARSET, connectTimeout, readTimeout);
		}
	}

	/**
	 * 执行带文件上传的HTTP POST请求。
	 * 
	 * @param context
	 * 
	 * @param url
	 *            请求地址
	 * @param fileParams
	 *            文件请求参数
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @param textParams
	 *            文本请求参数
	 * 
	 * @return 响应字符串
	 * @throws IOException
	 */
	public String doPost(/* Context context, */String url,
			Map<String, String> params, Map<String, String> headers,
			Map<String, FileItem> fileParams, String charset,
			int connectTimeout, int readTimeout) throws IOException {
		String boundary = System.currentTimeMillis() + ""; // 随机分隔线
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			try {
				String ctype = "multipart/form-data;charset=" + charset
						+ ";boundary=" + boundary;
				conn = getConnection(
				/* context, */new URL(url.startsWith("http") ? url
						: (ROOTPATH + url)), METHOD_POST, ctype);
				if (headers != null) {
					Set<Entry<String, String>> set = headers.entrySet();
					for (Entry<String, String> entry : set) {
						conn.addRequestProperty(entry.getKey(),
								entry.getValue());
					}

				}
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(readTimeout);
			} catch (IOException e) {
				// Map<String, String> map = getParamsFromUrl(url);
				// TaobaoLogger.logCommError(e,
				// url,map.get("app_key"),map.get("method"), params);

				throw new IOException(getResposeMsg(conn, e.getMessage()));
			}

			try {
				out = conn.getOutputStream();

				byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n")
						.getBytes(charset);

				// 组装文本请求参数
				Set<Entry<String, String>> textEntrySet = params.entrySet();
				for (Entry<String, String> textEntry : textEntrySet) {
					byte[] textBytes = getTextEntry(textEntry.getKey(),
							textEntry.getValue(), charset);
					out.write(entryBoundaryBytes);
					out.write(textBytes);
				}

				// 组装文件请求参数
				Set<Entry<String, FileItem>> fileEntrySet = fileParams
						.entrySet();
				for (Entry<String, FileItem> fileEntry : fileEntrySet) {
					FileItem fileItem = fileEntry.getValue();
					byte[] fileBytes = getFileEntry(fileEntry.getKey(),
							fileItem.getFileName(), fileItem.getMimeType(),
							charset);
					out.write(entryBoundaryBytes);
					out.write(fileBytes);
					out.write(fileItem.getContent());
				}

				// 添加请求结束标志
				byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n")
						.getBytes(charset);
				out.write(endBoundaryBytes);
				rsp = getResponseAsString(conn, false);
			} catch (IOException e) {
				throw e;
			}

		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	private byte[] getTextEntry(String fieldName, String fieldValue,
			String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
		entry.append(fieldValue);
		return entry.toString().getBytes(charset);
	}

	private byte[] getFileEntry(String fieldName, String fileName,
			String mimeType, String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\";filename=\"");
		entry.append(fileName);
		entry.append("\"\r\nContent-Type:");
		entry.append(mimeType);
		entry.append("\r\n\r\n");
		return entry.toString().getBytes(charset);
	}

	/**
	 * 执行HTTP GET请求。
	 * 
	 * @param context
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * 
	 * @return 响应字符串
	 * @throws IOException
	 */
	public String doGet(String url, Map<String, String> params)
			throws IOException {
		return doGet(url, params, DEFAULT_CHARSET);
	}

	/**
	 * 执行HTTP GET请求。
	 * 
	 * @param context
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * 
	 * @return 响应字符串
	 * @throws IOException
	 */
	public String doGet(String url, Map<String, String> params, String charset)
			throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;
		long time = System.currentTimeMillis();
		try {
			String ctype = "application/x-www-form-urlencoded;charset="
					+ charset;
			// String ctype = "text/xml;charset=" + charset;
			String query = buildQuery(params, charset);
			try {
				conn = getConnection( /* context, */buildGetUrl(url, query),
						METHOD_GET, ctype);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (conn == null)
					return null;
				rsp = getResponseAsString(conn, false);
				
			} catch (IOException e) {
				rsp = getResposeMsg(conn, e.getMessage());
				e.printStackTrace();
				throw new IOException(getResposeMsg(conn, e.getMessage()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	
		if (MyLog.isDEBUG) {
			MyLog.i(TAG, "do get url=" + url + " 耗时:"
					+ (System.currentTimeMillis() - time) + " " + conn.getURL());
		}
		return rsp;
	}

	public String downFile(String url, Map<String, String> params)
			throws IOException {
		return downFile(System.currentTimeMillis() + "", url, params);
	}

	private String getResposeMsg(HttpURLConnection conn, String errMsg) {
		if (conn != null) {
			try {
				int responseCode = conn.getResponseCode();
				
				// MyLog.e( TAG, "==========================" );
				// MyLog.e( TAG, "responseCode=" + responseCode + "   " +
				// conn.getResponseMessage( ) );
				// MyLog.e( TAG, "==========================" );
				if (responseCode >= 500) {
					String msg = "服务器大姨妈，请稍后刷新再试";
					switch (responseCode) {
					// case 502:// Bad Gateway
					// msg="服务器正在维护，请稍后再试";
					// break;
					case 503:// Service Unavailable
					case 504:// Gateway Timeout
						msg = "连接服务器超时，服务器可能在维护";
					default:
						break;
					}
					return "{\"status\":"+responseCode+",\"msg\":\"" + msg + "\",\"op_status\":\"NG\"}";
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String downFile(String fileName, String url,
			Map<String, String> params) throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;
		// long time = System.currentTimeMillis( );
		try {
			String ctype = "application/x-www-form-urlencoded;charset="
					+ DEFAULT_CHARSET;
			String query = buildQuery(params, DEFAULT_CHARSET);
			try {
				conn = getConnection( /* context, */buildGetUrl(url, query),
						METHOD_GET, ctype);
			} catch (IOException e) {
				throw new IOException(getResposeMsg(conn, e.getMessage()));
			}

			try {
				rsp = getResponseAsFile(conn, fileName, false);
			} catch (IOException e) {

				throw e;
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		// MyLog.i( TAG, "do get file=" + url + " 耗时:" +
		// (System.currentTimeMillis( ) - time) );

		return rsp;
	}

	private HttpURLConnection getConnection(/* Context context, */URL url,
			String method, String ctype) throws IOException {

		HttpURLConnection conn = null;
		if ("https".equals(url.getProtocol())) {
			SSLContext ctx = null;
			try {
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0],
						new TrustManager[] { new DefaultTrustManager() },
						new SecureRandom());
			} catch (Exception e) {
				throw new IOException(e.getMessage());
			}
			HttpsURLConnection connHttps = null;
			// if (proxy == null) {
			connHttps = (HttpsURLConnection) url.openConnection();
			// } else {
			// connHttps = (HttpsURLConnection) url.openConnection( proxy );
			// }
			connHttps.setSSLSocketFactory(ctx.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;// 默认都认证通过
				}
			});
			conn = connHttps;
		} else {
			// if (proxy == null) {
			conn = (HttpURLConnection) url.openConnection();
			// } else {
			// conn = (HttpURLConnection) url.openConnection( proxy );
			// }
		}
//		if (MyLog.isDEBUG)
//			MyLog.e(TAG, "getConnection url=" + url.toString());
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(WebDataManager.METHOD_POST.equals(method));
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Content-Type", ctype);
		conn.setRequestProperty("Accept-Encoding", "gzip");// support gzip

		return conn;
	}

	public URL buildGetUrl(String url, Map<String, String> params,
			String charset) throws IOException {
		String queryString = buildQuery(params, charset);
		return buildGetUrl(url, queryString);
	}

	private URL buildGetUrl(String strUrl, String query) throws IOException {
		URL url = new URL(strUrl.startsWith("http") ? strUrl
				: (ROOTPATH + strUrl));
		MyLog.e(TAG, "strUrl"+url);
		if (TextUtils.isEmpty(query)) {
			return url;
		}

		if (TextUtils.isEmpty(url.getQuery())) {
			if (strUrl.endsWith("?")) {
				strUrl = strUrl + query;
			} else {
				strUrl = strUrl + "?" + query;
			}
		} else {
			if (strUrl.endsWith("&")) {
				strUrl = strUrl + query;
			} else {
				strUrl = strUrl + "&" + query;
			}
		}

		return new URL(strUrl.startsWith("http") ? strUrl : (ROOTPATH + strUrl));
	}

	public String buildQuery(Map<String, String> params, String charset)
			throws UnsupportedEncodingException {
		if (params == null || params.isEmpty()) {
			return null;
		}
		if (TextUtils.isEmpty(charset)) {
			charset = DEFAULT_CHARSET;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			MyLog.e(TAG, "name="+name+" value="+value);
			// 忽略参数名或参数值为空的参数
			if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(value)) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}

				query.append(name).append("=")
						.append(URLEncoder.encode(value, charset));
			}
		}

		return query.toString();
	}

	/**
	 * ==========================================<BR>
	 * 功能： <BR>
	 * 时间：2013-1-28 下午4:39:18 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param conn
	 * @param responseError
	 * @return
	 * @throws IOException
	 */
	protected String getResponseAsString(HttpURLConnection conn,
			boolean responseError) throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		String header = conn.getHeaderField("Content-Encoding");
		boolean isGzip = false;
		if (header != null
				&& header.toLowerCase(Locale.getDefault()).contains("gzip")) {
			isGzip = true;
		}
		InputStream es = conn.getErrorStream();
		if (es == null) {
			InputStream input = conn.getInputStream();
			if (isGzip) {
				input = new GZIPInputStream(input);
			}
			return getStreamAsString(input, charset);
		} else {
			if (isGzip) {
				es = new GZIPInputStream(es);
			}
			String msg = getStreamAsString(es, charset);
			if (TextUtils.isEmpty(msg)) {
				throw new IOException("ResponseCode=" + conn.getResponseCode()
						+ ",ResponseMessage=" + conn.getResponseMessage());
			} else if (responseError) {
				return msg;
			} else {
				throw new IOException(msg);
			}
		}
	}

	private String getStreamAsString(InputStream stream, String charset)
			throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, charset));
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}

			return writer.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	private String getResponseCharset(String ctype) {
		String charset = DEFAULT_CHARSET;

		if (!TextUtils.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if (pair.length == 2) {
						if (!TextUtils.isEmpty(pair[1])) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}

		return charset;
	}

	protected String getResponseAsFile(HttpURLConnection conn, String fileName,
			boolean responseError) throws IOException {

		String header = conn.getHeaderField("Content-Encoding");
		boolean isGzip = false;
		if (header != null
				&& header.toLowerCase(Locale.getDefault()).contains("gzip")) {
			isGzip = true;
		}
		InputStream es = conn.getErrorStream();
		if (es == null) {
			InputStream input = conn.getInputStream();
			if (isGzip) {
				input = new GZIPInputStream(input);
			}
			return getStreamAsFileName(input, fileName);
		} else {
			return null;
		}
	}

	// private String getStreamAsFileName(InputStream stream) throws IOException
	// {
	// return getStreamAsFileName( stream, System.currentTimeMillis( ) + "" );
	// }

	private String getStreamAsFileName(InputStream stream, String fileName)
			throws IOException {
		try {

			BufferedInputStream bis = new BufferedInputStream(stream);

			// StringWriter writer = new StringWriter( );
			File file = new File(BitmapUtil.rootCacheAppSavaPath + "/"
					+ fileName);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte buffer[] = new byte[8 * 1024];
			int lg = -1;
			while ((lg = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, lg);
				bos.flush();
			}
			bos.close();
			fos.close();
			bis.close();

			return file.exists() ? file.getPath() : null;
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	//
	// private String getResponseCharset(String ctype) {
	// String charset = DEFAULT_CHARSET;
	//
	// if (!TextUtils.isEmpty( ctype )) {
	// String[] params = ctype.split( ";" );
	// for (String param : params) {
	// param = param.trim( );
	// if (param.startsWith( "charset" )) {
	// String[] pair = param.split( "=", 2 );
	// if (pair.length == 2) {
	// if (!TextUtils.isEmpty( pair[1] )) {
	// charset = pair[1].trim( );
	// }
	// }
	// break;
	// }
	// }
	// }
	//
	// return charset;
	// }
	/**
	 * 使用默认的UTF-8字符集反编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @return 反编码后的参数值
	 */
	public String decode(String value) {
		return decode(value, DEFAULT_CHARSET);
	}

	/**
	 * 使用默认的UTF-8字符集编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @return 编码后的参数值
	 */
	public String encode(String value) {
		return encode(value, DEFAULT_CHARSET);
	}

	/**
	 * 使用指定的字符集反编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @param charset
	 *            字符集
	 * @return 反编码后的参数值
	 */
	public String decode(String value, String charset) {
		String result = null;
		if (!TextUtils.isEmpty(value)) {
			try {
				result = URLDecoder.decode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 使用指定的字符集编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @param charset
	 *            字符集
	 * @return 编码后的参数值
	 */
	public String encode(String value, String charset) {
		String result = null;
		if (!TextUtils.isEmpty(value)) {
			try {
				result = URLEncoder.encode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	// private Map<String, String> getParamsFromUrl(String url) {
	// Map<String, String> map = null;
	// if (url != null && url.indexOf('?') != -1) {
	// map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
	// }
	// if (map == null) {
	// map = new HashMap<String, String>();
	// }
	// return map;
	// }

	/**
	 * 从URL中提取所有的参数。
	 * 
	 * @param query
	 *            URL地址
	 * @return 参数映射
	 */
	public Map<String, String> splitUrlQuery(String query) {
		Map<String, String> result = new HashMap<String, String>();

		String[] pairs = query.split("&");
		if (pairs != null && pairs.length > 0) {
			for (String pair : pairs) {
				String[] param = pair.split("=", 2);
				if (param != null && param.length == 2) {
					result.put(param[0], param[1]);
				}
			}
		}

		return result;
	}

	private class DefaultTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
	}

}
