package org.qe.Client;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class RestClient {
    public CloseableHttpResponse get(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse resp = httpClient.execute(get);
        return resp;

    }
    public CloseableHttpResponse get(String url,HashMap<String,String> header) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        for(Map.Entry<String,String> entry:header.entrySet()){
            get.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse resp = httpClient.execute(get);
        return resp;
    }
    public CloseableHttpResponse post(String url,String entityString,HashMap<String,String> header) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(entityString));
        for(Map.Entry<String,String> entry:header.entrySet()){
            post.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse resp = httpClient.execute(post);
        return resp;
    }

    public CloseableHttpResponse delete(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete del = new HttpDelete(url);

        CloseableHttpResponse resp = httpClient.execute(del);
        return resp;
    }

}
