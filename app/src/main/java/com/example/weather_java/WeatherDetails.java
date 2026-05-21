package com.example.weather_java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class WeatherDetails extends AppCompatActivity {
    TextView aqi_data,comp1,val1,comp2,val2,comp3,val3,comp4,val4,comp5,val5,err;
    Toolbar aqi_act;
    TabLayout tblyt;
    ViewPager vpgr;
    LottieAnimationView weather;
    com.github.mikephil.charting.charts.PieChart pieChart;
    Intent compositionVals, ageHealth;

    LinearLayout v1,v2,v3,v4,v5;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        String lat=getIntent().getStringExtra("latitude") ;
//        String lon=getIntent().getStringExtra("longitude");
       aqi_act=findViewById(R.id.weather_tlbr);
//        aqi_data=findViewById(R.id.aqi_level);
        //weather=findViewById(R.id.weather_animation);
//        comp1=findViewById(R.id.comp1);
//        val1=findViewById(R.id.val1);
//        comp2=findViewById(R.id.comp2);
//        val2=findViewById(R.id.val2);
//        comp3=findViewById(R.id.comp3);
//        val3=findViewById(R.id.val3);
//        comp4=findViewById(R.id.comp4);
//        val4=findViewById(R.id.val4);
//        comp5=findViewById(R.id.comp5);
//        val5=findViewById(R.id.val5);
//        err=findViewById(R.id.error);
//        pieChart=findViewById(R.id.piechart);
//        v1=findViewById(R.id.v1);
//        v2=findViewById(R.id.v2);
//        v3=findViewById(R.id.v3);
//        v4=findViewById(R.id.v4);
//        v5=findViewById(R.id.v5);
//
//
//         compositionVals=new Intent(WeatherDetails.this,PieChart.class);
//        ageHealth=new Intent(this, health_and_age.class);
//
       setSupportActionBar(aqi_act);
 Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setTitle("Pollution details");
       getSupportActionBar();
//       weather.playAnimation();
        tblyt=findViewById(R.id.weatheraqitabs);
        vpgr=findViewById(R.id.updatepager);
        ParyavaranAdaptor adp=new ParyavaranAdaptor(getSupportFragmentManager());
        vpgr.setAdapter(adp);
        tblyt.setupWithViewPager(vpgr);
//        weather.pauseAnimation();
//
//        String url_AQL="https://api.openweathermap.org/data/2.5/air_pollution?lat="+lat+"&lon="+lon+"&appid=566833ab7b2054dcc5d36df7030d84b4";
//        String weather_URL="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=566833ab7b2054dcc5d36df7030d84b4";
//        AndroidNetworking.get(weather_URL).build().getAsJSONObject(new JSONObjectRequestListener() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {Log.d("response",response.toString());}
//                catch (Exception e){
//                    err.setText(e.toString());
//                }
//
//                //res.setText(response.toString());
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fetchAPI(lat,lon,url_AQL);
//            }
//        },2000);
    }
//    private void fetchAPI(String latitude,String longitude,String uri){
//                weather.playAnimation();
//
//        AndroidNetworking.get(uri).build().getAsJSONObject(new JSONObjectRequestListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray list = response.getJSONArray("list");
//                    JSONObject data = list.getJSONObject(0);
//                    JSONObject main = data.getJSONObject("main");
//                    int aqi = main.getInt("aqi");  // ✅ aqi is an integer, not an object
//
//                    JSONObject components = data.getJSONObject("components");
//
//                    double no2 = components.getDouble("no2");
//                    double o3 = components.getDouble("o3");
//                    double so2 = components.getDouble("so2");
//                    double pm2_5 = components.getDouble("pm2_5");
//                    double pm10 = components.getDouble("pm10");
//
//                    ArrayList<PieEntry> values=new ArrayList<>();
//                    values.add(new PieEntry((float)no2,"NO₂"));
//                    values.add(new PieEntry((float)o3,"O₃"));
//                    values.add(new PieEntry((float)so2,"SO₂"));
//                    values.add(new PieEntry((float)pm2_5,"PM2.5"));
//                    values.add(new PieEntry((float)pm10,"PM10"));
//                    PieDataSet datas=new PieDataSet(values,"Air Composition details");
//                    datas.setColors(ColorTemplate.COLORFUL_COLORS);
//                    datas.setSliceSpace(3f);
//                    datas.setValueTextSize(10f);
//                    datas.setValueTextColor(getResources().getColor(android.R.color.white));
//
//                    PieData pdata=new PieData(datas);
//                    pieChart.setData(pdata);
//                    pieChart.setUsePercentValues(false);
//                    pieChart.getDescription().setEnabled(false);
//                    pieChart.setCenterText("Air Composition");
//                    pieChart.setCenterTextSize(15f);
//                    pieChart.animateY(1400);
//
////
//                    compositionVals.putExtra("no2",no2);
//                    compositionVals.putExtra("o3",o3);
//                    compositionVals.putExtra("so2",so2);
//                    compositionVals.putExtra("pm2_5",pm2_5);
//                    compositionVals.putExtra("pm10",pm10);
//                    compositionVals.putExtra("aqi",aqi);
//                    //startActivity(compositionVals);
//
//
//
//
//
//                    weather.cancelAnimation();
//                    weather.setVisibility(View.GONE);
//
//                    comp1.setText("NO₂");
//                    val1.setText(String.valueOf(no2));
//
//                    aqi_data.setText(String.valueOf(aqi));
//
//
//                    comp2.setText("O₃");
//                    val2.setText(String.valueOf(o3));
//
//                    comp3.setText("SO₂");
//                    val3.setText(String.valueOf(so2));
//
//
//
//
//                    comp4.setText("PM2.5");
//                    val4.setText(String.valueOf(pm2_5));
//
//                    comp5.setText("PM10");
//                    val5.setText(String.valueOf(pm10));
//
//                    v1.setBackgroundColor(R.color.red);
//                    v2.setBackgroundColor(R.color.orange);
//                    v3.setBackgroundColor(R.color.yellow);
//                    v4.setBackgroundColor(R.color.light_green);
//                    v5.setBackgroundColor(R.color.green);
//
//
//
//                    // Optional: show AQI value too
//                    // err.setText("AQI: " + aqi);
//                    pieChart.setVisibility(View.VISIBLE);
//
//
//                } catch (JSONException e) {
//                    err.setText(e.toString());
//                }
//            }
//
//
//
//            @Override
//            public void onError(ANError anError) {
//                anError.printStackTrace();
//                Log.d("ERROR",anError.toString());
//            }
//        });
//    }
//
//            @Override
//            public void onError(ANError anError) {
//                anError.printStackTrace();
//                Log.d("ERROR",anError.toString());
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//       getMenuInflater().inflate(R.menu.pollution_detail_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if(item.getItemId()==R.id.age){
//            startActivity(compositionVals);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}