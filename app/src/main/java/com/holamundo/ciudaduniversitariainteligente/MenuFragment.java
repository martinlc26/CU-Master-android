package com.holamundo.ciudaduniversitariainteligente;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    //textview para mostrar las partes del menu
    private TextView t_entrada;
    private TextView t_principal;
    private TextView t_postre;
    private Button botonActualizar;
    private ImageButton botonRecargar;

    //variables para guardar las partes del menu
    private String entrada_n;
    private String principal_n;
    private String postre_n;
    private String entrada_c;
    private String principal_c;
    private String postre_c;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ProgressDialog p;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        //seteo las variables
        this.entrada_c = this.principal_c = this.postre_c = this.entrada_c = this.principal_c
                = this.postre_c = "(Sin información)";

        this.entrada_n = this.principal_n = this.postre_n = this.entrada_n = this.principal_n
                = this.postre_n = "(Sin información)";

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


        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        this.t_entrada = (TextView) view.findViewById(R.id.entrada); //instancie al texview del xml
        this.t_principal = (TextView) view.findViewById(R.id.principal); //instancie al texview del xml
        this.t_postre = (TextView) view.findViewById(R.id.postre); //instancie al texview del xml
        this.botonActualizar = (Button) view.findViewById(R.id.botonVerMenu);
        this.botonRecargar = (ImageButton) view.findViewById(R.id.botonRecargar);

        //webservice publico fake , ingresar a la url para ver la estructura json de los datos
        String url = "https://my-json-server.typicode.com/cristian16b/DispMoviles2019/db";
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
        return view;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

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
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
            //debug: para ver si obtuve datos de la query
            //Toast.makeText(getActivity().getApplicationContext(),result, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ParserTask extends AsyncTask<String, Void, Void >{

        @Override
        protected Void doInBackground(String... jsonData) {

            try
            {
                JSONObject jso = new JSONObject(jsonData[0]);

                //accedo a menu regular
                JSONArray jregular = jso.getJSONArray("regular");
                //accedo al primer elemento
                JSONObject item = jregular.getJSONObject(0);

                entrada_n = item.getString("entrada");
                principal_n = item.getString("plato");
                postre_n = item.getString("postre");

                //obtengo el menu para celiacos y lo guardo
                JSONArray jceliaco = jso.getJSONArray("celiaco");
                //accedo al primer item
                item = jceliaco.getJSONObject(0);

                entrada_c = item.getString("entrada");
                principal_c = item.getString("plato");
                postre_c = item.getString("postre");
            }
            catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexión", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            t_entrada.setText(entrada_n);
            t_principal.setText(principal_n);
            t_postre.setText(postre_n);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /*
    COMENTO ESTE METODO CREADO AUTOMATICAMENTE CUANO SE CREA EL FRAGMNENT
    COMENTADO, FUNCIONA Y MUESTRA EL FRAGMENT_MENU.XML
    SINO EXPLOTA EN TIEMPO DE EJEC
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    */


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void mostrarMenues(String botonTexto)
    {
        if(botonTexto.equals("Menú Celiaco"))
        {
            //Muestro el menu para celiacos
            t_entrada.setText(entrada_c);
            t_principal.setText(principal_c);
            t_postre.setText(postre_c);

            //seteo texto del boton
            botonActualizar.setText("Volver");
        }
        else if(botonTexto.equals("Volver"))
        {
            //Muestro el menu para celiacos
            t_entrada.setText(entrada_n);
            t_principal.setText(principal_n);
            t_postre.setText(postre_n);

            //seteo texto del boton
            botonActualizar.setText("Menú Celiaco");
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(), "Error: no hay menues disponibles.", Toast.LENGTH_LONG).show();
        }
    }

    public void recargarMenu()
    {
        String url = "https://my-json-server.typicode.com/cristian16b/DispMoviles2019/db";
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }
}