package com.dap.cloud.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestUtil {

	private static String ENCODING = "UTF-8";

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			/*for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}*/
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		String[] a = param.split("&");
		System.out.println(param);
		Map<String, String> map = new HashMap<>();
		for(int i=0;i<a.length;i++){
			String[] a_ = a[i].split("=");
			map.put(a_[0],a_[1]);
		}
		String result = POST(url,map);
		return result;
	}

	public static String doPost(String url,String data) {
		HttpClient client = HttpClients.createDefault();
		// 要调用的接口方法
		HttpPost post = new HttpPost(url);
		String result = "";
		try {
			StringEntity s = new StringEntity(data);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			post.addHeader("content-type", "text/xml");
			HttpResponse res = client.execute(post);
			String response1 = EntityUtils.toString(res.getEntity());
			System.out.println(response1);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(res.getEntity());// 返回json格式：
			}else{
				result = "{\"success\":false,\"result\":\"发送请求失败\"}";
			}
			System.out.println(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static String POST(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
    /**
     * 向指定 URL 发送PUT方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
	public static String sendPut(String url, String param) {
		String[] a = param.split("&");
		System.out.println(param);
		Map<String, String> map = new HashMap<>();
		for(int i=0;i<a.length;i++){
			String[] a_ = a[i].split("=");
			map.put(a_[0],a_[1]);
		}
		String result = PUT(url,map);
		return result;
	}
	public static String PUT(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPut method = new HttpPut(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}

	public static String doPostGetJson(String url,String data) throws ParseException {
		String sr = doPost(url,data);
		System.out.println("返回参数：" + sr);
		return sr;
	}

	//appKey
	public static final String APP_KEY = "1000047";
	//秘钥
	public static final String SECRET = "65beb2eba35f12f2dcdc2dc5b2ea9f1b";
	//注册接口
	public static final String REGISTER_URL = "http://api.cbasetest.cosmoplat.com/api/out/user/register";
	//登陆接口
	public static final String LOGIN_URL = "http://cg.haier.net/cas/login";
	//订单列表接口
	public static final String ORDER_LIST_URL = "http://cg.haier.net/cosmocg/purchase/api/purchaseOrderMainService/findPurchaseOrderListMain";
	//订单详情接口
	public static final String ORDER_URL = "http://cg.haier.net/cosmocg/purchaseOrder/orderSeniorDetails";
	//cosmoplat下单
	public static final String COS_ORDER_URL = "http://api.cbasetest.cosmoplat.com/api/out/modular/order";
	//cosmoplat下单
	public static final String COS_ORDER2_URL = "http://api.cbasetest.cosmoplat.com/api/out/modular/order/multi";
	//cosmoplat支付
	public static final String PAY_URL = "http://zf.cbasetest.cosmoplat.com/buyer/pre-pay";
	//创建商品
	public static final String NEW_ITEMS = "http://api.cbasetest.cosmoplat.com/api/out/seller/createItemsNew";
	//创建店铺
	public static final String NEW_SHOP = "http://api.cbasetest.cosmoplat.com/api/out/shopsNew";
	public static final String FIND_USER = "http://api.cbasetest.cosmoplat.com/api/out/user/find";
    //cosmoplat退款接口--退款接口需要临时拼接，这里只写前段
    public static final String ORDER_REFUND = "http://api.cbasetest.cosmoplat.com//api/out/order/whole/";

    //cosmoplat取消接口--取消接口需要临时拼接，这里只写前段
    public static final String ORDER_CANCEL = "http://api.cbasetest.cosmoplat.com/api/out/order/";

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws Exception {
        //下单的例子
		//String param = "buyer={\"id\":\"306516\"}&controlKey=8127&delivery={\"province\":\"山东省\",\"city\":\"淄博市\",\"region\":\"张店区\",\"detail\":\"政通路4516号\",\"consignee\":\"张良\",\"phone\":\"17694298058\",\"zip\":\"255000\"}&invoice={\"type\":\"3\",\"title\":\"个人\"}&items={\"item\":[{\"skuId\":\"60541\",\"quantity\":\"1\",\"fee\":\"100000\"}]}&order={\"serviceType\":\"001\",\"note\":\"请于周末送达\",\"remark\":\"下单满减200\"}&pay={\"stages\":{\"stage\":[{\"current_stage\":\"1\",\"fee\":\"100\"},{\"current_stage\":\"2\",\"fee\":\"100\"}]}}&seller={\"id\":\"306555\"}";
        /*String param = "buyer={\"id\":586130}&controlKey=7696&delivery={\"province\":\"山东省\",\"city\":\"青岛市\",\"region\":\"崂山区\",\"detail\":\"海尔工业园\",\"consignee\":\"张三\",\"phone\":\"1777777777\",\"zip\":\"255000\",\"extra\":\"\"}&invoice={\"type\":3,\"title\":\"个人\"}&items=[{\"sellerId\":586161,\"skuId\":65903,\"quantity\":1,\"fee\":1}]&order={\"serviceType\":\"001\",\"note\":\"\",\"remark\":\"\"}";
        String secret = MD5.md5("appKey="+APP_KEY+"&"+param+"&key="+SECRET).toUpperCase();
		String sign = "{\"appKey\":\""+ APP_KEY +"\",\"secret\":\""+secret+"\"}";
		System.out.println(param);
		System.out.println(secret);
		HashMap map = new HashMap();
		String result = HttpRequestUtil.sendPost(COS_ORDER2_URL, param+"&sign="+sign);
		System.out.println(result);*/
        /*String param = "buyer={\"id\":586180}&controlKey=7680&delivery={\"province\":\"山东省\",\"city\":\"青岛市\",\"region\":\"崂山区\",\"detail\":\"海尔工业园\",\"consignee\":\"586180\",\"phone\":\"15166076329\",\"zip\":\"255000\",\"extra\":\"\"}&invoice={\"type\":3,\"title\":\"个人\"}&items=[{\"sellerId\":586161,\"skuId\":66041,\"quantity\":1,\"fee\":1}]&order={\"serviceType\":\"001\",\"note\":\"\",\"remark\":\"\"}";
        String secret = MD5.md5("appKey="+APP_KEY+"&"+param+"&key="+SECRET).toUpperCase();
		String sign = "{\"appKey\":\""+ APP_KEY +"\",\"secret\":\""+secret+"\"}";
		System.out.println(param);
		System.out.println(secret);
		String result = HttpRequestUtil.sendPost(COS_ORDER2_URL, param+"&sign="+sign);
		System.out.println(result);*/

		/*String param = "buyer={\"id\":586154}&controlKey=7680&delivery={\"province\":\"山东省\",\"city\":\"青岛市\",\"region\":\"崂山区\",\"detail\":\"海尔工业园\"," +
				"\"consignee\":\"586247\",\"phone\":\"18653375349\",\"zip\":\"255000\",\"extra\":\"\"}&invoice={\"type\":3,\"title\":\"个人\"}" +
				"&items=[{\"sellerId\":586247,\"skuId\":66344,\"quantity\":1,\"fee\":10}]&order={\"serviceType\":\"001\",\"note\":\"\",\"remark\":\"\"}";
		String secret = MD5.md5("appKey="+APP_KEY+"&"+param+"&key="+SECRET).toUpperCase();
		String sign = "{\"appKey\":\""+ APP_KEY +"\",\"secret\":\""+secret+"\"}";
        String result = HttpRequestUtil.sendPost(COS_ORDER2_URL, param+"&sign="+sign);
		System.out.println(result);*/

		//创建用户的例子
		/*String param2 = "mobile=1787044944&nickName=demo";
		String secret2 = MD5.md5("appKey="+ APP_KEY+"&"+param2+"&key="+SECRET).toUpperCase();
		System.out.println(param2);
		String sign2 = "{\"appKey\":\""+ APP_KEY +"\",\"secret\":\""+secret2+"\"}";
        System.out.println(param2+"&sign="+sign2);
		String result = HttpRequestUtil.sendGet(FIND_USER, param2+"&sign="+sign2);
		System.out.println(result);*/

		//取消订单
        /*String param = "orderId=100000064803&userId=586130";
        String secret = MD5.md5("appKey="+APP_KEY+"&"+param+"&key="+SECRET).toUpperCase();
        String sign = "{\"appKey\":\""+ APP_KEY +"\",\"secret\":\""+secret+"\"}";
        String result = HttpRequestUtil.sendPut(ORDER_CANCEL+"100000064803/cancel", param+"&sign="+sign);
        System.out.println(result);*/

        //退款
        String param = "orderId=100000065500&refundReason=\"货有问题\"&userId=586273";
        String secret = MD5.md5("appKey="+APP_KEY+"&"+param+"&key="+SECRET).toUpperCase();
        String sign = "{\"appKey\":\""+ APP_KEY +"\",\"secret\":\""+secret+"\"}";
        String result = HttpRequestUtil.sendPut(ORDER_REFUND+"100000065500/applyRefund", param+"&sign="+sign);
        System.out.println(result);

        //创建店铺
       /* String param = "shop={" +
				"\"name\":\"定智旅行家\"," +
				"\"realRightType\":\"1\"," +
				"\"companyCode\":\"90M0\"," +
				"\"quickPassAccount\":\"300003017789\"," +
				"\"rate\":\"600\"," +
				"\"delivery\":\"2\"," +
				"\"stock\":\"2\"," +
				"\"shopType\":\"11\"," +
				"\"customerCode\":\"\"," +
				"\"sellerName\":\"\"," +
				"\"sellerCity\":\"\"" +
				"}&user={" +
				"\"nickname\":\"smartTourist\"," +
				"\"displayName\":\"定智旅行家\"," +
				"\"email\":\"\"," +
				"\"mobile\":\"15947621896\"," +
				"\"passwd\":\"123456\"" +
				"}";
        String secret = MD5.md5("appKey="+APP_KEY+"&"+param+"&key="+SECRET).toUpperCase();
		System.out.println(param);
		System.out.println(secret);
		HashMap map = new HashMap();
		String result = HttpRequestUtil.sendPost(NEW_SHOP, param+"&appKey="+APP_KEY+"&secret="+secret);
		System.out.println(result);*/

        //创建商品
		/*String param = "itemDto={\"isHaier\":false,\"isVirtual\":false,\"item\":{\"businessType\":\"A11\",\"taxRate\":600,\"taxTypeCode\":\"3040603000000000000\",\"userId\":586278,\"name\":\"2019春节玩转纯净新西兰南-北岛\",\"mainImage\":\"www.baidu.com\",\"originPrice\":10,\"outerItemId\":\"154599773399299773399\"},\"skus\":[{\"sku\":{\"outerId\":\"154599773399299773399\",\"image\":\"www.baidu.com\",\"name\":\"2019春节玩转纯净新西兰南-北岛\",\"desc\":\"2019春节玩转纯净新西兰南-北岛\",\"price\":10,\"stockQuantity\":9999}}]}";
		String secret = MD5.md5("appKey="+APP_KEY+"&"+param+"&key="+SECRET).toUpperCase();
		String sign = "{\"appKey\":\""+ APP_KEY +"\",\"secret\":\""+secret+"\"}";
		System.out.println(param+"&appKey="+APP_KEY+"&secret="+secret);
		String result = HttpRequestUtil.sendPost(NEW_ITEMS, param+"&sign="+sign);
		System.out.println(result);*/
	}
}