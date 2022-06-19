package com.example.test.ui.map;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.test.AttractionActivity;
import com.example.test.ui.attractions.AttractionsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.example.test.R;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap mMap;
    int[] images = {R.drawable.jackson_memorial, R.drawable.visotskiy, R.drawable.sevastyanov_house,
            R.drawable.temple_na_krovi, R.drawable.popov_museum, R.drawable.mayakovskiy_park,
            R.drawable.novotichvinskiy_monastery, R.drawable.black_tulip, R.drawable.rastorguev_manor,
            R.drawable.dendropark};
    String[] titles;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        titles = getResources().getStringArray(R.array.attractions);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.zoomTo(11));
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.info_window, null);
                TextView text = view.findViewById(R.id.infoWindow_text);
                ImageView image = view.findViewById(R.id.infoWindow_picture);
                int index = Integer.parseInt(marker.getTitle());
                text.setText(titles[index]);
                image.setImageResource(images[index]);
                return view;
            }
        });

        /*mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        });*/

        /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.isInfoWindowShown()) {
                    marker.();
                } else {
                    marker.showInfoWindow();
                }
                return false;
            }
        });*/

        SetMarker("Памятник Майклу Джексону", "0",  56.8295, 60.5971917);
        SetMarker("Небоскрёб Высоцкий", "1", 56.836092, 60.614473);
        SetMarker("Дом Севастьянова", "2", 56.839033, 60.60593);
        SetMarker("Храм на Крови", "3", 56.8443809, 60.6088771);
        SetMarker("Музей радио имени А.С.Попова", "4", 56.8334760, 60.6129970);
        SetMarker("ЦПКиО имени В.В.Маяковского", "5", 56.811389, 60.645278);
        SetMarker("Ново-Тихвинский монастырь", "6", 56.822778, 60.598611);
        SetMarker("Чёрный тюльпан", "7", 56.843139, 60.617361);
        SetMarker("Усадьба Расторгуевых-Харитоновых", "8", 56.845636, 60.611111);
        SetMarker("Екатеринбургский дендропарк", "9",56.848, 60.651);

    }

    private void SetMarker(String attractionTitle, String index ,double latitude, double longitude){
        LatLng mapPoint = new LatLng(latitude, longitude);
        MarkerOptions marker = new MarkerOptions().position(mapPoint).title(index);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mapPoint));
    }
}
