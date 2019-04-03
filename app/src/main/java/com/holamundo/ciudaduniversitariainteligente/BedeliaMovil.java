package com.holamundo.ciudaduniversitariainteligente;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BedeliaMovil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BedeliaMovil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BedeliaMovil extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinnerFacultad = null;
    private ListView listaClases = null;
    private ArrayList<String> lista;

    private String facultad;

    ProgressDialog p;


    public BedeliaMovil() {
        // Required empty public constructor
        lista = new ArrayList<String>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BedeliaMovil.
     */
    // TODO: Rename and change types and number of parameters
    public static BedeliaMovil newInstance(String param1, String param2) {
        BedeliaMovil fragment = new BedeliaMovil();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bedelia_movil, container, false);

        spinnerFacultad = (Spinner) view.findViewById(R.id.facultadSpinner);

        //cargo los valores de spinner
        //convertir a mayusculas
        final String[] facultades = {" --- ", "FICH", "FBCB", "FADU", "FHUC", "ISM", "FCM"};
        ArrayAdapter <String>adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,facultades);
        spinnerFacultad.setAdapter(adapter);

        listaClases =(ListView) view.findViewById(android.R.id.list);

        spinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() // register the listener
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // User selected item
                //Toast.makeText(getActivity().getApplicationContext(), facultades[position] + " selected!", Toast.LENGTH_SHORT).show();
                mostrarHorariosCursado(facultades[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void mostrarHorariosCursado(String facultad) { //final String facultad

        //webservice publico fake , ingresar a la url para ver la estructura json de los datos
        String url = "https://my-json-server.typicode.com/cristian16b/DispMoviles2019/db";
        DownloadTask downloadTask = new DownloadTask(facultad);
        downloadTask.execute(url);
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        String facultad;

        DownloadTask(String f){
            this.facultad = f;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(getActivity());
            p.setMessage("Descargando información, espere...");
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
                Log.d("Error: no URL available",e.toString());
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
                //Toast.makeText(getActivity().getApplicationContext(), facultad, Toast.LENGTH_LONG).show();
                ParserTask parser = new ParserTask();
                parser.execute(result, facultad);

            }
        }
    }


    private class ParserTask extends AsyncTask<String, Void, Integer > {

        @Override
        protected Integer doInBackground(String... strings) { //Void doInBackgroud
            Integer bandera = 0;
            //valores de bandera:
            //0 = la opcion seleccionada es " -- "
            //1 = el json tiene datos
            //2 = el json no tiene datos (se selecciono una facu que no tiene horarios)
            String json = strings[0];
            String fac = strings[1];
            if (!fac.equals(" --- ")) {
                try {
                    JSONObject jso = new JSONObject(json);
                    //accedo al subarray bedelia
                    JSONArray jregular = jso.getJSONArray("bedelia");
                    //accedo al primer elemento (listado de facultades)
                    JSONObject listaHorariosFacultades = jregular.getJSONObject(0);
                    //accedo al listado de la facultad
                    JSONArray listadoClases = listaHorariosFacultades.getJSONArray(fac);

                    int tamanio = listadoClases.length();
                    //inicializo la lista de mensajes a mostrar
                    String mensaje = "\n";
                    //borro la lista
                    lista.clear();

                    //listaClases.setAdapter(null);
                    for (int i = 0; i < tamanio; i++) {
                        JSONObject tmp = (JSONObject) listadoClases.get(i);
                        mensaje = " Aula: " + tmp.getString("aula") + "\n" +
                                " Inicio: " + tmp.getString("inicio") + " - Fin: " + tmp.getString("fin") + "\n" +
                                " Materia: " + tmp.getString("materia") + "\n" +
                                " Docentes: " + tmp.getString("docentes");

                        lista.add(mensaje);
                    }
                bandera = 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                    bandera = 2;
                }
            }
            return bandera;
        }

        @Override
        protected void onPostExecute(Integer result) { //Void result
            super.onPostExecute(result);
            //accedo al activity,accedo al layout,accedor a la fila del layout,agrego la lista con los string
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (getActivity().getApplicationContext(),
                            R.layout.simple_list_item_1
                            , R.id.rowTextView,
                            lista);
            if (result == 1) {//listaClases !=null
                listaClases.setAdapter(adapter);
            } else if (result == 2) {
                listaClases.setAdapter(null);
                Toast.makeText(getActivity().getApplicationContext(), "No se encontraron horarios de cursado.", Toast.LENGTH_LONG).show();
            }
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


/*        //si selecciono un option del select, averiguo las clases
        if (!facultad.equals(" --- ")) {
            //Toast.makeText(getActivity().getApplicationContext(), facultad, Toast.LENGTH_LONG).show();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jso = new JSONObject(response);

                        //accedo al subarray bedelia
                        JSONArray jregular = jso.getJSONArray("bedelia");
                        //accedo al primer elemento (listado de facultades)
                        JSONObject listaHorariosFacultades = jregular.getJSONObject(0);
                        //accedo al listado de la facultad
                        JSONArray listadoClases = listaHorariosFacultades.getJSONArray(facultad);

                        int tamanio = listadoClases.length();
                        //inicializo la lista de mensajes a mostrar
                        String mensaje = "\n";
                        //borro la lista
                        lista.clear();
                        listaClases.setAdapter(null);

                        for (int i = 0; i < tamanio; i++) {
                            JSONObject tmp = (JSONObject) listadoClases.get(i);
                            mensaje = " Aula: " + tmp.getString("aula") + "\n" +
                                    " Inicio: " + tmp.getString("inicio") + " - Fin: " + tmp.getString("fin") + "\n" +
                                    " Materia: " + tmp.getString("materia") + "\n" +
                                    " Docentes: " + tmp.getString("docentes");

                            lista.add(mensaje);
                        }
                        //accedo al activity,accedo al layout,accedor a la fila del layout,agrego la lista con los string
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getActivity().getApplicationContext(),
                                        R.layout.simple_list_item_1
                                        , R.id.rowTextView,
                                        lista);
                        //muestro
                        listaClases.setAdapter(adapter);
                    } catch (JSONException e) {
                        listaClases.setAdapter(null);
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "No se encontraron horarios de cursado.", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }*/

    //}

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
