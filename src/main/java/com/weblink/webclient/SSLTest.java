package com.weblink.webclient;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @Author Melin Chao
 * @Since 2022/7/28
 */
public class SSLTest {
	static final String turl = "https://www.taishinbank.com.tw/TSB/personal/";
	static final String wurl = "https://www.weblink.com.tw";
	public static void main(String[] args)  throws Exception{
		main3(args);
	}
    public static void main1(String[] args) throws IOException {
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                SSLContexts.createDefault(),
//                new String[] { "TLSv1.2", "TLSv1.3" },
//                null,
//                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                SSLContexts.createDefault(),
                new String[] { "TLSv1.2" },
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        String url = "https://www.taishinbank.com.tw/TSB/personal/";
        //url = "https://www.weblink.com.tw";
        HttpGet get = new HttpGet(url);
        get.setHeader("Content-Type", "text/plain; charset=utf-8");
        get.setHeader("Expect", "100-continue");
        HttpResponse resp = httpClient.execute(get);
        String body = EntityUtils.toString(resp.getEntity());
        
        System.out.println(body);
        System.out.println(resp.toString());
    }
    
    public static void main2(String args[]) throws IOException {
    	  URL url = new URL(turl);
          HttpURLConnection http = (HttpURLConnection) url.openConnection();
          http.setRequestMethod("GET");
    	
          InputStream input = http.getInputStream();
          
          byte[] data = new byte[1024];
          int idx = 0;
          while((idx = input.read(data))> 0) {
	          String str = new String(data, 0, idx);
	          System.out.println(str);
          }
          
          input.close();
          http.disconnect();
    }
    
    public static void main3(String args[]) throws Exception {
  	  	URL url = new URL(wurl);
		TrustManager[] trustAllCerts = new TrustManager[] { 
			new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
	
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}
	
				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}
			} 
		};
		
		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		 // Init the SSLContext with a TrustManager[] and SecureRandom()
		 sc.init(null, trustAllCerts, new java.security.SecureRandom());
		 
		 HttpsURLConnection http = (HttpsURLConnection) url.openConnection();
		 http.setSSLSocketFactory(sc.getSocketFactory());
		 
		 http.setRequestMethod("GET");
	    	
         InputStream input = http.getInputStream();
         
         byte[] data = new byte[1024];
         int idx = 0;
         while((idx = input.read(data))> 0) {
	          String str = new String(data, 0, idx);
	          System.out.println(str);
         }
         
         input.close();
         http.disconnect();		 
    }
    
    

}
