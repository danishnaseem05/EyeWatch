package Lib;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;


public class Http_Client {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static JSONObject getRequest(String url) throws IOException, JSONException {
        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return processRequest(response);
        }
    }

    private static JSONObject processRequest(CloseableHttpResponse response) throws IOException, JSONException {
        // Get HttpResponse Status
        GUI.appendLog(response.getStatusLine().toString());
        System.out.println(response.getStatusLine().toString());

        HttpEntity entity = response.getEntity();
        Header headers = entity.getContentType();
        GUI.appendLog(headers.toString());
        System.out.println(headers);

        JSONObject result = null;
        if (entity != null) {
            String retSrc = EntityUtils.toString(entity);
            result = new JSONObject(retSrc);
            //System.out.println(result);
        }
        return result;
    }

}
