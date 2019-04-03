package com.holamundo.ciudaduniversitariainteligente;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.maps.android.PolyUtil;


import static android.content.ContentValues.TAG;
import static android.view.View.GONE;


/**
 * Created by Lautaro on 29/11/2016.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, SensorEventListener, GoogleMap.OnPolylineClickListener {


    public GoogleMap miMapa = null;
    private SensorManager miSensorManager;
    private MarkerOptions miPosicion = null;
    private Marker miPosicionMarcador = null;
    private int cantPisos = 0;
    private int pisoActual = 0;
    private int cantidad_edificios = 8;           //Cantidad de edificios relevados (8 con PREDIO)

    private Vector<PolylineOptions> misPolilineas = new Vector<>(); //Vector de polilineas, cada elemento será una polilinea para un piso
    private Vector<MarkerOptions> marcadoresPiso = new Vector<>(); //Vector de marcadores por piso, dos marcadores por polilinea por piso
    //uno en cada extremo


    private Vector<MarkerOptions> misMarcadores = new Vector<>();  //Vector de nodos para mostrar nodos sueltos (baños, bares, oficinas, etc)

    private Vector<Vector<GroundOverlayOptions>> misOverlays = new Vector<>(); //Vector de overLays, los planos de cada edificio
    //El vector de afuera es para los pisos, cada elemento es un piso
    //Cada elemento es un vector que tiene overlays de 1 o mas edificios
    //Una polilinea puede pasar por mas de un edificio en un piso

    private HashMaps miHashMaps = new HashMaps();

    private Map<String, LatLngBounds> hashMapBounds = miHashMaps.getHashMapsBound();
    private Map<String, Integer> hashMapID = miHashMaps.getHashMapID();

    private Map<LatLng, Integer> hashMapImagenes = new HashMap<>();


    private String key;


    private float angle = 0;
    private double lat;
    private double lon;

    private static View view;
    private String TAG = "so47492459";

    private Spinner spinner_colectivo = null;
    private ImageButton botonColectivo = null;
    private ImageButton botonCerrar = null;
    private ImageButton botonClear = null;

    private ImageButton pos_off = null;
    private ImageButton pos_on = null;

    ProgressDialog p;
    Boolean bandera_pos = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {

        }

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //LocationManager
        LocationManager mlocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener mlocListener = new MyLocationListener();
        mlocListener.setMainActivity(this);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (android.location.LocationListener) mlocListener);

        //SensorManager
        SensorManager mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener((SensorEventListener) this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), 1000000);
        this.miSensorManager = mSensorManager;


        //
        spinner_colectivo = (Spinner) view.findViewById(R.id.spinner_colectivo);
        spinner_colectivo.setVisibility(GONE);

        botonColectivo = (ImageButton) view.findViewById(R.id.botonColectivo);
        botonColectivo.setVisibility(View.VISIBLE);
        botonCerrar = (ImageButton) view.findViewById(R.id.botonCerrar);
        botonCerrar.setVisibility(View.GONE);

        botonClear = (ImageButton) view.findViewById(R.id.botonClear);
        botonClear.setVisibility(View.VISIBLE);

        pos_off = (ImageButton) view.findViewById(R.id.botonPosicionOff);
        pos_off.setVisibility(View.VISIBLE);

        pos_on = (ImageButton) view.findViewById(R.id.botonPosicionOn);
        pos_on.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        miMapa = googleMap;

        //Esto es para armar el grafo, clickeando encima del overlay y viendo la lat y long del punto
        miMapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("Prueba", latLng.latitude + ", " + latLng.longitude);
            }
        });

        String[] itemsSC = {" --- ", "Linea 13", "Linea 13 a CU", "Linea 13 a centro"};
        ArrayAdapter<String> arraySC = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, itemsSC);
        spinner_colectivo.setAdapter(arraySC);

        final List<LatLng> linea13_centro = Arrays.asList(
                new LatLng(-31.676926,	            -60.692626),
                new LatLng(-31.676026,	            -60.693392),
                new LatLng(-31.674954,	            -60.69432),
                new LatLng(-31.673634,	            -60.695473),
                new LatLng(-31.672736,	            -60.696089),
                new LatLng(-31.672226,	            -60.696763),
                new LatLng(-31.670816,	            -60.697939),
                new LatLng(-31.66955,	            -60.698936),
                new LatLng(-31.668067,	            -60.699839),
                new LatLng(-31.66731,	            -60.700272),
                new LatLng(-31.666477,	            -60.700559),
                new LatLng(-31.665538,	            -60.700824),
                new LatLng(-31.663714,	            -60.70108),
                new LatLng(-31.662605,	            -60.701047),
                new LatLng(-31.66193,	            -60.700899),
                new LatLng(-31.66156,	            -60.700843),
                new LatLng(-31.660934,	            -60.700768),
                new LatLng(-31.660167,	            -60.700591),
                new LatLng(-31.66024,	            -60.700175),
                new LatLng(-31.659965,	            -60.699945),
                new LatLng(-31.659967,	            -60.698898),
                new LatLng(-31.658966,	            -60.698438),
                new LatLng(-31.657312,	            -60.697767),
                new LatLng(-31.656494,	            -60.69714),
                new LatLng(-31.655608,	            -60.696313),
                new LatLng(-31.654462,	            -60.695251),
                new LatLng(-31.650891,	            -60.691989),
                new LatLng(-31.650647,	            -60.691486),
                new LatLng(-31.650482,	            -60.690783),
                new LatLng(-31.65034,	            -60.689916),
                new LatLng(-31.64994,	            -60.689131),
                new LatLng(-31.649461,	            -60.688656),
                new LatLng(-31.648593,	            -60.687983),
                new LatLng(-31.648273,	            -60.687362),
                new LatLng(-31.646663,	            -60.683392),
                new LatLng(-31.64528,	            -60.680038),
                new LatLng(-31.644617,	            -60.679374),
                new LatLng(-31.643846,	            -60.679087),
                new LatLng(-31.64279,	            -60.679113),
                new LatLng(-31.641562,	            -60.679239),
                new LatLng(-31.641308,	            -60.679421),
                new LatLng(-31.641017,	            -60.679528),
                new LatLng(-31.640818,	            -60.679469),
                new LatLng(-31.640738,	            -60.67933),
                new LatLng(-31.640746,	            -60.679145),
                new LatLng(-31.640818,	            -60.67902),
                new LatLng(-31.640926,	            -60.678859),
                new LatLng(-31.640882,	            -60.678724),
                new LatLng(-31.64087,	            -60.678632),
                new LatLng(-31.640987,	            -60.678518),
                new LatLng(-31.641046,	            -60.678414),
                new LatLng(-31.641062,	            -60.678264),
                new LatLng(-31.641014,	            -60.678088),
                new LatLng(-31.640918,	            -60.677957),
                new LatLng(-31.640848,	            -60.677797),
                new LatLng(-31.640923,	            -60.677263),
                new LatLng(-31.641065,	            -60.675805),
                new LatLng(-31.641019,	            -60.673548),
                new LatLng(-31.64091,	            -60.671911),
                new LatLng(-31.640374,	            -60.671916),
                new LatLng(-31.640284,	            -60.672139),
                new LatLng(-31.640387,	            -60.672398),
                new LatLng(-31.64039,	            -60.672909),
                new LatLng(-31.64035,	            -60.673084),
                new LatLng(-31.640427,	            -60.673217),
                new LatLng(-31.640596,	            -60.673278),
                new LatLng(-31.640949,	            -60.673198),
                new LatLng(-31.641053,	            -60.674253),
                new LatLng(-31.641088,	            -60.675882),
                new LatLng(-31.64105,	            -60.676309),
                new LatLng(-31.641095,	            -60.676543),
                new LatLng(-31.641154,	            -60.676712),
                new LatLng(-31.641222,	            -60.676935),
                new LatLng(-31.641313,	            -60.67757),
                new LatLng(-31.6413,	            -60.677997),
                new LatLng(-31.6412,	            -60.678856),
                new LatLng(-31.640789,	            -60.681042),
                new LatLng(-31.640412,	            -60.683124),
                new LatLng(-31.640277,	            -60.683616),
                new LatLng(-31.639918,	            -60.684224),
                new LatLng(-31.639499,	            -60.684717),
                new LatLng(-31.639367,	            -60.685219),
                new LatLng(-31.638844,	            -60.687575),
                new LatLng(-31.638716,	            -60.688019),
                new LatLng(-31.637721,	            -60.687714),
                new LatLng(-31.636914,	            -60.687477),
                new LatLng(-31.636695,	            -60.688536),
                new LatLng(-31.638374,	            -60.688994),
                new LatLng(-31.63959,	            -60.689406),
                new LatLng(-31.639274,	            -60.690863),
                new LatLng(-31.638914,	            -60.692498),
                new LatLng(-31.638326,	            -60.69521),
                new LatLng(-31.637974,	            -60.696878),
                new LatLng(-31.637131,	            -60.700778),
                new LatLng(-31.637305,	            -60.701125),
                new LatLng(-31.636955,	            -60.702865),
                new LatLng(-31.636464,	            -60.705037),
                new LatLng(-31.637709,	            -60.705405),
                new LatLng(-31.63862,	            -60.705733),
                new LatLng(-31.639725,	            -60.706076),
                new LatLng(-31.640207,	            -60.706212),
                new LatLng(-31.640922,	            -60.706442),
                new LatLng(-31.641872,	            -60.70672),
                new LatLng(-31.643145,	            -60.707113),
                new LatLng(-31.644246,	            -60.707397),
                new LatLng(-31.645327,	            -60.70773),
                new LatLng(-31.646512,	            -60.708077),
                new LatLng(-31.647043,	            -60.708228),
                new LatLng(-31.647675,	            -60.708362),
                new LatLng(-31.648259,	            -60.708547),
                new LatLng(-31.648858,	            -60.708688),
                new LatLng(-31.649473,	            -60.708879),
                new LatLng(-31.649917,	            -60.709007),
                new LatLng(-31.649634,	            -60.710125),
                new LatLng(-31.649543,	            -60.710577),
                new LatLng(-31.649413,	            -60.711418),
                new LatLng(-31.649236,	            -60.712642),
                new LatLng(-31.649075,	            -60.7135),
                new LatLng(-31.648725,	            -60.715325),
                new LatLng(-31.64845,	            -60.716659),
                new LatLng(-31.648004,	            -60.718979)
        );

        final List<LatLng> linea13_facu = Arrays.asList(
                new LatLng(-31.648002,	-60.718979),
                new LatLng(-31.647831,	-60.719964),
                new LatLng(-31.647685,	-60.720677),
                new LatLng(-31.64743,	-60.721942),
                new LatLng(-31.647168,	-60.723273),
                new LatLng(-31.646786,	-60.72512),
                new LatLng(-31.646378,	-60.727058),
                new LatLng(-31.646332,	-60.727322),
                new LatLng(-31.644217,	-60.726634),
                new LatLng(-31.64546,	-60.72165),
                new LatLng(-31.646243,	-60.71858),
                new LatLng(-31.644086,	-60.71794),
                new LatLng(-31.643025,	-60.717657),
                new LatLng(-31.642293,	-60.717436),
                new LatLng(-31.641181,	-60.717103),
                new LatLng(-31.640102,	-60.716787),
                new LatLng(-31.638791,	-60.7164),
                new LatLng(-31.637466,	-60.716033),
                new LatLng(-31.636349,	-60.715684),
                new LatLng(-31.635334,	-60.71541),
                new LatLng(-31.635543,	-60.7144),
                new LatLng(-31.635848,	-60.713041),
                new LatLng(-31.636695,	-60.709221),
                new LatLng(-31.637588,	-60.705182),
                new LatLng(-31.638026,	-60.703227),
                new LatLng(-31.638332,	-60.701825),
                new LatLng(-31.638411,	-60.701518),
                new LatLng(-31.638548,	-60.700897),
                new LatLng(-31.638882,	-60.699361),
                new LatLng(-31.638451,	-60.699224),
                new LatLng(-31.638662,	-60.698198),
                new LatLng(-31.639131,	-60.696059),
                new LatLng(-31.639745,	-60.693112),
                new LatLng(-31.640284,	-60.690721),
                new LatLng(-31.640993,	-60.687539),
                new LatLng(-31.641254,	-60.686269),
                new LatLng(-31.639421,	-60.685726),
                new LatLng(-31.639607,	-60.684854),
                new LatLng(-31.639782,	-60.68456),
                new LatLng(-31.640208,	-60.68399),
                new LatLng(-31.640422,	-60.683433),
                new LatLng(-31.640686,	-60.682268),
                new LatLng(-31.641064,	-60.680084),
                new LatLng(-31.641295,	-60.678795),
                new LatLng(-31.641586,	-60.677731),
                new LatLng(-31.641894,	-60.677154),
                new LatLng(-31.642,	-60.677348),
                new LatLng(-31.641706,	-60.677824),
                new LatLng(-31.641633,	-60.678188),
                new LatLng(-31.641464,	-60.679326),
                new LatLng(-31.640973,	-60.679528),
                new LatLng(-31.640742,	-60.679422),
                new LatLng(-31.640787,	-60.679088),
                new LatLng(-31.640927,	-60.678852),
                new LatLng(-31.640895,	-60.678613),
                new LatLng(-31.641062,	-60.678403),
                new LatLng(-31.641062,	-60.678202),
                new LatLng(-31.640947,	-60.677993),
                new LatLng(-31.640829,	-60.677827),
                new LatLng(-31.640957,	-60.676767),
                new LatLng(-31.641065,	-60.675956),
                new LatLng(-31.641057,	-60.675102),
                new LatLng(-31.640982,	-60.673013),
                new LatLng(-31.640904,	-60.671899),
                new LatLng(-31.640381,	-60.671913),
                new LatLng(-31.640296,	-60.672128),
                new LatLng(-31.640396,	-60.672377),
                new LatLng(-31.640394,	-60.67295),
                new LatLng(-31.640361,	-60.673088),
                new LatLng(-31.640456,	-60.673201),
                new LatLng(-31.640628,	-60.673262),
                new LatLng(-31.640952,	-60.673178),
                new LatLng(-31.641082,	-60.674734),
                new LatLng(-31.641034,	-60.676021),
                new LatLng(-31.640849,	-60.677735),
                new LatLng(-31.640908,	-60.677905),
                new LatLng(-31.641005,	-60.678079),
                new LatLng(-31.641068,	-60.678298),
                new LatLng(-31.640966,	-60.67853),
                new LatLng(-31.640875,	-60.678705),
                new LatLng(-31.640908,	-60.678926),
                new LatLng(-31.640746,	-60.679154),
                new LatLng(-31.640717,	-60.679309),
                new LatLng(-31.640875,	-60.679501),
                new LatLng(-31.641044,	-60.679525),
                new LatLng(-31.641307,	-60.679414),
                new LatLng(-31.641563,	-60.67929),
                new LatLng(-31.64192,	-60.679187),
                new LatLng(-31.643665,	-60.679102),
                new LatLng(-31.644078,	-60.679161),
                new LatLng(-31.644566,	-60.679354),
                new LatLng(-31.645069,	-60.679773),
                new LatLng(-31.645331,	-60.680189),
                new LatLng(-31.646072,	-60.68184),
                new LatLng(-31.647312,	-60.684921),
                new LatLng(-31.64855,	-60.687934),
                new LatLng(-31.649446,	-60.688636),
                new LatLng(-31.65001,	-60.689228),
                new LatLng(-31.650308,	-60.689858),
                new LatLng(-31.650683,	-60.691543),
                new LatLng(-31.650943,	-60.69196),
                new LatLng(-31.651593,	-60.69256),
                new LatLng(-31.654385,	-60.695172),
                new LatLng(-31.65631,	-60.696971),
                new LatLng(-31.657283,	-60.697762),
                new LatLng(-31.658397,	-60.698194),
                new LatLng(-31.65998,	-60.698921),
                new LatLng(-31.659967,	-60.699943),
                new LatLng(-31.660245,	-60.700181),
                new LatLng(-31.660158,	-60.700585),
                new LatLng(-31.660996,	-60.70079),
                new LatLng(-31.661532,	-60.700861),
                new LatLng(-31.661835,	-60.700865),
                new LatLng(-31.662412,	-60.701005),
                new LatLng(-31.662693,	-60.70106),
                new LatLng(-31.663342,	-60.701085),
                new LatLng(-31.664449,	-60.700998),
                new LatLng(-31.665525,	-60.700836),
                new LatLng(-31.666713,	-60.700466),
                new LatLng(-31.667399,	-60.700225),
                new LatLng(-31.667832,	-60.699954),
                new LatLng(-31.668264,	-60.699699),
                new LatLng(-31.668835,	-60.699351),
                new LatLng(-31.669266,	-60.699089),
                new LatLng(-31.669918,	-60.698665),
                new LatLng(-31.670415,	-60.698268),
                new LatLng(-31.671391,	-60.697466),
                new LatLng(-31.672234,	-60.696737),
                new LatLng(-31.672655,	-60.696225),
                new LatLng(-31.672772,	-60.696082),
                new LatLng(-31.673334,	-60.695666),
                new LatLng(-31.673714,	-60.695396),
                new LatLng(-31.674285,	-60.694921),
                new LatLng(-31.674955,	-60.694344),
                new LatLng(-31.675562,	-60.69381),
                new LatLng(-31.676217,	-60.693233),
                new LatLng(-31.676919,	-60.692609)
        );


        //OnItemSelectedListener para el spinner_colectivo, depende que
        // selecciono, habilito o deshabilito los otros spinners
        spinner_colectivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spinner_colectivo.getSelectedItem().toString()) {
                    case " --- ":
                        break;

                    case "Linea 13":
                        limpiarMapa();
                        miMapa.addPolyline(new PolylineOptions() //final Polyline linea13 = 
                                .clickable(false)
                                .color(Color.BLUE)
                                .width(8f)
                                .addAll(linea13_centro)
                                .addAll(linea13_facu)
                                );
                        miMapa.addMarker(new MarkerOptions() //final Marker mLinea13 =
                                .position(new LatLng(-31.640371, -60.672550))
                                .title("Ciudad Universitaria")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                        );
                        miMapa.addMarker(new MarkerOptions() //final Marker pLinea13a =
                                .position(new LatLng(-31.676985, -60.692558))
                                .title("Parada")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))//parada
                        );
                        break;

                    case "Linea 13 a CU":
                        limpiarMapa();
                        miMapa.addPolyline(new PolylineOptions() //final Polyline linea13_afacu =
                                .clickable(false)
                                .color(Color.GREEN)
                                .width(8f)
                                .addAll(linea13_facu)
                                );
                        miMapa.addMarker(new MarkerOptions() //final Marker mLinea13_facu =
                                .position(new LatLng(-31.640371, -60.672550))
                                .title("Ciudad Universitaria")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                        );
                        miMapa.addMarker(new MarkerOptions() //final Marker pLinea13b =
                                .position(new LatLng(-31.676985, -60.692558))
                                .title("Parada")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))//parada
                        );
                        break;

                    case "Linea 13 a centro":
                        limpiarMapa();
                        miMapa.addPolyline(new PolylineOptions() //final Polyline linea13_acentro =
                                .clickable(false)
                                .color(Color.RED)
                                .width(8f)
                                .addAll(linea13_centro)
                                );
                        miMapa.addMarker(new MarkerOptions() //final Marker mLinea13_centro =
                                .position(new LatLng(-31.640371, -60.672550))
                                .title("Ciudad Universitaria")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                        );
                        miMapa.addMarker(new MarkerOptions() //final Marker pLinea13c =
                                .position(new LatLng(-31.676985, -60.692558))
                                .title("Parada")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))//parada
                        );
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        miMapa.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-31.640578, -60.672906)));

        //Hago mi propia InfoWindow, para poder mostrar una imagen del nodo cuando hago click en el y ver el lugar que está señalado
        miMapa.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getActivity().getLayoutInflater().inflate(R.layout.custom_infowindow, null);
                ImageView imagen = (ImageView) v.findViewById(R.id.imageView);
                TextView titulo = (TextView) v.findViewById(R.id.titulo);

                //Busco en el mapa por ese punto, si tiene imagen la agrego
                if (hashMapImagenes.containsKey(marker.getPosition())) {
                    imagen.setImageResource(hashMapImagenes.get(marker.getPosition()));
                }

                titulo.setText(marker.getTitle());
                return v;
            }
        });

        mostrarPosicion();
        //Muevo la camara hasta mi posicion y agrego un marcador allí
/*        LatLng position = new LatLng(this.lat, this.lon);
        miMapa.moveCamera(CameraUpdateFactory.newLatLng(position));
        miMapa.moveCamera(CameraUpdateFactory.zoomTo(18));
        miPosicion = new MarkerOptions().position(new LatLng(this.lat, this.lon)).title("Usted está aquí");
        miPosicionMarcador = miMapa.addMarker(miPosicion);*/

        //Agrego los marcadores adicionales (Edificios, baños, bares,etc), si los hay
        for (int i = 0; i < misMarcadores.size(); i++) {
            String texto;
            if (pisoActual == 0) {
                texto = "Planta Baja";
            } else {
                texto = "Piso " + pisoActual;
            }
            if (misMarcadores.elementAt(i).getTitle().contains(texto)) {
                miMapa.addMarker(misMarcadores.elementAt(i));
            }
        }

        //Agrego polilinea si la hay
        if (misPolilineas.size() != 0) {
            miMapa.addPolyline(misPolilineas.elementAt(pisoActual));
            miMapa.addMarker(marcadoresPiso.elementAt(2 * pisoActual));
            miMapa.addMarker(marcadoresPiso.elementAt(2 * pisoActual + 1));
        }

        //Agrego los overlays
        if (misOverlays.size() != 0) {
            for (int i = 0; i < misOverlays.elementAt(pisoActual).size(); i++) {
                miMapa.addGroundOverlay(misOverlays.elementAt(pisoActual).elementAt(i));
            }
            //miMapa.moveCamera(CameraUpdateFactory.newLatLngBounds(misOverlays.elementAt(pisoActual).elementAt(0).getBounds(),0));
            miMapa.moveCamera(CameraUpdateFactory.zoomTo(18));
        }
    }

    //Setters, getters y demas utilidades
    public void setLat(double l) {
        this.lat = l;
    }

    public void setLon(double l) {
        this.lon = l;
    }

    public double getLat() {
        return this.lat;
    }

    public double getLon() {
        return this.lon;
    }

    public int getCantPisos() {
        return cantPisos;
    }

    public void setPisoActual(int p) {
        this.pisoActual = p;
    }

    public int getPisoActual() {
        return pisoActual;
    }

    public boolean modoPolilinea() {
        return !misPolilineas.isEmpty();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        /*synchronized (this) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ORIENTATION:
                    float degree = Math.round(sensorEvent.values[0]);
                    //Si el angulo de rotación con respecto a la rotación de la muestra anterior es mayor a 20
                    //roto la camara, sino no porque sino baila mucho
                    if (Math.abs(degree - angle) > 30) {
                        angle = degree;
                        CameraPosition oldPos = miMapa.getCameraPosition();
                        CameraPosition pos = CameraPosition.builder(oldPos).bearing(degree).build();
                        miMapa.moveCamera(CameraUpdateFactory.newCameraPosition(pos));
                    }
            }
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //Limpio el mapa de polilineas, marcadores, etc
    public void limpiarMapa() {
        miPosicionMarcador.remove();
        misPolilineas.clear();
        marcadoresPiso.clear();
        //misOverlays.clear();
        miMapa.clear();
        if (bandera_pos) miPosicionMarcador = miMapa.addMarker(miPosicion);
        setPisoActual(0);
    }

    //Actualizo mi posición si me moví. Quito mi marcador y lo pongo en donde corresponde
    @TargetApi(Build.VERSION_CODES.M)
    void actualizaPosicion() {

        if (bandera_pos) {
            LatLng position = new LatLng(this.lat, this.lon);
            miMapa.moveCamera(CameraUpdateFactory.newLatLng(position));
            miPosicion.position(position);
            miPosicionMarcador.remove();
            miPosicionMarcador = miMapa.addMarker(miPosicion);
        }

        if (!misPolilineas.isEmpty() && pisoActual + 1 <= misPolilineas.size()) {
            cambiarPolilinea(pisoActual);
        } else if (pisoActual + 1 > misPolilineas.size() && !misPolilineas.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Su objetivo está en un piso inferior", Toast.LENGTH_LONG).show();
        }
        if (!misMarcadores.isEmpty() && pisoActual + 1 <= misMarcadores.size()) {
            cambiarNodos(pisoActual);
        } else if (pisoActual + 1 > misMarcadores.size() && !misMarcadores.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Su objetivo está en un piso inferior", Toast.LENGTH_LONG).show();

        }
    }

    //Obtengo mi latitud y longitud en un objeto LatLng
    public LatLng getPosicion() {
        return new LatLng(this.lat, this.lon);
    }

    //Recibo un vector de puntos y creo un polilinea con ellos
    public void dibujaCamino(Vector<Punto> path) {
        misPolilineas.clear();
        misMarcadores.clear();
        marcadoresPiso.clear();
        miMapa.clear();
        cantPisos = 0;
        Vector<String> edificios = new Vector<>();

        //Veo cuantos pisos hay
        for (int i = 0; i < path.size(); i++) {
            if (path.elementAt(i).getPiso() > cantPisos) {
                cantPisos = path.elementAt(i).getPiso();
            }
        }

        //Creo las polilineas y overlays que voy a usar
        cantPisos = cantPisos + 1;
        for (int i = 0; i < cantPisos; i++) {
            PolylineOptions p = new PolylineOptions().width(5).color(Color.RED);
            Vector<GroundOverlayOptions> g = new Vector<>();
            misPolilineas.add(p);
            misOverlays.add(g);
        }

        //Agrego puntos a las polilineas segun piso e identifico por que edificios y pisos pasa mi polilinea
        for (int i = 0; i < path.size(); i++) {
            misPolilineas.elementAt(path.elementAt(i).getPiso()).add(new LatLng(path.elementAt(i).getLatitud(), path.elementAt(i).getLongitud()));
            for (int j = 0; j < cantidad_edificios; j++) {
                //Veo si ese marcador está dentro de algun edificio con el mapa y la funcion dentroDeLimites
                //Tratar de optimizar esto
                if (hashMapBounds.containsKey("ed" + j + "_" + path.elementAt(i).getPiso())) {
                    if (dentroDeLimites(new LatLng(path.elementAt(i).getLatitud(), path.elementAt(i).getLongitud()), hashMapBounds.get("ed" + j + "_" + path.elementAt(i).getPiso()))) {
                        if (!edificios.contains("ed" + j + "_" + path.elementAt(i).getPiso())) {
                            edificios.add("ed" + j + "_" + path.elementAt(i).getPiso());
                        }
                    }
                }
            }
        }

        //Agrego los overlays a mi vector
        for (int i = 0; i < edificios.size(); i++) {
            if (hashMapID.containsKey(edificios.elementAt(i))) {
                misOverlays.elementAt(Integer.parseInt(edificios.elementAt(i).substring(edificios.elementAt(i).indexOf("_") + 1)))
                        .add(new GroundOverlayOptions()
                                .positionFromBounds(hashMapBounds.get(edificios.elementAt(i)))
                                .image(BitmapDescriptorFactory.fromResource(hashMapID.get(edificios.elementAt(i)))));
            }
        }

        //Busco cuales marcadores por piso voy a tener
        marcadoresPiso.add(new MarkerOptions()
                .position(new LatLng(path.elementAt(0).getLatitud(), path.elementAt(0).getLongitud()))
                .title(path.elementAt(0).getNombre())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        for (int i = 1; i < path.size() - 1; i++) {
            if (path.elementAt(i).getPiso() != path.elementAt(i + 1).getPiso()) {
                marcadoresPiso.add(new MarkerOptions()
                        .position(new LatLng(path.elementAt(i).getLatitud(), path.elementAt(i).getLongitud()))
                        .title(path.elementAt(i).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                marcadoresPiso.add(new MarkerOptions()
                        .position(new LatLng(path.elementAt(i + 1).getLatitud(), path.elementAt(i + 1).getLongitud()))
                        .title(path.elementAt(i + 1).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        }

        marcadoresPiso.add(new MarkerOptions()
                .position(new LatLng(path.elementAt(path.size() - 1).getLatitud(), path.elementAt(path.size() - 1).getLongitud()))
                .title(path.elementAt(path.size() - 1).getNombre())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));



        //Cargo las imagenes en el map
        cargarMapaImagnes(path);
    }

    //Recibo un conjunto de puntos y creo marcadores para todos ellos
    public void mostrarNodos(Vector<Punto> nodos) {
        misMarcadores.clear();
        misPolilineas.clear();
        marcadoresPiso.clear();
        Vector<String> edificios = new Vector<>();
        cantPisos = 0;
        for (int i = 0; i < nodos.size(); i++) {
            String texto;
            if (nodos.elementAt(i).getPiso() == 0) {
                texto = "Planta Baja";
            } else {
                texto = "Piso " + nodos.elementAt(i).getPiso();
            }
            //Cuento la cantidad de pisos en donde encontre lo que busco
            if (nodos.elementAt(i).getPiso() > cantPisos) {
                cantPisos = nodos.elementAt(i).getPiso();
            }

            //Agrego los marcadores
            misMarcadores.add(new MarkerOptions().position(new LatLng(nodos.elementAt(i).getLatitud(), nodos.elementAt(i).getLongitud())).title(nodos.elementAt(i).getNombre() + " - " + texto).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            for (int j = 0; j < cantidad_edificios; j++) {
                //Veo si ese marcador está dentro de algun edificio
                if (hashMapBounds.containsKey("ed" + j + "_" + nodos.elementAt(i).getPiso())) {
                    if (dentroDeLimites(new LatLng(nodos.elementAt(i).getLatitud(), nodos.elementAt(i).getLongitud()), hashMapBounds.get("ed" + j + "_" + nodos.elementAt(i).getPiso()))) {
                        if (!edificios.contains("ed" + j + "_" + nodos.elementAt(i).getPiso())) {
                            edificios.add("ed" + j + "_" + nodos.elementAt(i).getPiso());
                        }
                    }
                }
            }
        }

        //Creo las polilineas y overlays que voy a usar
        cantPisos = cantPisos + 1;
        for (int i = 0; i < cantPisos; i++) {
            Vector<GroundOverlayOptions> g = new Vector<>();
            misOverlays.add(g);
        }

        //Agrego los overlays a mi vector
        for (int i = 0; i < edificios.size(); i++) {
            if (hashMapID.containsKey(edificios.elementAt(i))) {
                misOverlays.elementAt(Integer.parseInt(edificios.elementAt(i).substring(edificios.elementAt(i).indexOf("_") + 1)))
                        .add(new GroundOverlayOptions()
                                .positionFromBounds(hashMapBounds.get(edificios.elementAt(i)))
                                .image(BitmapDescriptorFactory.fromResource(hashMapID.get(edificios.elementAt(i)))));
            }
        }

        //Cargo imagenes en el map
        cargarMapaImagnes(nodos);
    }

    public void cambiarPolilinea(int piso) {
        miMapa.clear();

        if (bandera_pos) miPosicionMarcador = miMapa.addMarker(miPosicion);


        miMapa.addPolyline(misPolilineas.elementAt(piso));
        miMapa.addMarker(marcadoresPiso.elementAt(2 * piso));
        miMapa.addMarker(marcadoresPiso.elementAt(2 * piso + 1));

        //Agrego los overlays
        if (misOverlays.size() > piso) {
            for (int i = 0; i < misOverlays.elementAt(piso).size(); i++) {
                miMapa.addGroundOverlay(misOverlays.elementAt(piso).elementAt(i));
            }
        }
    }

    //Funcion para actualizar los nodos según el piso que se quiera ver
    public void cambiarNodos(int piso) {
        miMapa.clear();

        if (bandera_pos) miPosicionMarcador = miMapa.addMarker(miPosicion);
        for (int i = 0; i < misMarcadores.size(); i++) {
            if (piso == 0) {
                if (misMarcadores.elementAt(i).getTitle().contains("Planta Baja")) {
                    miMapa.addMarker(misMarcadores.elementAt(i));
                }
            } else {
                if (misMarcadores.elementAt(i).getTitle().contains("Piso " + piso)) {
                    miMapa.addMarker(misMarcadores.elementAt(i));
                }
            }
        }

        //Agrego los overlays
        if (misOverlays.size() > piso) {
            for (int i = 0; i < misOverlays.elementAt(piso).size(); i++) {
                miMapa.addGroundOverlay(misOverlays.elementAt(piso).elementAt(i));
            }
        }
    }

    //Funcion para saber si un punto está dentro de ciertos limites
    public boolean dentroDeLimites(LatLng posicion, LatLngBounds bounds) {
        LatLng limiteInfIzquierdo = bounds.southwest;
        LatLng limiteSupDerecho = bounds.northeast;
        boolean esta = true;
        if (posicion.latitude > limiteSupDerecho.latitude || posicion.latitude < limiteInfIzquierdo.latitude || posicion.longitude > limiteSupDerecho.longitude || posicion.longitude < limiteInfIzquierdo.longitude) {
            esta = false;
        }
        return esta;
    }

    //hashMap de Posición de nodo - Imagen del nodod
    public void cargarMapaImagnes(Vector<Punto> puntos) {
        hashMapImagenes.clear();
        for (int i = 0; i < puntos.size(); i++) {
            if (puntos.elementAt(i).getImagen() != null) {
                hashMapImagenes.put(new LatLng(puntos.elementAt(i).getLatitud(), puntos.elementAt(i).getLongitud()), puntos.elementAt(i).getImagen());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //funcion para armar la ruta
    private String armaUrl(boolean bandera,double lat,double lon)
    {
        //LA URL PARA CONSULTAR AL WEBSERVICE SE USAR COMO SE VE:
        //ORIGIN
        //DESTINATION
        //KEY
        //SON PARAMETROS
        //SI SE PONE EN EL BROWSER TE MUESTRA LA RESPUESTA JSON O LOS ERRORES
        String key = "&key=" + getKey();

        String modo;
        //segun la bandera booleana
        //si bandera = true, muestro recorrido caminando
        //si bandera = false, muestro recorrido manejando
        if(bandera)
        {
            modo = "&mode=walking";
        }
        else
        {
            modo = "&mode=driving";
        }

        //convierto variables de double a string
        Double dlatitud = lat;
        Double dlongitud = lon;
        String latitud = dlatitud.toString();
        String longitud = dlongitud.toString();

        //String url = "http://cuandopasa.smartmovepro.net/Paginas/Paginas/Recorridos.aspx/RecuperarRecorrido";

        //armo la url
        return  "https://maps.googleapis.com/maps/api/directions/json?origin="+latitud+","+longitud+"&destination=-31.640771, -60.671849"+ key + modo;
        //return "https://maps.googleapis.com/maps/api/directions/json?origin=-31.6177085,-60.6841818&destination=-31.640771, -60.671849"+ key + modo;
    }

    private void mostrarDistanciaTiempo(JSONObject jso)
    {
        try
        {
            //obtengo routes
            JSONArray jRoutes = jso.getJSONArray("routes");

            JSONObject routes = jRoutes.getJSONObject(0);

            JSONArray legs = routes.getJSONArray("legs");

            JSONObject steps = legs.getJSONObject(0);

            JSONObject distance = steps.getJSONObject("distance");

            JSONObject tiempo = steps.getJSONObject("duration");

            String aux =  tiempo.get("text").toString();

            //conversion de hours a hora y mins a min
            String h = aux.replace("hour","hora");
            String m = h.replace("mins","min.");

            //armo el menssje
            String mensaje = "La distancia es " + distance.get("text") + "\n" + "Tiempo estimado :" + m ;
            //debug
            Toast.makeText(getActivity().getApplicationContext(),mensaje, Toast.LENGTH_LONG).show();
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void mostrarCaminoCaminando()
    {
        String url = armaUrl(true,this.lat,this.lon);
        Log.i("url: ",""+url);
        if(!bandera_pos) mostrarPosicion();

        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    public void mostrarCaminoManejando()
    {
        String url = armaUrl(false,this.lat,this.lon);
        Log.i("url: ",""+url);
        if(!bandera_pos) mostrarPosicion();

        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);

    }

    /////////////////////////////////////////////////////////**********************************

    private class DownloadTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(getActivity());
            p.setMessage("Trazando ruta, espere...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("ERROR",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            p.hide();
            if (result == "") {
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexión", Toast.LENGTH_LONG).show();
            } else {
            ParserTask parserTask = new ParserTask();
            Toast.makeText(getActivity().getApplicationContext(),result, Toast.LENGTH_LONG).show();
            parserTask.execute(result); }
        }
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                ////
                mostrarDistanciaTiempo(jObject); ////
                ////

                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.rgb(0,0,255));
            }
            if(lineOptions!=null) {
                miMapa.addPolyline(lineOptions);
            }
        }
    }


    public class DirectionsJSONParser {

        public List<List<HashMap<String,String>>> parse(JSONObject jObject){

            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;

            try {

                jRoutes = jObject.getJSONArray("routes");

                for(int i=0;i<jRoutes.length();i++){
                    jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<HashMap<String, String>>();

                    for(int j=0;j<jLegs.length();j++){
                        jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                        for(int k=0;k<jSteps.length();k++){
                            String polyline = "";
                            polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            for(int l=0;l<list.size();l++){
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                                hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
            }

            return routes;
        }

        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }

            return poly;
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creamos una conexion http
            urlConnection = (HttpURLConnection) url.openConnection();

            // Conectamos
            urlConnection.connect();

            // Leemos desde URL
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    //metodo para setear la key
    public void setKey(String key)
    {
        this.key = key;
    }

    //metodo para devolver la key
    public String getKey() { return this.key; }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    public void mostrarSpinnerColes() {
        spinner_colectivo.setVisibility(View.VISIBLE);
        botonColectivo.setVisibility(GONE);
        botonClear.setVisibility(View.GONE);
        botonCerrar.setVisibility(View.VISIBLE);
    }

    public void cerrarColes() {
        spinner_colectivo.setSelection(0);
        spinner_colectivo.setVisibility(View.GONE);
        botonCerrar.setVisibility(View.GONE);
        botonClear.setVisibility(View.VISIBLE);
        botonColectivo.setVisibility(View.VISIBLE);
        limpiarMapa();
    }

    public void mostrarPosicion(){
        pos_on.setVisibility(View.GONE);
        pos_off.setVisibility(View.VISIBLE);
        LatLng position = new LatLng(this.lat, this.lon);
        miMapa.moveCamera(CameraUpdateFactory.newLatLng(position));
        miMapa.moveCamera(CameraUpdateFactory.zoomTo(18));
        miPosicion = new MarkerOptions().position(new LatLng(this.lat, this.lon)).title("Usted está aquí");
        miPosicionMarcador = miMapa.addMarker(miPosicion);
        //Toast.makeText(getActivity().getApplicationContext(), "Posición Activada", Toast.LENGTH_SHORT).show();
        bandera_pos = true;
    }

    public void ocultarPosicion(){
        pos_off.setVisibility(View.GONE);
        pos_on.setVisibility(View.VISIBLE);
        miPosicionMarcador.remove();
        //Toast.makeText(getActivity().getApplicationContext(), "Posición desactivada", Toast.LENGTH_SHORT).show();
        bandera_pos = false;
    }

    public void clearMapa(){
        Toast.makeText(getActivity().getApplicationContext(), "Mapa reiniciado.", Toast.LENGTH_LONG).show();
        limpiarMapa();
    }




}
