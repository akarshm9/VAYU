package com.example.weather_java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PollutionDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PollutionDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PollutionDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PollutionDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static PollutionDetails newInstance(String param1, String param2) {
        PollutionDetails fragment = new PollutionDetails();
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
        View v= inflater.inflate(R.layout.fragment_pollution_details, container, false);
        Intent intent= requireActivity().getIntent();
        String lat=intent.getStringExtra("latitude");
        String lon=intent.getStringExtra("longitude");
        AndroidNetworking.initialize(requireActivity());

        String pollution_URL="https://api.openweathermap.org/data/2.5/air_pollution?lat="+lat+"&lon="+lon+"&appid=566833ab7b2054dcc5d36df7030d84b4";
        AndroidNetworking.get(pollution_URL).build().getAsJSONObject(new JSONObjectRequestListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray list = response.getJSONArray("list");
                    JSONObject data = list.getJSONObject(0);
                    JSONObject main = data.getJSONObject("main");
                    int aqi = main.getInt("aqi");  // ✅ aqi is an integer, not an object
                    if (aqi>3){
                        Toast.makeText(requireContext(), "Be careful! High pollution", Toast.LENGTH_SHORT).show();
                    }

                    JSONObject components = data.getJSONObject("components");

                    double no2 = components.getDouble("no2");
                    double o3 = components.getDouble("o3");
                    double so2 = components.getDouble("so2");
                    double pm2_5 = components.getDouble("pm2_5");
                    double pm10 = components.getDouble("pm10");

                    TextView aqi_level,no2_level,o3_level,so2_level,pm25_level,pm10_level;
                   aqi_level= v.findViewById(R.id.aqi_level);
                   aqi_level.setText(String.valueOf(aqi));
                   no2_level=v.findViewById(R.id.no2level);
                   no2_level.setText(String.valueOf(no2));
                   so2_level=v.findViewById(R.id.so2level);
                   so2_level.setText(String.valueOf(so2));
                   o3_level=v.findViewById(R.id.o3level);
                    o3_level.setText(String.valueOf(o3));
                    pm25_level=v.findViewById(R.id.pm25level);
                    pm25_level.setText(String.valueOf(pm2_5));
                    pm10_level=v.findViewById(R.id.pm10level);
                    pm10_level.setText(String.valueOf(pm10));
                } catch (JSONException e) {
                    Toast.makeText(requireContext(),"Error is "+e.getMessage(),Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(requireContext(), "Er is "+anError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}