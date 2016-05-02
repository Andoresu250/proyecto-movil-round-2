package miltoncasanova.proyectomovil2;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyMapFragment extends MapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback{

    public GoogleApiClient mGoogleApiClient;
    public Location mCurrentLocation;
    public GoogleMap mGoogleMap;


    public final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    public int curMapTypeIndex = 1;


    public void setCameraMarket(){
        MarkerOptions options = new MarkerOptions().position( new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude()) );
        options.title("Foto");
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource( getResources(),
                        R.drawable.ic_my_camera ) ));
        mGoogleMap.addMarker( options );
    }

    public void setRecorderaMarket(){
        MarkerOptions options = new MarkerOptions().position( new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude()) );
        options.title("Foto");
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource( getResources(),
                        R.drawable.ic_my_mic ) ));
        mGoogleMap.addMarker( options );
    }

    public void setTextaMarket(){
        double lat = mCurrentLocation.getLatitude();
        double lng = mCurrentLocation.getLongitude();
        Log.d("asd",""+lat+" "+lng);
        LatLng latLng = new LatLng(lat, lng);

        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("Foto");
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource( getResources(),
                        R.drawable.ic_text ) ));
        System.out.println();
        Log.d("asd",""+mGoogleMap.getMapType());
        mGoogleMap.addMarker(options);
        getMap();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);


        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        this.getMapAsync(this);
    }



    private void initListeners() {
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapLongClickListener(this);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setOnMapClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void initCamera(Location location) {
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),
                        location.getLongitude()))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);
        mGoogleMap.setMapType(MAP_TYPES[curMapTypeIndex]);

        mGoogleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        initCamera(mCurrentLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("asd");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        mGoogleMap.addMarker(options);
        Log.d("asd",latLng.toString());
    }


    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        initListeners();
        Log.d("asd","map is ready");
        Log.d("asd",""+mGoogleMap.getMapType());
    }
}

