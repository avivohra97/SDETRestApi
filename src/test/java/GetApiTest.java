import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.qe.Base.TestBase;
import org.qe.Client.RestClient;
import org.qe.Client.Users;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class GetApiTest extends TestBase {
    TestBase base;
    String serviceUrl;
    String url;
    String apiUrl;
    Properties prop;

    @BeforeMethod
    public void setup() throws IOException {
        base = new TestBase();
        base.base();
        prop = base.properties;
        serviceUrl = prop.getProperty("URL");
        apiUrl = prop.getProperty("path");
         url = serviceUrl+apiUrl;
    }

    @Test
    public void restClient() throws IOException {
        RestClient client = new RestClient();
        CloseableHttpResponse resp = client.get(url);
        int statusCode = resp.getStatusLine().getStatusCode();

        System.out.println("status code "+statusCode);
        Assert.assertEquals(statusCode,RESPONSE_200);
        String responseString = EntityUtils.toString(resp.getEntity(),"UTF-8");

        JSONObject respJson = new JSONObject(responseString);
        System.out.println("response json :"+respJson);
        System.out.println(respJson.getClass().getName());

        Header[] headers = resp.getAllHeaders();
        HashMap<String,String> map = new HashMap<>();
        for(Header header:headers){
            map.put(header.getName(),header.getValue());
        }
        System.out.println("headers :"+headers);

        HashMap<String,String>  header= new HashMap<>();
        header.put("Content-Type","application/json");
        CloseableHttpResponse respHeader = client.get(url,header);
        String responseStringHeader = EntityUtils.toString(respHeader.getEntity(),"UTF-8");

        JSONObject respJsonHeader = new JSONObject(responseStringHeader);
        System.out.println("response json :"+respJsonHeader);

        url = serviceUrl+prop.getProperty("postpath");
        String postBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        CloseableHttpResponse postResp = client.post(url, postBody, header);
        String postRespString = EntityUtils.toString(postResp.getEntity(),"UTF-8");

        JSONObject respPost = new JSONObject(postRespString);
        System.out.println("response json :"+respPost);

//        delete
        System.out.println(respPost.get("id"));
        String id = (String) respPost.get("id");
        String delUrl= serviceUrl+prop.getProperty("delpath")+id;
        System.out.println(delUrl);
        CloseableHttpResponse delResp = client.delete(delUrl);
        System.out.println("response json :"+delResp);

//        create object mapper
        Users users = new Users("morpheus","leader");

        ObjectMapper objectMapper = new ObjectMapper();
//        object to json file
//        objectMapper.writeValue(file, users);

//        object to json string
        String jsonString = objectMapper.writeValueAsString(users);
        System.out.println(jsonString);
        postResp = client.post(url,jsonString,header);
        postRespString = EntityUtils.toString(postResp.getEntity(),"UTF-8");

        respPost = new JSONObject(postRespString);
        System.out.println("response json :"+respPost);
//      json to users object
        Users respUser = objectMapper.readValue(postRespString, Users.class);
        System.out.println(respUser);


    }
}
