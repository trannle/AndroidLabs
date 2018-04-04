package com.example.tran.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {

    ProgressBar progBar;
    TextView minTemp;
    TextView maxTemp;
    TextView currentTemp;
    ImageView wPicture;
    TextView wind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        this.wind= findViewById(R.id.windSpeed);
        this.progBar=findViewById(R.id.progressBar);
        this.minTemp = findViewById(R.id.minTemp);
        this.maxTemp= findViewById(R.id.maxTemp);
        this.currentTemp= findViewById(R.id.currentTemp);
        this.wPicture= findViewById(R.id.currentWeatherpic);

        progBar.setVisibility(View.VISIBLE);

        ForecastQuery foreCast = new ForecastQuery();
        foreCast.execute("hey");

        wPicture.setVisibility(View.VISIBLE);
    }


    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String windSpeed = "";
        String min = "";
        String max = "";
        Bitmap weatherPic;
        String value = "";
        String iCon="";

        public Bitmap getImage(URL url) {
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

        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }


        protected String doInBackground(String... args) {

            try {

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();

                InputStream iStream = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                //parser object to iterate through xml
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(iStream, "UTF-8");


                //if type is not at the end of the xml document
                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {


                    if (xpp.getEventType() == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("temperature")) {
                            this.value = xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            this.min = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            this.max = xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                        }
                        if (xpp.getName().equals("speed"))
                            this.windSpeed = xpp.getAttributeValue(null, "value");

                        if (xpp.getName().equals("weather")) {
                            this.iCon = xpp.getAttributeValue(null, "icon");
                        }
                    }

                    xpp.next();
                    // type=xpp.getEventType();
                }
                Log.i("value", this.iCon);
                Log.i("min", this.min);
                Log.i("value", this.windSpeed);

               if (!fileExistance(this.iCon+".png")) {
                    this.weatherPic = this.getImage("http://openweathermap.org/img/w/"+this.iCon +".png");
                    FileOutputStream outputStream = openFileOutput(this.iCon+".png", Context.MODE_PRIVATE);
                    this.weatherPic.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i("filename", "i'm looking for"+ iCon +".png");
                    Log.i("download", "i had to download this image");
                    publishProgress(100);

                } else {
                    FileInputStream fis = null;
                    fis = openFileInput(this.iCon +".png");
                    this.weatherPic = BitmapFactory.decodeStream(fis);
                    Log.i("filename", "i'm looking for" + iCon + ".png");
                    Log.i("local", "this is a local image");
                    publishProgress(100);
                }


            } catch (FileNotFoundException e) {
                Log.e("filenotfound", "no image found");
                e.printStackTrace();
            } catch (Exception mfe) {
                Log.e("Error", mfe.getMessage());
            }

            return "";

        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }


        protected void onProgressUpdate(Integer... progress) {
            progBar.setProgress(progress[0]);

        }

        protected void onPostExecute(String result) {
            Log.i("test","this is onPostExecute");

          minTemp.setText(minTemp.getText()+": "+min);
          maxTemp.setText(maxTemp.getText()+": "+max);
          currentTemp.setText(currentTemp.getText()+": "+value);
          wPicture.setImageBitmap(weatherPic);
          wind.setText(wind.getText()+": "+windSpeed);

            progBar.setVisibility(View.INVISIBLE);


        }

    }

}



