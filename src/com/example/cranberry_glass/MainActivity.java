package com.example.cranberry_glass;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cranberry_glass.model.CranberryServerManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
public class MainActivity extends Activity {
	private final String tidmarshURL = "http://tidmarsh.media.mit.edu/api/sites/7";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		Thread downloadThread = new Thread() {  
			//CranberryServerManager server = new CranberryServerManager();
		    public void run() {                                    
		    	try {
		    		CranberryServerManager server = new CranberryServerManager();
		    		JSONObject siteJson = server.getJSONFromURL(tidmarshURL);
		    		JSONObject siteDevicesValues = (JSONObject) siteJson.get("devices");
		    		String siteDevicesLink = siteDevicesValues.get("_href").toString();
		    		System.out.println(siteJson);
		    		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
		    		System.out.println(server.getJSONFromURL(siteDevicesLink));
//TODO  cache of data of each sensor and update per request.lang lend 0.0.0 gps locations
		    		//TODO google places?
		    		
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		};
		downloadThread.start();
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}