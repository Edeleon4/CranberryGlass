package com.example.cranberry_glass.model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class CranberryServerManager {

  public JSONObject getJSONFromURL(String URL)
      throws ClientProtocolException, IOException, JSONException {
      DefaultHttpClient httpclient = new DefaultHttpClient();
      HttpGet request = new HttpGet(URL);
      request.setHeader("Accept", "application/json");
      // Handles what is returned from the page
      HttpResponse httpResponse = httpclient.execute(request);
      // Grab the response
      BufferedReader reader = new BufferedReader(new InputStreamReader(
              httpResponse.getEntity().getContent(), "UTF-8"));
      StringBuilder builder = new StringBuilder();
      for (String line = null; (line = reader.readLine()) != null;) {
          builder.append(line).append("\n");
      }
      String getRequestResult = builder.toString();
      JSONObject obj = new JSONObject(getRequestResult);
      return obj;
      }

}
