package com.example.cranberry_glass;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import com.example.cranberry_glass.model.CranberryJsonEvaluator;
import com.example.cranberry_glass.model.Node;
import com.example.cranberry_glass.model.SensorNodes;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final String tidmarshURL = "http://tidmarsh.media.mit.edu/api/sites/7";
    protected SensorNodes nodes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
        final LineChartView linechart = (LineChartView) findViewById(R.id.linechart);
        linechart.setChartData(new float[]{ 1, 2,3, 4,5,6,7,8,9,10}); 
        
		Thread downloadThread = new Thread() {  
			//CranberryServerManager server = new CranberryServerManager();
			CranberryJsonEvaluator evaluator = new CranberryJsonEvaluator(tidmarshURL);
		    public void run() {     
				try {
					nodes = new SensorNodes(evaluator.getListOfNodes(evaluator.getSiteJSON()));
					linechart.setChartData(nodes.getCurrentData());
					Toast.makeText(getBaseContext(), "completed download", Toast.LENGTH_LONG);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
		    	
		    }
		};
		downloadThread.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
