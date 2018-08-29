package centrale.localisation.lunettes_appli;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements SensorEventListener, LocationListener, View.OnClickListener {

    private String Accessibles= "toilettes accessibles: oui, ascenseur accessible: oui";
    private TextView view;
    Float azimut;
    CustomDrawableView customDrawableView;
    private SensorManager sensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    float[] mGravity;
    float[] mGeomagnetic;

    //    Location fixedLocation;
    private float bearing;
    private Graphe graphe;
    private Noeud noeudDepart;
    private LocationManager locationManager;
    private ArrayList<Noeud> chemin;

    private static final float MARGE_DISTANCE = 5;

    String[] itemSpinner = {"S1","S2","S3","S4","S5","S6","S7","S8","S9"};
    private AlertDialog.Builder alertDialogBuilderDepart;

    private Noeud noeudDestination;
    int indiceNoeudIntermediaire;


    private void init() {
//        initFixedLOcation();
        initManagers();
        Log.i("TEST", "+++++++++++++" + getLastKnownLocation(locationManager).getLongitude());
        noeudDepart = getNoeudProche(getLastKnownLocation(locationManager));
        Log.i("NOEUD", "------" + noeudDepart.toString());
        //noeudDepart = graphe.getGraphList().get(0);
        indiceNoeudIntermediaire = 0;
    }

//    private void initFixedLOcation() {
//        fixedLocation = new Location("");
//        fixedLocation.setLatitude(48.8510);
//        fixedLocation.setLongitude(2.3456);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customDrawableView = new CustomDrawableView(this);
        setContentView(customDrawableView);
        //setContentView(R.layout.activity_main);








        init();
        //Log.i("LIST", "======" + chemin.toString());
        afficherdisponibilités();
        afficherChoixDestination();

    }


    private void afficherChoixDestination() {
        alertDialogBuilderDepart = new AlertDialog.Builder(this);
        alertDialogBuilderDepart.setTitle("Choix Destination");
        alertDialogBuilderDepart.setMessage("Où voulez vous aller ?");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,itemSpinner);

        final Spinner spinner = new Spinner(this);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                noeudDestination = graphe.getGraphList().get(i);
                chemin = graphe.getShortestChemin(noeudDepart, noeudDestination);
                indiceNoeudIntermediaire += 1;
                Log.i("LIST", "======" + chemin.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        alertDialogBuilderDepart.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialogBuilderDepart.setView(spinner);
        alertDialogBuilderDepart.create();
        alertDialogBuilderDepart.show();
    }

    private void afficherdisponibilités(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Les disponibilités");
        builder.setMessage(Accessibles);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        //builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
           // @Override
           // public void onClick(DialogInterface dialog, int which) {
           // }
       // });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @SuppressLint("MissingPermission")
    private void initManagers() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,5, this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mGravity = sensorEvent.values;
        }
        if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            mGeomagnetic = sensorEvent.values;
        }
        if(mGravity != null && mGeomagnetic != null){
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R,I,mGravity,mGeomagnetic);
            if(success){
                float orientation[] = new float[3];
                SensorManager.getOrientation(R,orientation);
                azimut = orientation[0];
            }
        }
        customDrawableView.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if( indiceNoeudIntermediaire < chemin.size()) {
            bearing = location.bearingTo(chemin.get(indiceNoeudIntermediaire).toLocation());
            double longitude = chemin.get(indiceNoeudIntermediaire).getCoordonnées()[0];
            double latitude = chemin.get(indiceNoeudIntermediaire).getCoordonnées()[1];
            float distance = calculDistance(location, longitude, latitude);
            if( distance <= MARGE_DISTANCE ) {
                afficherMessageArrivee();
            }

        }

    }

    private void afficherMessageArrivee() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle("Arrivée destination");
        alertDialogBuilder
                .setMessage("Êtes-vous arrivé à " + chemin.get(0).getNom() + " ?")
                .setCancelable(false)
                .setPositiveButton("Oui",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        chemin.remove(0);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Non",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        noeudDepart = getNoeudProche(getLastKnownLocation(locationManager));
                        chemin = graphe.getShortestChemin(noeudDepart, graphe.getNoeud(Lieu.S9));
                        indiceNoeudIntermediaire =0;
                        dialog.cancel();
                    }
                });

        alertDialogBuilder.create().show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private Noeud getNoeudProche( Location currentLocation ) {
        graphe = new Graphe();
        float distanceMin = Float.MAX_VALUE;
        Noeud noeudProche = null;
        ArrayList<Noeud> noeuds = graphe.getGraphList();
        for (Noeud noeud : noeuds) {
            double noeudLongitude = noeud.getCoordonnées()[0];
            double noeudLatitude = noeud.getCoordonnées()[1];
            if( calculDistance( currentLocation, noeudLatitude, noeudLongitude ) < distanceMin )
                noeudProche = noeud;
        }
        return noeudProche;
    }

    private float calculDistance(Location currentLocation, double noeudLatitude, double noeudLongitude) {
        Location destination = new Location("");
        destination.setLongitude(noeudLongitude);
        destination.setLatitude(noeudLatitude);
        return currentLocation.distanceTo( destination );
    }

    private Location getLastKnownLocation( LocationManager locationManager ) {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        boolean x = true;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                x = false;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if ((bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) && x) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }



    @Override
    public void onClick(View view) {
        Graphe.removeLienTo(chemin.get(indiceNoeudIntermediaire-1),chemin.get(indiceNoeudIntermediaire));
        noeudDepart = chemin.get(indiceNoeudIntermediaire-1);
        chemin = graphe.getShortestChemin(noeudDepart,noeudDestination);
        indiceNoeudIntermediaire = 0;

    }


    public class CustomDrawableView extends View {
        Paint paint = new Paint();

        public CustomDrawableView(Context context){
            super(context);
            paint.setColor(0xff00ff00);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setAntiAlias(true);
        }

        public void onDraw (Canvas canvas){
            int width = getWidth();
            int height = getHeight();
            int centerx = width/2;
            int centery = height/2;
            //canvas.drawLine(centerx,0, centerx, height, paint);
            //canvas.drawLine(0, centery, width, centery, paint);
//            canvas.drawPicture();

//            Picture




            // Rotate the canvas with the azimut. /!\ A changer
            if (azimut != null) {

                canvas.rotate((-azimut*360/(2*3.14159f)) - bearing, centerx, centery);
//                canvas.rotate(-azimut*360/(2*3.14159f), centerx, centery);
            }
            paint.setColor(0xff0000ff);
            //canvas.drawLine(centerx, -1000, centerx, +1000, paint);
            //canvas.drawLine(-1000, centery, 1000, centery, paint);
            canvas.drawText("N", centerx+5, centery-10, paint);
            canvas.drawText("S", centerx-10, centery+15, paint);

            Path path = new Path();
            path.moveTo(centerx, centery+100);
            path.lineTo(centerx+50, centery);
            path.lineTo(centerx-50, centery);
            path.close();
            path.offset(0, 0);
            canvas.drawPath(path, paint);

            paint.setColor(0xff00ff00);
        }
    }
}
