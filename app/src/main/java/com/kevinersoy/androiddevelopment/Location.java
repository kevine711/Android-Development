package com.kevinersoy.androiddevelopment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Location.OnFragmentLocationListener} interface
 * to handle interaction events.
 * Use the {@link Location#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Location extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentLocationListener mListener;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final int REQUEST_COARSE_LOCATION = 555;
    private GoogleMap mMap;
    private Location myContext;
    private double mLatitude;
    private double mLongitude;
    private boolean mGotLatLon = false;
    private boolean mGotMap = false;

    public Location() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Location.
     */
    // TODO: Rename and change types and number of parameters
    public static Location newInstance(String param1, String param2) {
        Location fragment = new Location();
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



        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        //check location permission
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
        } else {
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
                        @Override
                        public void onSuccess(android.location.Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                onLocationUpdated(location);
                            }
                        }
                    });
        }



    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentLocationInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentLocationListener) {
            mListener = (OnFragmentLocationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentSelectorListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case REQUEST_COARSE_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Log.i("permission", "Location Permission Granted");
                try {
                    mFusedLocationProviderClient.getLastLocation()
                            .addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
                                @Override
                                public void onSuccess(android.location.Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        // Logic to handle location object
                                        onLocationUpdated(location);
                                    }
                                }
                            });
                } catch (SecurityException ex) {
                    Log.d("permission", "Location Permission Denied");
                }
        }
    }


    private void onLocationUpdated(android.location.Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        mGotLatLon = true;
        TextView locationText = (TextView)getView().findViewById(R.id.text_location);
        if(locationText != null)
            locationText.setText("Latitude: " + mLatitude + "\n Longitude: " + mLongitude);
        updateMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mGotMap = true;
        updateMap();
    }

    public void updateMap(){
        if(mGotLatLon && mGotMap){
            LatLng yourLocation = new LatLng(mLatitude, mLongitude);
            mMap.addMarker(new MarkerOptions().position(yourLocation).title(getString(R.string.your_location)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 12f));
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentLocationListener {
        // TODO: Update argument type and name
        void onFragmentLocationInteraction(Uri uri);
    }
}
