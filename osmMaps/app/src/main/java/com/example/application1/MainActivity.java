package com.example.application1;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.TilesOverlay;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity {
    private MapView mapView = null;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        //TODO check permissions
        //setContentView(R.layout.activity_main);
        org.osmdroid.views.MapView mapView = (MapView)findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        //mapView.setMapCenterOffset(45,55);

        //mapView.setBuiltInZoomControls(true);
        //MapTileProviderBasic provider = new MapTileProviderBasic(getApplicationContext());
        //provider.setTileSource(TileSourceFactory.PUBLIC_TRANSPORT);
        //TilesOverlay tilesOverlay = new TilesOverlay(provider, this.getBaseContext());
        //tilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);
        //mapView.getOverlays().add(tilesOverlay);

        //Drawable marker=getResources().getDrawable(android.R.drawable.star_big_on);
        //int markerWidth = marker.getIntrinsicWidth();
        //int markerHeight = marker.getIntrinsicHeight();
        //marker.setBounds(0, markerHeight, markerWidth, 0);
        /*
        ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
        myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
        mapView.getOverlays().add(myItemizedOverlay);
        GeoPoint myPoint1 = new GeoPoint(0*1000000, 0*1000000);
        myItemizedOverlay.addItem(myPoint1, "myPoint1", "myPoint1");
        GeoPoint myPoint2 = new GeoPoint(50*1000000, 50*1000000);
        myItemizedOverlay.addItem(myPoint2, "myPoint2", "myPoint2");
        //mapView.setUseSafeCanvas(false);

         */
    }

    @Override
    public void onResume(){
        super.onResume();
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView!=null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        Configuration.getInstance().save(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView!=null) {
            mapView.onPause();
        }
    }
}