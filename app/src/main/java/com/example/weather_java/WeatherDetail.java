package com.example.weather_java;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherDetail newInstance(String param1, String param2) {
        WeatherDetail fragment = new WeatherDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_weather_detail, container, false);

        Intent intent= requireActivity().getIntent();
        String lat=intent.getStringExtra("latitude");
        String lon=intent.getStringExtra("longitude");
        AndroidNetworking.initialize(requireContext());
        String wURL="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&appid=566833ab7b2054dcc5d36df7030d84b4";
       //String weather_URL="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"units=metric&appid=566833ab7b2054dcc5d36df7030d84b4";
        AndroidNetworking.get(wURL).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject obj=response.getJSONObject("list");
                    JSONObject main=response.getJSONObject("main");
                    double temp=main.getDouble("temp");
                    double humidity=main.getDouble("humidity");
                    double pressure=main.getDouble("pressure");
                    JSONArray weatherD=response.getJSONArray("weather");
                    JSONObject weather=weatherD.getJSONObject(0);
                    //JSONObject a=weather.getJSONObject("")
                    String icon_uri=weather.getString("icon");
                    String icon_url = "https://openweathermap.org/img/wn/" + icon_uri + "@2x.png";
//                    double temp_c=Math.round();
                    JSONObject location=response.getJSONObject("sys");
                    String country=location.getString("country");
                    int sunrise=location.getInt("sunrise");
                    int sunset=location.getInt("sunset");
                    int date=response.getInt("dt");
                    String city=response.getString("name");
                    Uri imageUri=Uri.parse(icon_url);
                    TextView cityname=v.findViewById(R.id.cityname);
                    TextView countryname=v.findViewById(R.id.countryname);
                    TextView tempc=v.findViewById(R.id.temp);
                    TextView humid=v.findViewById(R.id.humid);
                    TextView pres=v.findViewById(R.id.pressure);
ImageView icon=v.findViewById(R.id.weathericon);
                    Glide.with(requireActivity()).load(icon_url).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.outline_block_24).into(icon);
                    cityname.setText(city);
                    countryname.setText(country);
                    tempc.setText(String.valueOf(temp));
                    humid.setText(String.valueOf(humidity));
                    pres.setText(String.valueOf(pressure));


                } catch (JSONException e) {
                    Toast.makeText(requireContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(requireContext(), "Error:"+anError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}