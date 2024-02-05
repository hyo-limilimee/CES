package com.android.buddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class map extends AppCompatActivity implements OnMapReadyCallback {

    private SearchView searchView;
    private GoogleMap mMap;
    EditText search2;
    Button search_bt;


    String strAddress;

    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        searchView = findViewById(R.id.searchView);
        search_bt = findViewById(R.id.search_bt);
        search2 = findViewById(R.id.search2);


        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment MainFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        MainFragment.getMapAsync(this);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com"));
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
    } //인스타 구현 완료, 여기서 손 대지 않기(구조적으로)

    //지도 안에서의 기능 -> 위치마크 / 줌 기능
    @Override
    public void onMapReady(@NonNull GoogleMap googlemap) {


        geocoder = new Geocoder(this);
        mMap = googlemap;
        LatLng seoul = new LatLng(37.56, 126.97);
        mMap.addMarker(new MarkerOptions().position(seoul).title("Marker in Seoul"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));

        mMap.getUiSettings().setZoomGesturesEnabled(true);

        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(seoul);
        builder.zoom(20);


        search_bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {


                String str = search2.getText().toString();
                List<Address> listAddress = null;

                try {
                    listAddress = geocoder.getFromLocationName(str, 10);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(listAddress.get(0).toString());

                String[] splitStr = listAddress.get(0).toString().split(",");
                String Address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,
                        splitStr[0].length() - 2);
                System.out.println(Address);

                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1);
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1);

                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title("search result");
                mOptions2.snippet(Address);
                mOptions2.position(point);
                mMap.addMarker(mOptions2);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
            }
        });
    
     search2.setOnKeyListener(new View.OnKeyListener()

    {

        public boolean onKey (View view,int keyCode, KeyEvent event){
        switch (keyCode) {
            case android.view.KeyEvent.KEYCODE_ENTER:
                String str = search2.getText().toString();
                break;
        }
        return true;
    }
    });
}
    public boolean onKey(View view, int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            switch (view.getId()){
                case R.id.search2:
                    search2.setText(search2.getText());
                    break;
            }
        }
        return true;
    }
}

