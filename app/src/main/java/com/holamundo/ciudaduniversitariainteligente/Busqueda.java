package com.holamundo.ciudaduniversitariainteligente;

import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;

/**
 * Created by Lautaro on 30/11/2016.
 */
public class Busqueda extends Fragment {


    private Spinner spinnerBusqueda = null;
    private String Edificio = "*";
    private String Nombre = "";
    private Button botonBusqueda = null;
    private MainActivity oMainActivity = null;
    private BaseDatos CUdb = null;


    private TextView tv2 = null;
    private TextView tv2b = null;
    private TextView tv3 = null;
    private Spinner spinnerBusqueda2 = null;
    private Spinner spinnerBusqueda3 = null;

    /*
    spinnerBusqueda es el spinner principal donde están las posibles busqueda: Edificios, aulas, baños, etc 
    spinnerBusqueda2 tendrá una lista de los edificios (si seleccione Edificios o aulas)
    spinnerBusqueda3 tendrá una lista de las aulas para el edificio del spinnerBusqueda2 (si seleccione aulas en spinner2)

    En los atributos Edificio y Nombre, guardo los valores que voy a utilizar para hacer las busquedas y armar el camino
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.busqueda, container, false);

        spinnerBusqueda = (Spinner) rootView.findViewById(R.id.busquedaSpinner);
        botonBusqueda = (Button) rootView.findViewById(R.id.boton_busqueda);
        oMainActivity = (MainActivity) getActivity();

        //Por el momento no muestro estos spinners, los habilito si fuera necesario
        tv2 = (TextView) rootView.findViewById(R.id.tv2);
        tv2b = (TextView) rootView.findViewById(R.id.tv2b);
        spinnerBusqueda2 = (Spinner) rootView.findViewById(R.id.busquedaSpinner2);
        tv2.setVisibility(View.GONE);
        tv2b.setVisibility(View.GONE);
        spinnerBusqueda2.setVisibility(View.GONE);

        tv3 = (TextView) rootView.findViewById(R.id.tv3);
        spinnerBusqueda3 = (Spinner) rootView.findViewById(R.id.busquedaSpinner3);
        tv3.setVisibility(View.GONE);
        spinnerBusqueda3.setVisibility(View.GONE);

        //Instancio la base de datos
        CUdb = new BaseDatos(getActivity(),"DBBusquedas", null, 1);

        //Adaptar para las posibles opciones de busqueda, uso uno propio porque queda mejor esteticamente
        String[] itemsSB1 = {" --- ", "Edificios", "Aulas", "Baños", "Bares", "Predio"};
        ArrayAdapter<String> arraySB1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, itemsSB1);
        spinnerBusqueda.setAdapter(arraySB1);

        //OnItemSelectedListener para el spinnerBusqueda, depende que
        // selecciono, habilito o deshabilito los otros spinners
        spinnerBusqueda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spinnerBusqueda.getSelectedItem().toString()) {
                    case " --- ":
                        tv2.setVisibility(View.GONE);
                        tv2b.setVisibility(View.GONE);
                        spinnerBusqueda2.setVisibility(View.GONE);
                        tv3.setVisibility(View.GONE);
                        spinnerBusqueda3.setVisibility(View.GONE);

                        Edificio = "*";
                        Nombre = "";

                        break;
                    case "Edificios":
                        Nombre = "Entrada";
                        //Lista de edificios que hay cargados. Debería hacer una función en ArmaCamino que me devuelva esta lista
                        //para no tener que actualiarlo si agrego un nodo en un edificio nuevo
                        String[] itemsSB2 = {"Todos", "FICH", "FCBC", "FCM", "FADU", "FHUC", "ISM", "Cubo"};
                        ArrayAdapter<String> arraySB2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, itemsSB2);
                        spinnerBusqueda2.setAdapter(arraySB2);

                        tv2.setVisibility(View.VISIBLE);
                        tv2b.setVisibility(View.GONE);
                        spinnerBusqueda2.setVisibility(View.VISIBLE);
                        tv3.setVisibility(View.GONE);
                        spinnerBusqueda3.setVisibility(View.GONE);
                        Edificio = "*";
                        break;
                    case "Aulas":

                        String[] itemsSB2a = {" --- ", "FICH", "FCBC", "FADU", "FHUC", "Cubo"};
                        ArrayAdapter<String> arraySB2a = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, itemsSB2a);
                        spinnerBusqueda2.setAdapter(arraySB2a);

                        tv2.setVisibility(View.VISIBLE);
                        tv2b.setVisibility(View.GONE);
                        spinnerBusqueda2.setVisibility(View.VISIBLE);

                        break;
                    case "Baños":
                        Nombre = "Baño";
                        Edificio = "*";

                        tv2.setVisibility(View.GONE);
                        tv2b.setVisibility(View.GONE);
                        spinnerBusqueda2.setVisibility(View.GONE);
                        tv3.setVisibility(View.GONE);
                        spinnerBusqueda3.setVisibility(View.GONE);

                        break;
                    case "Bares":
                        Nombre = "Cantina";
                        Edificio = "*";

                        tv2.setVisibility(View.GONE);
                        tv2b.setVisibility(View.GONE);
                        spinnerBusqueda2.setVisibility(View.GONE);
                        tv3.setVisibility(View.GONE);
                        spinnerBusqueda3.setVisibility(View.GONE);

                        break;
                    case "Predio":
                        Edificio = "Predio"; //SI USO * ENTONCES SOLO ME MARCA LA LOCACION, SIN TRAZAR EL CAMINO
                        String[] itemsSB2b = {"Entrada Predio Este", "Portico", "Pergolas", "Piletas", "Gimnasio", "Cancha Cubierta", "Canchas F5", "Cancha F11", "Cancha F7 Oeste", "Cancha F7 Este", "Cancha Basket/Voley", "Cancha Beach Voley", "Comedor", "Vestuarios", "Hotel (entrada predio)", "Hotel (entrada calle)"};
                        ArrayAdapter<String> arraySB2b = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, itemsSB2b);
                        spinnerBusqueda2.setAdapter(arraySB2b);

                        tv2b.setVisibility(View.VISIBLE);
                        tv2.setVisibility(View.GONE);
                        spinnerBusqueda2.setVisibility(View.VISIBLE);
                        tv3.setVisibility(View.GONE);
                        spinnerBusqueda3.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //OnItemSelectedListener para el spinnerBusqueda2
        spinnerBusqueda2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerBusqueda.getSelectedItem().toString().equals("Aulas")) {
                    switch (spinnerBusqueda2.getSelectedItem().toString()) {
                        case " --- ":
                            break;
                        default:
                            Edificio = spinnerBusqueda2.getSelectedItem().toString();
                            //Busco las aulas para el edifcio seleccionado, voy de Busqueda a MainActivity y de MainActivity a ArmaCamino
                            //y le paso el mensaje
                            Vector<Punto> puntoAulas = oMainActivity.verAulasPorEdificio(Edificio);
                            String [] sAulas = new String[puntoAulas.size()];
                            for(int j=0;j<sAulas.length;j++){
                                sAulas[j] = puntoAulas.elementAt(j).getNombre();
                            }
                            ArrayAdapter<String> arraySB3 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, sAulas);
                            spinnerBusqueda3.setAdapter(arraySB3);
                            tv3.setVisibility(View.VISIBLE);
                            spinnerBusqueda3.setVisibility(View.VISIBLE);
                            break;
                    }

                } else if (spinnerBusqueda.getSelectedItem().toString().equals("Predio")) {
                            Nombre = spinnerBusqueda2.getSelectedItem().toString();
                } else {
                    switch (spinnerBusqueda2.getSelectedItem().toString()) {
                        case "Todos":
                            break;
                        default:
                            Edificio = spinnerBusqueda2.getSelectedItem().toString();
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //OnItemSelectedListener para el spinnerBusqueda3
        spinnerBusqueda3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spinnerBusqueda3.getSelectedItem().toString()) {
                    case " --- ":
                        break;
                    default:
                        Nombre = spinnerBusqueda3.getSelectedItem().toString();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Listener para el boton de busqueda, ejecuto la busqueda y la guardo en la base de datos
        botonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!spinnerBusqueda.getSelectedItem().toString().equals(" --- ")) {
                    SQLiteDatabase db1 = CUdb.getReadableDatabase();
                    boolean repetido = false;
                    Cursor c = db1.rawQuery("SELECT nombre, edificio FROM Busquedas",null);
                    c.moveToFirst();
                    //Veo si ya hice esta busqueda, si está en la base de datos, no la vuelvo a guardar
                    //Tendría que buscar si no hay algo mas optimo para hacer esto
                    if(c.getCount() > 0) {
                        do {
                            if (c.getString(0).equals(Nombre) && c.getString(1).equals(Edificio)) {
                                repetido = true;
                            }
                        } while (c.moveToNext());
                    }
                    db1.close();

                    if(!repetido) {
                        String DB_insert = "INSERT INTO Busquedas (nombre, edificio) VALUES ('" + Nombre + "', '" + Edificio + "')";
                        SQLiteDatabase db = CUdb.getWritableDatabase();
                        db.execSQL(DB_insert);
                        db.close();
                    }
                    oMainActivity.mostrarBusqueda(Edificio, Nombre);
                }
            }
        });

        return rootView;
    }

}
