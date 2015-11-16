package mds.ufscar.br.ssunb;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class MyLocationListener implements LocationListener {

    Context context;
    Activity act;
    private Location mLocation;
    private LocationManager mlocManager;
    final private int REQUEST_CODE_ASK_PERMISSIONS= 123;

    public MyLocationListener(Context context, Activity act) {
        this.context = context;
        this.act = act;
        mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void updateLocation() {
        try {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
            }
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            this.mLocation = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        catch (Exception e) {
            Toast.makeText(context, "Can't get GPS Location!", Toast.LENGTH_SHORT).show();
        }
    }

    public Location getLocation() {
        updateLocation();
        return mLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.mLocation.setLatitude(location.getLatitude());
        this.mLocation.setLongitude(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(context, "Gps Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context, "Gps Disabled", Toast.LENGTH_SHORT ).show();
    }
}
