package com.example.cranberry_glass;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import com.example.cranberry_glass.model.CranberryJsonEvaluator;
import com.example.cranberry_glass.model.Node;

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
			CranberryJsonEvaluator evaluator = new CranberryJsonEvaluator(tidmarshURL);
		    public void run() {     
				try {
					ArrayList<Node> x = evaluator.getListOfNodes(evaluator.getSiteJSON());
					System.out.println(x);
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
