package com.holamundo.ciudaduniversitariainteligente;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
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

import java.util.ArrayList;
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
    private Boolean bandera = false;

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

        //POLILINEA PARA RECORRIDO

/*        final Polyline linea13_afacu = miMapa.addPolyline(new PolylineOptions()
                .clickable(false)
                .color(Color.GREEN)
                .width(8f)
                .add(
                        new LatLng(-31.640935, -60.671913),
                        new LatLng(-31.641380, -60.677363),
                        new LatLng(-31.639213, -60.685940),
                        new LatLng(-31.633476, -60.713075)
                ));*/

/*        final Polyline linea13_acentro = miMapa.addPolyline(new PolylineOptions()
                .clickable(false)
                .color(Color.BLUE)
                .width(8f)
                .add(
                        new LatLng(-31.647094, -60.703093),
                        new LatLng(-31.644299, -60.688416),
                        new LatLng(-31.641760, -60.685101),
                        new LatLng(-31.640417, -60.684307),
                        new LatLng(-31.641075, -60.676357),
                        new LatLng(-31.640935, -60.671913)
                ));*/
        //linea13_acentro.setVisible(false);
        //linea13_afacu.setVisible(false);
        //
        //
        String[] itemsSC = {" --- ", "Linea 13 a CU", "Linea 13 a centro"};
        ArrayAdapter<String> arraySC = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, itemsSC);
        spinner_colectivo.setAdapter(arraySC);

        //OnItemSelectedListener para el spinner_colectivo, depende que
        // selecciono, habilito o deshabilito los otros spinners
        spinner_colectivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spinner_colectivo.getSelectedItem().toString()) {
                    case " --- ":

                        break;
                    case "Linea 13 a CU":
                        limpiarMapa();
                        final Polyline linea13_afacu = miMapa.addPolyline(new PolylineOptions()
                                .clickable(false)
                                .color(Color.GREEN)
                                .width(8f)
                                .add(
                                        new LatLng(-31.640935, -60.671913),
                                        new LatLng(-31.641380, -60.677363),
                                        new LatLng(-31.639213, -60.685940),
                                        new LatLng(-31.633476, -60.713075)
                                ));

                        break;
                    case "Linea 13 a centro":
                        limpiarMapa();
                        final Polyline linea13_acentro = miMapa.addPolyline(new PolylineOptions()
                                .clickable(false)
                                .color(Color.BLUE)
                                .width(8f)
                                .add(
                                        new LatLng(-31.647094, -60.703093),
                                        new LatLng(-31.644299, -60.688416),
                                        new LatLng(-31.641760, -60.685101),
                                        new LatLng(-31.640417, -60.684307),
                                        new LatLng(-31.641075, -60.676357),
                                        new LatLng(-31.640935, -60.671913)
                                ));

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

        //Muevo la camara hasta mi posicion y agrego un marcador allí
        LatLng position = new LatLng(this.lat, this.lon);
        miMapa.moveCamera(CameraUpdateFactory.newLatLng(position));
        miMapa.moveCamera(CameraUpdateFactory.zoomTo(18));
        miPosicion = new MarkerOptions().position(new LatLng(this.lat, this.lon)).title("Usted está aquí");
        miPosicionMarcador = miMapa.addMarker(miPosicion);

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
        miMapa.addMarker(miPosicion);
        setPisoActual(0);
    }

    //Actualizo mi posición si me moví. Quito mi marcador y lo pongo en donde corresponde
    @TargetApi(Build.VERSION_CODES.M)
    void actualizaPosicion() {
        LatLng position = new LatLng(this.lat, this.lon);
        miMapa.moveCamera(CameraUpdateFactory.newLatLng(position));
        miPosicion.position(position);
        miPosicionMarcador.remove();
        miPosicionMarcador = miMapa.addMarker(miPosicion);

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
        miMapa.addMarker(miPosicion);
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
        miMapa.addMarker(miPosicion);
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

    private void trazarRuta(JSONObject jso)
    {

        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i = 0; i < jRoutes.length(); i++) {

                jLegs = ((JSONObject) (jRoutes.get(i))).getJSONArray("legs");

                for (int j = 0; j < jLegs.length(); j++) {

                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k < jSteps.length(); k++) {


                        String polyline = "" + ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end", "" + polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        miMapa.addPolyline(new PolylineOptions().addAll(list).color(Color.RED).width(10));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void mostrarCaminoCaminando()
    {
        String url = armaUrl(true,this.lat,this.lon);

        Log.i("url: ",""+url);

        Double longi = this.lon;
        Double lati = this.lat;
        String coord = longi.toString() + "," + lati.toString();
        Toast.makeText(getActivity().getApplicationContext(),coord, Toast.LENGTH_LONG).show();


        Toast.makeText(getActivity().getApplicationContext(),url, Toast.LENGTH_LONG).show();


        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jso = new JSONObject(response);
                    Toast.makeText(getActivity().getApplicationContext(),jso.toString(), Toast.LENGTH_LONG).show();

                    //trazo
                    trazarRuta(jso);

                    //muestro
                    mostrarDistanciaTiempo(jso);

                    Log.i("jsonRuta: ",""+response);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void mostrarCaminoManejando()
    {
        String url = armaUrl(false,this.lat,this.lon);

        Log.i("url: ",""+url);

        //Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        Toast.makeText(getActivity().getApplicationContext(),url, Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jso = new JSONObject(response);
                    Toast.makeText(getActivity().getApplicationContext(),jso.toString(), Toast.LENGTH_LONG).show();

                    //trazo
                    trazarRuta(jso);

                    //muestro
                    mostrarDistanciaTiempo(jso);

                    Log.i("jsonRuta: ",""+response);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
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
        botonCerrar.setVisibility(View.VISIBLE);
    }

    public void cerrarColes() {
        spinner_colectivo.setSelection(0);
        spinner_colectivo.setVisibility(View.GONE);
        botonCerrar.setVisibility(View.GONE);
        botonColectivo.setVisibility(View.VISIBLE);
        limpiarMapa();
    }





}
