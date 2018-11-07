package com.example.hunar_parneet.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class WeatherForecast extends Activity {

    private static final String ACTIVITY_NAME = "WeatherForecast";

    String iconName = "";

    ImageView image;
    ProgressBar pBar;
    TextView currTem;
    TextView minTem;
    TextView maxTem;
    TextView winSpeed;

    public Bitmap picture;
    XmlPullParser xpp;


    public static Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static Bitmap getImage(String urlString) {
        try {
            URL url = new URL(urlString);
            return getImage(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }


    public boolean fileExist(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        pBar = findViewById(R.id.pbar);
        pBar.setVisibility(View.VISIBLE);
        image = findViewById(R.id.image);
        currTem = findViewById(R.id.currentTemp);
        minTem = findViewById(R.id.minTemp);
        maxTem = findViewById(R.id.maxTemp);
        winSpeed = findViewById(R.id.windSpeed);
        ForecastQuery weatherQuery = new ForecastQuery();
        weatherQuery.execute();

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        public String windSpeed;
        public String minTemp;
        public String maxTemp;
        public String currentTemp;


        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");

            HttpURLConnection conn = null;

                conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);

                conn.setRequestMethod("GET");

            conn.setDoInput(true);
            // Starts the query

                conn.connect();

                xpp = Xml.newPullParser();
                xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xpp.setInput(conn.getInputStream(), "UTF-8");

                while (xpp.next() != XmlPullParser.END_DOCUMENT) {
                    if (xpp.getEventType() == XmlPullParser.START_TAG) {

String name = xpp.getName();
                        if (name.equals("temperature")) {
                            currentTemp = xpp.getAttributeValue(null, "value");

                            minTemp = xpp.getAttributeValue(null, "min");

                            maxTemp = xpp.getAttributeValue(null, "max");
                            publishProgress(25);

                        } else if (xpp.getName().equals("speed") ){
                            windSpeed = xpp.getAttributeValue(null, "value");
                            publishProgress(50);
                        } else if (xpp.getName().equals("weather") ){
                            publishProgress(75);
                            iconName = xpp.getAttributeValue(null, "icon");
                                if(fileExist(iconName+".png"))
                                {
                                    FileInputStream fis = null;
                                    Log.i(ACTIVITY_NAME,"Filename: "+iconName+" exists");
                                    try {    fis = openFileInput(iconName+".png");   }
                                    catch (FileNotFoundException e) {    e.printStackTrace();  }
                                    picture  = BitmapFactory.decodeStream(fis);
                                }
                                else {
                                    picture = getImage("http://openweathermap.org/img/w/" + iconName + ".png");
                                    //save picture:
                                    FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();

                                }

                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return currentTemp;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            pBar.setVisibility(View.VISIBLE);
            pBar.setProgress(value[0]);
            currTem.setText("Current Temp: "+currentTemp);
            minTem.setText("Minimum Temp: "+minTemp);
            maxTem.setText("Maximum Temp: "+maxTemp);
            winSpeed.setText("Wind speed: "+windSpeed);

        }

        @Override
        protected void onPostExecute(String result) {

            image.setImageBitmap(picture);
            pBar.setVisibility(View.INVISIBLE);


        }


    }
}

