package com.capinfo.utils;
/**
 * java 访问https忽略证书 处理
 * @author Administrator
 *
 */

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpUtils {
	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {

		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};
	 public static String doPost(String url, Map<String, Object> map) throws Exception {
		 CloseableHttpClient httpClient = HttpClients.createDefault();
		  // 声明httpPost请求
		  HttpPost httpPost = new HttpPost(url);

		  // 判断map不为空
		  if (map != null) {
		   // 声明存放参数的List集合
		   List<NameValuePair> params = new ArrayList<NameValuePair>();

		   // 遍历map，设置参数到list中
		   for (Map.Entry<String, Object> entry : map.entrySet()) {
			   
			   if(entry.getValue()!=null&&!"".equals(entry.getValue())){
				   params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			   }
		   
		   }

		   // 创建form表单对象
		   UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");

		   // 把表单对象设置到httpPost中
		   httpPost.setEntity(formEntity);
		  }

		  // 使用HttpClient发起请求，返回response
		  CloseableHttpResponse response = httpClient.execute(httpPost);

		  // 解析response封装返回对象httpResult
		  /*HttpResult httpResult = null;
		  if (response.getEntity() != null) {
		   httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
		     EntityUtils.toString(response.getEntity(), "UTF-8"));
		  } else {
		   httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
		  }
		  System.out.println(httpResult);*/
		  String result=null;
		  if (response.getEntity() != null) {
			  result= EntityUtils.toString(response.getEntity(), "UTF-8");
		  }
		  
		  
		  
		  // 返回结果
		  return result;
		 }
	 
	 /**
	     * 发送post请求
	     * 
	     * @author Michael -----CSDN: http://blog.csdn.net/capmiachael
	     * @param params
	     *            参数
	     * @param requestUrl
	     *            请求地址
	     * @param authorization
	     *            授权书
	     * @return 返回结果
	     * @throws IOException
	     */
	    public static String sendPost(String params, String requestUrl,
	            String authorization) throws IOException {
//	    	System.out.println(params);
	        byte[] requestBytes = params.getBytes("utf-8"); // 将参数转为二进制流
	        HttpClient httpClient = new HttpClient();// 客户端实例化
	        PostMethod postMethod = new PostMethod(requestUrl);
	        //设置请求头Authorization
	        postMethod.setRequestHeader("Authorization", "Basic " + authorization);
	        // 设置请求头  Content-Type
	        postMethod.setRequestHeader("Content-Type", "application/json");
	        InputStream inputStream = new ByteArrayInputStream(requestBytes, 0,
	                requestBytes.length);
	        RequestEntity requestEntity = new InputStreamRequestEntity(inputStream,
	                requestBytes.length, "application/json; charset=utf-8"); // 请求体
	        postMethod.setRequestEntity(requestEntity);
	        int statusCode = httpClient.executeMethod(postMethod);// 执行请求，状态，一般200为OK状态，其他情况会抛出如404,500,403等错误
	        if (statusCode != HttpStatus.SC_OK) {  
	        	throw new RuntimeException("请求返回错误状态");  
	         }
	        InputStream soapResponseStream = postMethod.getResponseBodyAsStream();// 获取返回的流
	        byte[] datas = null;
	        try {
	            datas = readInputStream(soapResponseStream);// 从输入流中读取数据
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        String result = new String(datas, "UTF-8");// 将二进制流转为String
	        // 打印返回结果
	        // System.out.println(result);

	        return result;

	    }
	    
	    
	    public static String sendPost12345(String params, String requestUrl,
	            String authorization) throws IOException {
//	    	System.out.println(params);
	        byte[] requestBytes = params.getBytes("utf-8"); // 将参数转为二进制流
	        HttpClient httpClient = new HttpClient();// 客户端实例化
	        PostMethod postMethod = new PostMethod(requestUrl);
	        //设置请求头Authorization
	        postMethod.setRequestHeader("Authorization", "Bearer 59969f51e4bf03b8c32e48ad4b9021b1");
//	        postMethod.setRequestHeader("Cookie", "hollysmart.session.id=9af4e2b724e64a2195c1bdad92a1a576");
	        // 设置请求头  Content-Type
	        postMethod.setRequestHeader("Content-Type", "application/json");
	        InputStream inputStream = new ByteArrayInputStream(requestBytes, 0,
	                requestBytes.length);
	        RequestEntity requestEntity = new InputStreamRequestEntity(inputStream,
	                requestBytes.length, "application/json; charset=utf-8"); // 请求体
	        postMethod.setRequestEntity(requestEntity);
	        int statusCode = httpClient.executeMethod(postMethod);// 执行请求，状态，一般200为OK状态，其他情况会抛出如404,500,403等错误
	        if (statusCode != HttpStatus.SC_OK) {  
	        	throw new RuntimeException("请求返回错误状态");  
	         }
	        InputStream soapResponseStream = postMethod.getResponseBodyAsStream();// 获取返回的流
	        byte[] datas = null;
	        try {
	            datas = readInputStream(soapResponseStream);// 从输入流中读取数据
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        String result = new String(datas, "UTF-8");// 将二进制流转为String
	        // 打印返回结果
	        // System.out.println(result);

	        return result;

	    }


	/**
	 *
	 * @param params			参数
	 * @param requestUrl		请求地址
	 * @param tokenStr		12345不同账号的不同token	授权书		authorization	设置在请求的header中
	 * @return
	 */
	public static String sendPost12345ByTokenStr(String params, String requestUrl, String tokenStr) throws IOException {

		byte[] requestBytes = params.getBytes("utf-8"); // 将参数转为二进制流
		HttpClient httpClient = new HttpClient();// 客户端实例化
		PostMethod postMethod = new PostMethod(requestUrl);
		//设置请求头Authorization
		String authorizationStr = "Bearer " + tokenStr;
		postMethod.setRequestHeader("Authorization", authorizationStr);
		//postMethod.setRequestHeader("Authorization", "Bearer 59969f51e4bf03b8c32e48ad4b9021b1");
		// 设置请求头  Content-Type
		postMethod.setRequestHeader("Content-Type", "application/json");
		InputStream inputStream = new ByteArrayInputStream(requestBytes, 0, requestBytes.length);
		RequestEntity requestEntity = new InputStreamRequestEntity(inputStream, requestBytes.length, "application/json; charset=utf-8"); // 请求体
		postMethod.setRequestEntity(requestEntity);
		int statusCode = httpClient.executeMethod(postMethod);// 执行请求，状态，一般200为OK状态，其他情况会抛出如404,500,403等错误
		if (statusCode != HttpStatus.SC_OK) {
			throw new RuntimeException("请求返回错误状态");
		}
		InputStream soapResponseStream = postMethod.getResponseBodyAsStream();// 获取返回的流
		byte[] datas = null;
		try {
			datas = readInputStream(soapResponseStream);// 从输入流中读取数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = new String(datas, "UTF-8");// 将二进制流转为String
		return result;
	}




	    
	    /**
	     * 从输入流中读取数据
	     * 
	     * @param inStream
	     * @return
	     * @throws Exception
	     */
	    public static byte[] readInputStream(InputStream inStream) {
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        byte[] data = null;
	        int len = 0;
	        try {
				while ((len = inStream.read(buffer)) != -1) {
				    outStream.write(buffer, 0, len);
				}
				data = outStream.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					outStream.close();
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	        return data;
	    }
	    
	    public static String httpGet(String uri) {
	    	String result="";
//			StringBuffer tempStr = new StringBuffer();
//			String responseContent = "";
			HttpURLConnection conn = null;
			try {
				// Create a trust manager that does not validate certificate chains
				trustAllHosts();

				URL url = new URL(uri);

				HttpsURLConnection https = (HttpsURLConnection) url
						.openConnection();
				if (url.getProtocol().toLowerCase().equals("https")) {
					https.setHostnameVerifier(DO_NOT_VERIFY);
					conn = https;
				} else {
					conn = (HttpURLConnection) url.openConnection();
				}
				conn.connect();
//				System.out.println(conn.getResponseCode() + " "
//						+ conn.getResponseMessage());
				result = conn.getResponseMessage();
				// HttpURLConnection conn = (HttpURLConnection)
				// url.openConnection();

				// conn.setConnectTimeout(5000);
				// conn.setReadTimeout(5000);
				// conn.setDoOutput(true);
				//
				// InputStream in = conn.getInputStream();
				// conn.setReadTimeout(10*1000);
				// BufferedReader rd = new BufferedReader(new InputStreamReader(in,
				// "UTF-8"));
				// String tempLine;
				// while ((tempLine = rd.readLine()) != null) {
				// tempStr.append(tempLine);
				// }
				// responseContent = tempStr.toString();
				// System.out.println(responseContent);
				// rd.close();
				// in.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	    
		private static void trustAllHosts() {

			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[] {};
				}

				public void checkClientTrusted(X509Certificate[] chain,
						String authType) {

				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) {

				}
			} };

			// Install the all-trusting trust manager
			try {
				SSLContext sc = SSLContext.getInstance("TLS");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection
						.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public  static String doGet(String paramUrl){
			 try {  
				  
		            URL url = new URL(paramUrl);    // 把字符串转换为URL请求地址  
		  
		            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接  
		  
		            connection.connect();// 连接会话  
		  
		            // 获取输入流  
		  
		            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
		  
		            String line;  
		  
		            StringBuilder sb = new StringBuilder();  
		  
		            while ((line = br.readLine()) != null) {// 循环读取流  
		  
		                sb.append(line);  
		  
		            }  
		  
		            br.close();// 关闭流  
		  
		            connection.disconnect();// 断开连接  
		  
		            System.out.println(sb.toString());  
		            return sb.toString();
		  
		        } catch (Exception e) {  
		  
		            e.printStackTrace();  
		  
		            System.out.println("失败!"); 
		            return null;
		  
		        }  

		 }
		public static String doget1(String path){
			try {
	            URL url = new URL(path.trim());
	            //打开连接
	            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

	            if(200 == urlConnection.getResponseCode()){
	                //得到输入流
	                InputStream is =urlConnection.getInputStream();
	                ByteArrayOutputStream baos = new ByteArrayOutputStream();
	                byte[] buffer = new byte[1024];
	                int len = 0;
	                while(-1 != (len = is.read(buffer))){
	                    baos.write(buffer,0,len);
	                    baos.flush();
	                }
	                return baos.toString("utf-8");
	            }
	        }  catch (IOException e) {
	            e.printStackTrace();
	        }

	        return null;
		}
	 
}
