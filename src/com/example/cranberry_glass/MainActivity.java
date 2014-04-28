package com.example.cranberry_glass;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cranberry_glass.model.CranberryJsonEvaluator;
import com.example.cranberry_glass.model.SensorNodes;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class MainActivity extends Activity {
    protected static final String TAG = MainActivity.class.getSimpleName();
    protected SensorNodes nodes;
    private LineChartView linechart;
    private TextView headerView;
    private TextView footerView;

    private GestureDetector gestureDetector;
    private ProgressBar mProgress;

    private final String TIDMARSH_URL = "http://chain-api.media.mit.edu/sites/7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headerView = (TextView) findViewById(R.id.header);
        footerView = (TextView) findViewById(R.id.footer);
        footerView = (TextView) findViewById(R.id.units);
        footerView.setText("Loading node data ...");

        gestureDetector = createGestureDetector(this);
        linechart = (LineChartView) findViewById(R.id.linechart);

        Thread downloadThread = new Thread() {
            // CranberryServerManager server = new CranberryServerManager();
            CranberryJsonEvaluator evaluator = new CranberryJsonEvaluator(
                    TIDMARSH_URL);

            public synchronized void run() {
                System.out.println("TEST NEW API");
                try {
                    nodes = new SensorNodes(evaluator.getListOfNodes(evaluator
                            .getSiteJSON()));
                    linechart.post(new Runnable() {
                        public void run() {
                            footerView.setText(nodes.getCurrentSensorType());
                            linechart.setChartData(nodes.getCurrentChartData(),
                                    nodes.getCurrentNodeId());

                        }
                    });
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);
        // Create a base listener for generic gestures
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {

            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    // do something on tap
                    nodes.setZerothSensor();
                    return true;
                } else if (gesture == Gesture.TWO_TAP) {
                    // do something on two finger tap
                    return true;
                } else if (gesture == Gesture.SWIPE_RIGHT) {
                    // do something on right (forward) swipe
                    Log.i(TAG, "swipe right");
                    if (nodes != null) {
                        nodes.shiftNodeRight();
                        float[] chartData = nodes.getCurrentChartData();

                        if (chartData.length != 0) {
                            linechart.setChartData(chartData,
                                    nodes.getCurrentNodeId());
                            footerView.setText(nodes.getCurrentSensorType());
                        }

                    }
                    return true;
                } else if (gesture == Gesture.SWIPE_LEFT) {
                    // do something on left (backwards) swipe
                    Log.i(TAG, "swipe left");
                    if (nodes != null) {

                        nodes.shiftNodeLeft();

                        float[] chartData = nodes.getCurrentChartData();

                        if (chartData.length != 0) {
                            linechart.setChartData(chartData,
                                    nodes.getCurrentNodeId());
                            footerView.setText(nodes.getCurrentSensorType());
                        }

                    }
                    return true;
                } else if (gesture == Gesture.TWO_SWIPE_LEFT) {
                    // do something on left (backwards) swipe
                    Log.i(TAG, "two swipe left");
                    // if (nodes != null) {
                    //
                    // nodes.shiftSensorLeft();
                    //
                    // float[] chartData = nodes.getCurrentChartData();
                    //
                    // if (chartData.length != 0) {
                    // linechart.setChartData(chartData,
                    // nodes.getCurrentNodeId());
                    // footerView.setText(nodes.getCurrentSensorType());
                    // }
                    // }
                    return true;
                } else if (gesture == Gesture.TWO_SWIPE_RIGHT) {
                    // do something on left (backwards) swipe
                    Log.i(TAG, "two swipe right");
                    // if (nodes != null) {
                    //
                    // nodes.shiftSensorRight();
                    // float[] chartData = nodes.getCurrentChartData();
                    //
                    // if (chartData.length != 0) {
                    // linechart.setChartData(chartData,
                    // nodes.getCurrentNodeId());
                    // footerView.setText(nodes.getCurrentSensorType());
                    // }
                    //
                    // }
                    // return true;
                }
                return false;
            }

        });
        return gestureDetector;
    }

    /*
     * Send generic motion events to the gesture detector
     */
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (gestureDetector != null) {
            return gestureDetector.onMotionEvent(event);
        }
        return false;
    }
}
