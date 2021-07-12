package com.example.googlemapsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.googlemapsapp.model.ListLocationModel;
import com.example.googlemapsapp.model.LocationModel;
import com.example.googlemapsapp.network.ApiClient;
import com.example.googlemapsapp.network.ApiService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemapsapp.databinding.ActivityMapsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LocationModel> mListMarker = new ArrayList<>();

    // separando para el infowindow
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getAllDataLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.none:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void getAllDataLocation() {

        final ProgressDialog proDialog = new ProgressDialog(this);
        proDialog.setMessage("Espera....");
        proDialog.show();

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<ListLocationModel> call = apiService.getAllLocation();

        call.enqueue(new Callback<ListLocationModel>() {
            @Override
            public void onResponse(Call<ListLocationModel> call, Response<ListLocationModel> response) {
                proDialog.dismiss();
                mListMarker = response.body().getmData();
                initMarker(mListMarker);
            }

            @Override
            public void onFailure(Call<ListLocationModel> call, Throwable t) {
                proDialog.dismiss();
                Toast.makeText(MapsActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initMarker(List<LocationModel> mListMarker) {
        for (int i = 0; i<mListMarker.size(); i++){
            LatLng location = new LatLng(Double.parseDouble(mListMarker.get(i).getLatitude()),
                    Double.parseDouble(mListMarker.get(i).getLongitude()));

            Marker marker = mMap.addMarker(new MarkerOptions().position(location)
            .title(mListMarker.get(i).getFacultad())
            .snippet(mListMarker.get(i).getDecano())
            .snippet(mListMarker.get(i).getLatitude() + " / " + mListMarker.get(i).getLongitude())
            .snippet(mListMarker.get(i).getUrl()));

            LocationModel info = new LocationModel();
            info.setDecano(mListMarker.get(i).getDecano());
            info.setLongitude(mListMarker.get(i).getLongitude());
            info.setLatitude(mListMarker.get(i).getLatitude());
            info.setUrl(mListMarker.get(i).getUrl());

            marker.setTag(info);

            LatLng latLng = new LatLng(Double.parseDouble(mListMarker.get(0).getLatitude()), Double.parseDouble(mListMarker.get(0).getLongitude()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude),12.0f));

            if(mListMarker.size() != 0)
            {
                TestInfoWindowsAdapter testIWdapter = new TestInfoWindowsAdapter(this);
                mMap.setInfoWindowAdapter(testIWdapter);
            }
        }
    }

    // IMPLEMENTANDO EL INFOWINDOWADAPTER ------------------

    private class TestInfoWindowsAdapter implements  GoogleMap.InfoWindowAdapter{

        private Context context;

        public TestInfoWindowsAdapter(Context context)
        {
            this.context = context;
        }

        @Nullable
        @Override
        public View getInfoWindow(@NonNull Marker marker) {
            return null;
        }
        @Nullable
        @Override
        public View getInfoContents(@NonNull Marker marker) {

            View view = ((Activity)context).getLayoutInflater().inflate(R.layout.info, null);

            TextView facultad = (TextView)  view.findViewById(R.id.mFacultadname);
            TextView Decano = (TextView)  view.findViewById(R.id.mFacultaddecano);
            TextView LongLati = (TextView) view.findViewById(R.id.mFacultadubicacion);
            ImageView iView = (ImageView) view.findViewById(R.id.iconImageView);



            LocationModel infomodel = (LocationModel) marker.getTag();
            URL = infomodel.getUrl();
            String decano = infomodel.getDecano();
            String ubicacion = infomodel.getLatitude() + " / " + infomodel.getLongitude();

            facultad.setText(marker.getTitle());
            Decano.setText(decano);
            LongLati.setText(ubicacion);

             Picasso.get()
                   .load(URL)
                   .error(R.mipmap.ic_launcher)
                    .into(iView, new MarkerCallBack(marker));

            Log.e("Titulo",marker.getTitle());
            Log.e("Decano",decano);
            Log.e("Ubicacion",ubicacion);
            Log.e("URL",URL);

           // Glide.with(context).load(URL).into(iView);
            return view;
        }
    }


    private class MarkerCallBack implements com.squareup.picasso.Callback {
        Marker marker = null;
        public MarkerCallBack(Marker marker) {
            this.marker = marker;
        }

        @Override
        public void onSuccess() {
            if (marker != null && marker.isInfoWindowShown()){
                marker.hideInfoWindow();
                marker.showInfoWindow();
            }
        }

        @Override
        public void onError(Exception e) {
            Log.e(getClass().getSimpleName(), "Error al cargar la miniatura");

        }
    }
}