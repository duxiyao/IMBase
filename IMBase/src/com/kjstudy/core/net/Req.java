package com.kjstudy.core.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
 

import org.myframe.MHttp;
import org.myframe.http.HttpCallBack;
import org.myframe.http.HttpParams;

import android.util.Log;

public class Req {

	private static String mHost = "http://www.doctorsclub.cn";
	private static String mHandler = "/handlers/";
	private static String mUrlPre = mHost + mHandler;
	
	public static void login(String name,String pwd,HttpCallBack cb){
		String url=mUrlPre+"HLogin.ashx";
        MHttp kjh = new MHttp();
        HttpParams params = new HttpParams(); 
        params.put("email", name);
        params.put("pwd", pwd);
        kjh.post(url, params,false,cb);
	}

	protected List<NameValuePair> mNvps = new ArrayList<NameValuePair>();
	protected String url;

	public Req(String url) {
		this.url = url;
	}

	public static Req c(String url) {
		Req req = new Req(url);
		return req;
	}

	public Req add(String k, String v) {
		mNvps.add(new BasicNameValuePair(k, v));
		return this;
	}

	// public String exe(){
	//
	//
	// String ret = "";
	// CloseableHttpClient httpclient = HttpClients.createDefault();
	// try {
	// HttpPost httpPost = new HttpPost(url);
	// httpPost.setEntity(new UrlEncodedFormEntity(mNvps));
	// CloseableHttpResponse response = httpclient.execute(httpPost);
	//
	// try {
	// System.out.println(response.getStatusLine());
	// HttpEntity entity = response.getEntity();
	// // BufferedReader reader = new BufferedReader(
	// // new InputStreamReader(entity.getContent(), "UTF-8"));
	// // ret = reader.readLine();
	// ret = EntityUtils.toString(entity);
	// System.out.println(ret);
	// } finally {
	// response.close();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// httpclient.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// return ret;
	//
	// }
	//
	// public String exeHttps() {
	// String ret = "";
	// // CloseableHttpClient httpclient = HttpClients.createDefault();
	// // CloseableHttpClient httpclient = HttpClients.custom().build();
	// CloseableHttpClient httpclient = null;
	// try {
	// httpclient =/*getDefaultHttpClient();*/new
	// CcopHttpClient().registerSSL("TLS", 8084, "https");
	// HttpPost httpPost = new HttpPost(url);
	// httpPost.setEntity(new UrlEncodedFormEntity(mNvps));
	// CloseableHttpResponse response = httpclient.execute(httpPost);
	//
	// try {
	// System.out.println(response.getStatusLine());
	// HttpEntity entity = response.getEntity();
	// // BufferedReader reader = new BufferedReader(
	// // new InputStreamReader(entity.getContent(), "UTF-8"));
	// // ret = reader.readLine();
	// ret = EntityUtils.toString(entity);
	// System.out.println(ret);
	// } finally {
	// response.close();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// httpclient.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// return ret;
	// }
	//
	// public static CloseableHttpClient createSSLInsecureClient() {
	// try {
	// SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
	// null, new TrustStrategy() {
	// // 信任所有
	// public boolean isTrusted(X509Certificate[] chain,
	// String authType) throws CertificateException {
	// return true;
	// }
	//
	// @Override
	// public boolean isTrusted(
	// java.security.cert.X509Certificate[] arg0,
	// String arg1)
	// throws java.security.cert.CertificateException {
	// // TODO Auto-generated method stub
	// return false;
	// }
	// }).build();
	// SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	// sslContext);
	// return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	// } catch (KeyManagementException e) {
	// e.printStackTrace();
	// } catch (NoSuchAlgorithmException e) {
	// e.printStackTrace();
	// } catch (KeyStoreException e) {
	// e.printStackTrace();
	// }
	// return HttpClients.createDefault();
	// }
	//
	// private DefaultHttpClient getDefaultHttpClient() {
	// BasicHttpParams httpParams = new BasicHttpParams();
	// HttpConnectionParams.setConnectionTimeout(httpParams, 10);
	// HttpConnectionParams.setSoTimeout(httpParams, 10);
	// // 创建一个默认的HttpClient
	// DefaultHttpClient httpsClient = new DefaultHttpClient();
	// // 请求超时
	// // httpsClient.getParams().setParameter(
	// // CoreConnectionPNames.CONNECTION_TIMEOUT, CLIENT_TIME_OUT);
	// // // 读取超时
	// // httpsClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
	// // CLIENT_TIME_OUT);
	// // 创建SSL连接
	// SSLContext ctx;
	// try {
	// ctx = SSLContext.getInstance("TLS");
	//
	// // 鏈嶅姟绔瘉涔﹂獙璇�姝ゆ帴鍙ｇ殑瀹炰緥绠＄悊浣跨敤鍝竴涓�X509 璇佷功鏉ラ獙璇佽繙绔殑瀹夊叏濂楁帴瀛�
	// X509TrustManager tm = new X509TrustManager() {
	//
	// @Override
	// public void checkClientTrusted(
	// java.security.cert.X509Certificate[] chain,
	// String authType)
	// throws java.security.cert.CertificateException {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void checkServerTrusted(
	// java.security.cert.X509Certificate[] chain,
	// String authType)
	// throws java.security.cert.CertificateException {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	// };
	//
	// // 鍒濆鍖朣SL涓婁笅鏂�
	// /**
	// * TrustManager 杩欐槸鐢ㄤ簬 JSSE 淇′换绠＄悊鍣ㄧ殑鍩烘帴鍙� SecureRandom
	// * 姝ょ被鎻愪緵鍔犲瘑鐨勫己闅忔満鏁扮敓鎴愬櫒
	// */
	// ctx.init(null, new TrustManager[] { tm },
	// new java.security.SecureRandom());
	//
	// SSLSocketFactory socketFactory = new SSLSocketFactory(ctx,
	// SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	// Scheme sch = new Scheme("https", socketFactory, 8084);
	// // 注册SSL连接
	// httpsClient.getConnectionManager().getSchemeRegistry()
	// .register(sch);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return httpsClient;
	// }
	//
	// private CloseableHttpClient getHttps() throws Exception {
	// KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	// trustStore.load(null, null);
	//
	// SSLSocketFactoryEx sf = new SSLSocketFactoryEx(trustStore);
	// // Trust own CA and all self-signed certs
	// SSLContext sslcontext = sf.getSSLContext();
	// // Allow TLSv1 protocol only
	// SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	// sslcontext, new String[] { "TLSv1" }, null,
	// SSLConnectionSocketFactory.getDefaultHostnameVerifier());
	// CloseableHttpClient httpclient = HttpClients.custom()
	// .setSSLSocketFactory(sf.getSocketFactory()).build();
	// return httpclient;
	// // try {
	// //
	// // HttpGet httpget = new HttpGet("https://localhost/");
	// //
	// // System.out.println("executing request " + httpget.getRequestLine());
	// //
	// // CloseableHttpResponse response = httpclient.execute(httpget);
	// // try {
	// // HttpEntity entity = response.getEntity();
	// //
	// // System.out.println("----------------------------------------");
	// // System.out.println(response.getStatusLine());
	// // EntityUtils.consume(entity);
	// // } finally {
	// // response.close();
	// // }
	// // } finally {
	// // httpclient.close();
	// // }
	//
	// }
	//
	// /**
	// * HttpClient连接SSL
	// */
	// public void ssl() {
	// CloseableHttpClient httpclient = null;
	// try {
	// KeyStore trustStore = KeyStore.getInstance(KeyStore
	// .getDefaultType());
	// FileInputStream instream = new FileInputStream(new File(
	// "d:\\test.keystore"));
	// try {
	// // 加载keyStore d:\\tomcat.keystore
	// trustStore.load(instream, "123456".toCharArray());
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// instream.close();
	// } catch (Exception ignore) {
	// }
	// }
	// // 相信自己的CA和所有自签名的证书
	// SSLContext sslcontext = SSLContexts
	// .custom()
	// .loadTrustMaterial(trustStore,
	// new TrustSelfSignedStrategy()).build();
	// // 只允许使用TLSv1协议
	// SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	// sslcontext,
	// new String[] { "TLSv1" },
	// null,
	// SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	// httpclient = HttpClients.custom().setSSLSocketFactory(sslsf)
	// .build();
	// // 创建http请求(get方式)
	// HttpGet httpget = new HttpGet(
	// "https://localhost:8443/myDemo/Ajax/serivceJ.action");
	// System.out.println("executing request" + httpget.getRequestLine());
	// CloseableHttpResponse response = httpclient.execute(httpget);
	// try {
	// HttpEntity entity = response.getEntity();
	// System.out.println("----------------------------------------");
	// System.out.println(response.getStatusLine());
	// if (entity != null) {
	// System.out.println("Response content length: "
	// + entity.getContentLength());
	// System.out.println(EntityUtils.toString(entity));
	// EntityUtils.consume(entity);
	// }
	// } finally {
	// response.close();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// // } catch (IOException e) {
	// // e.printStackTrace();
	// // } catch (KeyManagementException e) {
	// // e.printStackTrace();
	// // } catch (NoSuchAlgorithmException e) {
	// // e.printStackTrace();
	// // } catch (KeyStoreException e) {
	// // e.printStackTrace();
	// } finally {
	// if (httpclient != null) {
	// try {
	// httpclient.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

}
