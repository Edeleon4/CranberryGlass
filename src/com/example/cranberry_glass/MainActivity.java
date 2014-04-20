package com.example.cranberry_glass;

import java.io.IOException;

import org.json.JSONException;

import com.example.cranberry_glass.model.CranberryJsonEvaluator;
//import com.example.cranberry_glass.model.SensorNodes;
//import com.google.android.glass.touchpad.Gesture;
//import com.google.android.glass.touchpad.GestureDetector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MotionEvent;

public class MainActivity extends Activity {
	private final String tidmarshURL = "http://chain-api.media.mit.edu/sites/7";
    //protected SensorNodes nodes;
   // private GestureDetector gestureDetector;
    private LineChartView linechart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
//        gestureDetector = createGestureDetector(this);

        linechart = (LineChartView) findViewById(R.id.linechart);
        linechart.setChartData(new float[]{ 1, 2,3, 4,5,6,7,8,9,10}); 
        
		Thread downloadThread = new Thread() {  
			//CranberryServerManager server = new CranberryServerManager();
			CranberryJsonEvaluator evaluator = new CranberryJsonEvaluator(tidmarshURL);
			
		    public void run() {     
				System.out.println("TEST NEW API");
				try {
					System.out.println(evaluator.getListOfNodes(evaluator.getSiteJSON()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//try {
					//nodes = new SensorNodes(evaluator.getListOfNodes(evaluator.getSiteJSON()));
					//linechart.setChartData(nodes.getCurrentData());
				//} catch (IOException e) {
				//	e.printStackTrace();
				//} catch (JSONException e) {
					//e.printStackTrace();
				//}
		    	
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
//	private GestureDetector createGestureDetector(Context context) {
//	    GestureDetector gestureDetector = new GestureDetector(context);
//	        //Create a base listener for generic gestures
//	        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
//	            @Override
//	            public boolean onGesture(Gesture gesture) {
//	                if (gesture == Gesture.TAP) {
//	                    // do something on tap
//	                    return true;
//	                } else if (gesture == Gesture.TWO_TAP) {
//	                    // do something on two finger tap
//	                    return true;
//	                } else if (gesture == Gesture.SWIPE_RIGHT) {
//	                    // do something on right (forward) swipe
//	                    //linechart.setChartData(nodes.getNodeShiftRightData());
//	                    return true;
//	                } else if (gesture == Gesture.SWIPE_LEFT) {
//	                    // do something on left (backwards) swipe
//	                    //linechart.setChartData(nodes.getNodeShiftLeftData());
//	                    return true;
//	                }else if (gesture == Gesture.TWO_SWIPE_LEFT) {
//                        // do something on left (backwards) swipe
//	                    //linechart.setChartData(nodes.getSensorShiftRightData());
//                        return true;
//                    }else if (gesture == Gesture.TWO_SWIPE_RIGHT) {
//                        // do something on left (backwards) swipe
//                        //linechart.setChartData(nodes.getSensorShiftLeftData());
//                        return true;
//                    }
//	                return false;
//	            }
//
//	        });
//	        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
//	            @Override
//	            public void onFingerCountChanged(int previousCount, int currentCount) {
//	              // do something on finger count changes
//	            }
//	        });
//	        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
//	            @Override
//	            public boolean onScroll(float displacement, float delta, float velocity) {
//                    return false;
//	                // do something on scrolling
//	            }
//	        });
//	        return gestureDetector;
//	    }
//
//	    /*
//	     * Send generic motion events to the gesture detector
//	     */
//	    @Override
//	    public boolean onGenericMotionEvent(MotionEvent event) {
//	        if (gestureDetector != null) {
//	            return gestureDetector.onMotionEvent(event);
//	        }
//	        return false;
//	    }
}
