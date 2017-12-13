package io.proximi.proximiiodemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.proximi.proximiiolibrary.ProximiioAPI;
import io.proximi.proximiiolibrary.ProximiioGeofence;
import io.proximi.proximiiolibrary.ProximiioListener;
import io.proximi.proximiiomap.ProximiioMapHelper;
import io.proximi.proximiiomap.ProximiioMapView;

/**
 * Proximiio Demo
 */
public class MainActivity extends AppCompatActivity {
    private ProximiioAPI proximiioAPI;
    private ProximiioMapHelper mapHelper;

    private static final String TAG = "ProximiioDemo";

    public static final String AUTH = "AUTH_KEY_HERE"; // TODO: Replace with your own!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create our Proximi.io listener
        proximiioAPI = new ProximiioAPI(TAG, this);
        proximiioAPI.setListener(new ProximiioListener() {
            @Override
            public void geofenceEnter(ProximiioGeofence geofence) {
                Log.d(TAG, "Geofence enter: " + geofence.getName());
            }

            @Override
            public void geofenceExit(ProximiioGeofence geofence, @Nullable Long dwellTime) {
                Log.d(TAG, "Geofence exit: " + geofence.getName() + ", dwell time: " + String.valueOf(dwellTime));
            }

            @Override
            public void loginFailed(LoginError loginError) {
                Log.e(TAG, "LoginError! (" + loginError.toString() + ")");
            }
        });
        proximiioAPI.setAuth(AUTH);
        proximiioAPI.setActivity(this);

        // Initialize the map
        ProximiioMapView mapView = (ProximiioMapView)findViewById(R.id.map);
        mapHelper = new ProximiioMapHelper.Builder(this, mapView, AUTH, savedInstanceState)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapHelper.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapHelper.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapHelper.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapHelper.onDestroy();
        proximiioAPI.destroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapHelper.onLowMemory();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        proximiioAPI.onActivityResult(requestCode, resultCode, data);
        mapHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        proximiioAPI.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
