package com.holamundo.ciudaduniversitariainteligente;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Lautaro on 30/11/2016.
 */
public class BaseDatos extends SQLiteOpenHelper {

    private static final String INSERT_PUNTO_TEMPLATE = new String("INSERT INTO Punto (id, latitud, longitud, edificio, piso, nombre, imagen) VALUES " +
            "('%s', '%s', '%s', '%s', '%s', '%s', '%s')");

    private static final String INSERT_CONEXION_TEMPLATE = new String("INSERT INTO Conexiones (idDesde, idHasta) VALUES (%s, %s)");

    public BaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cargaDB(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Busquedas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Punto");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Conexiones");

        //Se crea la nueva versión de las tablas
        cargaDB(sqLiteDatabase);
    }


    public void cargaDB(SQLiteDatabase sqLiteDatabase){
        //Creo las tablas
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Punto");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Conexiones");
        sqLiteDatabase.execSQL("CREATE TABLE Busquedas (nombre TEXT, edificio TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Punto (id TEXT, latitud TEXT, longitud TEXT, edificio TEXT, piso TEXT, nombre TEXT, imagen TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Conexiones (idDesde TEXT, idHasta TEXT)");
        //Ciudad Universitaria
        //Entrada
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 0, "-31.640935", "-60.671913", "Ciudad Universitaria", 0, "Ciudad Universitaria", -1));
        //Facultades
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 1, "-31.639950", "-60.671896", "FICH", 0, "Entrada FICH", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 2, "-31.639953", "-60.672090", "FCBC", 0, "Aula Magna", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 3, "-31.639962", "-60.672151", "FICH", 0, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 4, "-31.639965", "-60.672261", "FICH", 0, "Fotocopiadora", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 5, "-31.639965", "-60.672306", "FICH", 0, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 6, "-31.639963", "-60.6724285", "FICH", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 7, "-31.639898", "-60.672430", "FICH", 0, "Cantina", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 8, "-31.639799", "-60.672426", "FICH", 0, "Aula 4", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 9, "-31.639799", "-60.672361", "FICH", 0, "Aula Magna", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 10, "-31.639798", "-60.672181", "FICH", 0, "Aula 5 - Aula 8", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 11, "-31.639801", "-60.672471", "FICH", 0, "Aula 3", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 12, "-31.639799", "-60.672556", "FICH", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 13, "-31.639799", "-60.672610", "FICH", 0, "Aula 1 - Aula 2", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 14, "-31.639969", "-60.672544", "FICH", 0, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 15, "-31.639937", "-60.671198", "FICH", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 16, "-31.639651", "-60.671193", "FICH", 0, "Aula 7", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 17, "-31.639710", "-60.671893", "FICH", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 18, "-31.639713", "-60.671578", "FICH", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 19, "-31.639811", "-60.671578", "FICH", 0, "Aula 6", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 20, "-31.639957", "-60.672262", "FICH", 1, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 21, "-31.639957", "-60.672151", "FICH", 1, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 22, "-31.639957", "-60.672071", "FICH", 1, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 23, "-31.639900", "-60.672071", "FICH", 1, "Relaciones Institucionales", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 24, "-31.639873", "-60.672071", "FICH", 1, "Decanato", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 25, "-31.639853", "-60.672151", "FICH", 1, "Alumnado", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 26, "-31.639799", "-60.672151", "FICH", 1, "Mesa de Entradas", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 27, "-31.639773", "-60.672151", "FICH", 1, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 28, "-31.639773", "-60.672198", "FICH", 1, "Laboratorio Electronica", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 29, "-31.639773", "-60.672296", "FICH", 1, "Aula Laboratorio 1 - 2", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 30, "-31.639773", "-60.672550", "FCBC", 1, "Aula Vigil", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 31, "-31.639847", "-60.672550", "FICH", 1, "Laboratorio 3 - 4", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 32, "-31.639956", "-60.672550", "FICH", 1, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 33, "-31.639956", "-60.672302", "FICH", 1, "Bedelia", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 34, "-31.639954", "-60.672269", "FICH", 2, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 35, "-31.639954", "-60.672109", "FICH", 2, "Laboratorio de Quimica", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 36, "-31.639954", "-60.672088", "FICH", 2, "Secretaria Extensión", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 37, "-31.639928", "-60.672088", "FICH", 2, "Aula Dibujo", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 38, "-31.639928", "-60.672072", "FICH", 2, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 39, "-31.639958", "-60.672285", "FICH", 3, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 40, "-31.639958", "-60.672214", "FCBC", 3, "Lab. Bacteriología - Inmunología", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 41, "-31.639958", "-60.672146", "FICH", 3, "Aula 9", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 42, "-31.639958", "-60.672085", "FICH", 3, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 43, "-31.639925", "-60.672085", "FICH", 3, "Aula 10", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 44, "-31.639869", "-60.672085", "FICH", 3, "Cátedra Matematica", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 45, "-31.639969", "-60.672706", "FCBC", 0, "Fotocopiadora", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 46, "-31.639969", "-60.672792", "FCBC", 0, "Lab Quimica Analítica I-II", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 47, "-31.639969", "-60.672823", "FCBC", 0, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 48, "-31.639869", "-60.672825", "FCBC", 0, "Lab Quimica Biologica", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 49, "-31.639969", "-60.672966", "FCBC", 0, "Bedelia", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 50, "-31.639969", "-60.673062", "FCBC", 0, "Mesa de entradas", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 51, "-31.639773", "-60.672587", "FCBC", 1, "Aula 1.1 - 1.2 - 1.3", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 52, "-31.639956", "-60.672605", "FCBC", 1, "Cátedra Matemática Gral", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 53, "-31.639956", "-60.672699", "FCBC", 1, "Lab. Química Orgánica I - II", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 54, "-31.639956", "-60.672795", "FCBC", 1, "Cátedra Química Orgánica", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 55, "-31.639956", "-60.672838", "FCBC", 1, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 56, "-31.639956", "-60.672884", "FCBC", 1, "Sala Informatica", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 57, "-31.639956", "-60.672951", "FCBC", 1, "Alumnado", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 58, "-31.639843", "-60.672838", "FCBC", 1, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 59, "-31.639954", "-60.672429", "FCBC", 2, "Cátedra Quimica Analitica", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 60, "-31.639863", "-60.672429", "FCBC", 2, "Aula 2.11", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 61, "-31.639777", "-60.672427", "FCBC", 2, "Aula 2.5", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 62, "-31.639777", "-60.672326", "FCBC", 2, "Aula 2.6 - 2.7", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 63, "-31.639777", "-60.672232", "FCBC", 2, "Aula 2.8 - 2.9", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 64, "-31.639777", "-60.672154", "FCBC", 2, "Aula 2.10", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 65, "-31.639954", "-60.672553", "FCBC", 2, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 66, "-31.639954", "-60.672554", "FCBC", 2, "Lab Quimica General e Inorganica", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 67, "-31.639840", "-60.672553", "FCBC", 2, "Aula 2.12", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 68, "-31.639777", "-60.672553", "FCBC", 2, "Aula 2.4", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 69, "-31.639777", "-60.672575", "FCBC", 2, "Aula 2.2 - 2.3", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 70, "-31.639777", "-60.672614", "FCBC", 2, "Aula 2.1", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 71, "-31.639954", "-60.672735", "FCBC", 2, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 72, "-31.639954", "-60.672838", "FCBC", 2, "Cátedra Qca Gral", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 73, "-31.639833", "-60.672838", "FCBC", 2, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 74, "-31.639774", "-60.672838", "FCBC", 2, "Aula 2.13", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 75, "-31.639958", "-60.672361", "FCBC", 3, "Lab.Bacteriología e Inmunología", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 76, "-31.639958", "-60.672432", "FCBC", 3, "Biblioteca", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 77, "-31.639958", "-60.672493", "FCBC", 3, "Lab Morfología Normal", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 78, "-31.639958", "-60.672555", "FCBC", 3, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 79, "-31.639958", "-60.672729", "FCBC", 3, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 80, "-31.639958", "-60.672839", "FCBC", 3, "Lab. Microbiología General y Micología", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 81, "-31.639846", "-60.672839", "FCBC", 3, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 82, "-31.639958", "-60.672926", "FCBC", 3, "Decanato", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 83, "-31.639645", "-60.671578", "FICH", 0, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 84, "-31.639646", "-60.671577", "FICH", 1, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 85, "-31.639720", "-60.671577", "FICH", 1, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 86, "-31.639720", "-60.671650", "FICH", 1, "Aula Laboratorio 5", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 87, "-31.639969", "-60.673159", "FCBC", 0, "Entrada FCBC", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 88, "-31.639969", "-60.673288", "Ciudad Universitaria", 0, "", -1));
        //sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 89, "-31.640159", "-60.673288", "Ciudad Universitaria", 0, "Aula FADU", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 89, "-31.640159", "-60.673288", "FADU", 0, "Entrada FADU", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 90, "-31.639964", "-60.673159", "Ciudad Universitaria", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 91, "-31.639656", "-60.673288", "Ciudad Universitaria", 0, "Aula 91", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 92, "-31.639649", "-60.672556", "Ciudad Universitaria", 0, "Aula 92", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 93, "-31.639649", "-60.671893", "Ciudad Universitaria", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 94, "-31.639567", "-60.671893", "Ciudad Universitaria", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 95, "-31.639567", "-60.670975", "FCM", 0, "Cantina", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 96, "-31.639567", "-60.670850", "FCM", 0, "Entrada FCM", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 97, "-31.639937", "-60.670975", "Ciudad Universitaria", 0, "Aula 97", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 98, "-31.640159", "-60.673362", "FADU", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 99, "-31.640210", "-60.673362", "FADU", 0, "Bedelia", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 100, "-31.640128", "-60.673362", "FADU", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 101, "-31.640128", "-60.673395", "FADU", 0, "Cantina", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 102, "-31.640128", "-60.673532", "FADU", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 103, "-31.640071", "-60.673532", "FADU", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 104, "-31.640071", "-60.673592", "FADU", 0, "Libreria", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 105, "-31.640071", "-60.673658", "FADU", 0, "Aula Salón de Actos", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 106, "-31.640128", "-60.673572", "FADU", 0, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 107, "-31.640194", "-60.673572", "FADU", 0, "Aula Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 108, "-31.640119", "-60.673602", "FADU", 1, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 109, "-31.640181", "-60.673602", "FADU", 1, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 110, "-31.640067", "-60.673602", "FADU", 1, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 111, "-31.640044", "-60.673602", "FADU", 1, "Aula Área Administrativa", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 112, "-31.640044", "-60.673646", "FADU", 1, "Área Gestión", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 113, "-31.640067", "-60.673474", "FADU", 1, "Asuntos Estudiantiles", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 114, "-31.640067", "-60.673350", "FADU", 1, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 115, "-31.640041", "-60.673350", "FADU", 1, "Alumnado", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 116, "-31.640116", "-60.673608", "FADU", 2, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 117, "-31.640178", "-60.673608", "FADU", 2, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 118, "-31.640116", "-60.673567", "FADU", 2, "Sala Informatica 1", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 119, "-31.640070", "-60.673608", "FADU", 2, "Aula Especiales", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 120, "-31.640070", "-60.673802", "FADU", 2, "Secretaria Posgrado", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 121, "-31.640070", "-60.673421", "FADU", 2, "Sala Informatica 2", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 122, "-31.640070", "-60.673336", "FADU", 2, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 123, "-31.640003", "-60.673336", "FADU", 2, "Biblioteca", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 124, "-31.640117", "-60.673607", "FADU", 3, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 125, "-31.640175", "-60.673607", "FADU", 3, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 126, "-31.640258", "-60.673607", "FADU", 3, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 127, "-31.640258", "-60.673747", "FADU", 3, "Aula Taller 4", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 128, "-31.640117", "-60.673577", "FADU", 3, "Aula Fotocopiadora", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 129, "-31.640073", "-60.673607", "FADU", 3, "Aula Taller 3", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 130, "-31.640073", "-60.673638", "FADU", 3, "Aula Taller 1", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 131, "-31.640073", "-60.673553", "FADU", 3, "Aula Taller 2", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 132, "-31.640113", "-60.673581", "FADU", 4, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 133, "-31.640174", "-60.673581", "FADU", 4, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 134, "-31.640252", "-60.673581", "FADU", 4, "Taller 8", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 135, "-31.640252", "-60.673533", "FADU", 4, "Taller 9", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 136, "-31.640252", "-60.673744", "FADU", 4, "Taller 7", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 137, "-31.640069", "-60.673581", "FADU", 4, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 138, "-31.640069", "-60.673606", "FADU", 4, "Taller 5", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 139, "-31.640069", "-60.673531", "FADU", 4, "Taller 6", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 140, "-31.640210", "-60.673491", "FHUC", 0, "Fotocopiadora", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 141, "-31.640194", "-60.673491", "FHUC", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 142, "-31.640272", "-60.673572", "FHUC", 0, "Aula 3", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 143, "-31.640272", "-60.673496", "FHUC", 0, "Aula 2", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 144, "-31.640272", "-60.673399", "FHUC", 0, "Aula 1", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 145, "-31.640272", "-60.673692", "FHUC", 0, "Aula 4", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 146, "-31.640272", "-60.673726", "FHUC", 0, "Aula 5 - Aula 8", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 147, "-31.640272", "-60.673818", "FHUC", 0, "Aula 6 - Aula 7", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 148, "-31.640260", "-60.673602", "FHUC", 1, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 149, "-31.640260", "-60.673821", "FHUC", 1, "Alumnado", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 150, "-31.640307", "-60.673821", "FHUC", 1, "Mesa Entrada", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 151, "-31.640307", "-60.673769", "FHUC", 1, "Secretaria Administrativa", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 152, "-31.640307", "-60.673559", "FHUC", 1, "Decanato", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 153, "-31.640260", "-60.673559", "FHUC", 1, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 154, "-31.640225", "-60.673608", "FHUC", 2, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 155, "-31.640261", "-60.673608", "FHUC", 2, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 156, "-31.640261", "-60.673658", "FHUC", 2, "Aula 12", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 157, "-31.640261", "-60.673722", "FHUC", 2, "Aula 11", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 158, "-31.640261", "-60.673745", "FHUC", 2, "Aula 9", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 159, "-31.640261", "-60.673834", "FHUC", 2, "Aula 10", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 160, "-31.640225", "-60.673427", "FHUC", 2, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 161, "-31.640272", "-60.673427", "FHUC", 2, "Aula 14", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 162, "-31.640258", "-60.673537", "FHUC", 3, "Lab. 1", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 163, "-31.640258", "-60.673425", "FHUC", 3, "Lab. 2", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 164, "-31.640258", "-60.673400", "FHUC", 3, "Lab. 3", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 165, "-31.640258", "-60.673363", "FHUC", 3, "Lab. Fisico Quimica", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 166, "-31.639964", "-60.674034", "Cubo", 0, "Aulario Común", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 167, "-31.639964", "-60.674178", "Cubo", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 168, "-31.640038", "-60.674178", "Cubo", 0, "Bedelia", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 169, "-31.640038", "-60.674210", "Cubo", 0, "Liberia", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 170, "-31.640038", "-60.674118", "Cubo", 0, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 171, "-31.640014", "-60.674118", "Cubo", 0, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 172, "-31.640006", "-60.674119", "Cubo", 1, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 173, "-31.640039", "-60.674119", "Cubo", 1, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 174, "-31.640039", "-60.674212", "Cubo", 1, "Aula 3", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 175, "-31.640039", "-60.674253", "Cubo", 1, "Aula 2", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 176, "-31.640039", "-60.674292", "Cubo", 1, "Aula 1", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 177, "-31.640006", "-60.674123", "Cubo", 2, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 178, "-31.640061", "-60.674077", "Cubo", 2, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 179, "-31.639938", "-60.674123", "Cubo", 2, "Taller A", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 180, "-31.640061", "-60.674123", "Cubo", 2, "Taller B", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 181, "-31.640065", "-60.674119", "Cubo", 1, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 182, "-31.640065", "-60.674073", "Cubo", 1, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 183, "-31.640064", "-60.674118", "Cubo", 0, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 184, "-31.640064", "-60.674073", "Cubo", 0, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 185, "-31.640002", "-60.674120", "Cubo", 3, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 186, "-31.639940", "-60.674120", "Cubo", 3, "Taller C", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 187, "-31.640064", "-60.674120", "Cubo", 3, "Aula 4", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 188, "-31.640064", "-60.674073", "Cubo", 3, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 189, "-31.640009", "-60.674121", "Cubo", 4, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 190, "-31.640009", "-60.674162", "Cubo", 4, "Aula 5", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 191, "-31.640009", "-60.674216", "Cubo", 4, "Aula 6", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 192, "-31.640009", "-60.674244", "Cubo", 4, "Aula 7", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 193, "-31.640009", "-60.674308", "Cubo", 4, "Aula 8", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 194, "-31.640066", "-60.674121", "Cubo", 4, "", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 195, "-31.640066", "-60.674074", "Cubo", 4, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 196, "-31.640002", "-60.674120", "Cubo", 5, "Escalera", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 197, "-31.639940", "-60.674120", "Cubo", 5, "Aula Taller 9", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 198, "-31.640064", "-60.674120", "Cubo", 5, "Aula 10", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 199, "-31.640064", "-60.674073", "Cubo", 5, "Baños", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 200, "-31.640064", "-60.671913", "Ciudad Universitaria", 0, "", -1));
        //Predio
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 201, "-31.639911", "-60.674692", "Predio", 0, "Entrada Predio Este", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 202, "-31.639125", "-60.677307", "Predio", 0, "Portico", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 203, "-31.638307", "-60.674444", "Predio", 0, "Pergolas", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 204, "-31.638280", "-60.674994", "Predio", 0, "Piletas", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 205, "-31.639351", "-60.677397", "Predio", 0, "Gimnasio", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 206, "-31.639629", "-60.677628", "Predio", 0, "Cancha Cubierta", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 207, "-31.638685", "-60.675849", "Predio", 0, "Canchas F5", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 208, "-31.637769", "-60.675627", "Predio", 0, "Cancha F11", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 209, "-31.639636", "-60.677209", "Predio", 0, "Cancha F7 Oeste", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 210, "-31.639257", "-60.675182", "Predio", 0, "Cancha F7 Este", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 211, "-31.638736", "-60.676404", "Predio", 0, "Cancha Basket/Voley", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 212, "-31.639477", "-60.674367", "Predio", 0, "Cancha Beach Voley", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 213, "-31.638704", "-60.674856", "Predio", 0, "Comedor", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 214, "-31.638665", "-60.675357", "Predio", 0, "Vestuarios", -1));
        //nodos mudos
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 215, "-31.640013", "-60.674795", "Predio", 0, "", -1));//test1
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 216, "-31.639748", "-60.675492", "Predio", 0, "", -1));//test2
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 217, "-31.639347", "-60.676587", "Predio", 0, "", -1));//test3
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 218, "-31.639161", "-60.677192", "Predio", 0, "", -1));//test4
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 219, "-31.639828", "-60.676848", "Predio", 0, "", -1));//test5
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 220, "-31.639378", "-60.674554", "Predio", 0, "", -1));//test6
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 221, "-31.638716", "-60.676040", "Predio", 0, "", -1));//test7
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 222, "-31.638522", "-60.675976", "Predio", 0, "", -1));//test8
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 223, "-31.638849", "-60.675494", "Predio", 0, "", -1));//test9
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 224, "-31.638528", "-60.675614", "Predio", 0, "", -1));//test10
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 225, "-31.638735", "-60.675209", "Predio", 0, "", -1));//test11
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 226, "-31.638569", "-60.675107", "Predio", 0, "", -1));//test12
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 227, "-31.638464", "-60.674933", "Predio", 0, "", -1));//test13
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 228, "-31.638634", "-60.674522", "Predio", 0, "", -1));//test14
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 229, "-31.638370", "-60.674572", "Predio", 0, "", -1));//test15
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 230, "-31.638196", "-60.674859", "Predio", 0, "", -1));//test16
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 231, "-31.637888", "-60.675329", "Predio", 0, "", -1));//test17
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 232, "-31.639745", "-60.673202", "Predio", 0, "", -1));//test18 (fuera predio)
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 233, "-31.640161", "-60.673218", "Predio", 0, "", -1));//test19 (fuera predio)
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 234, "-31.640113", "-60.671926", "Predio", 0, "", -1));//test20 (fuera predio)
        //hotel
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 235, "-31.640293", "-60.676516", "Predio", 0, "Hotel (entrada predio)", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 236, "-31.640576", "-60.676608", "Predio", 0, "Hotel (entrada calle)", -1));
        //nodos mudos exteriores para hotel
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 237, "-31.640920", "-60.676696", "Predio", 0, "", -1));//test21 (hotel)
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 238, "-31.641000", "-60.674858", "Predio", 0, "", -1));//test22 (hotel)
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 239, "-31.640965", "-60.673280", "Predio", 0, "", -1));//test23 (hotel)
        //Entrada ISM -31.640440 -60.673902
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 240, "-31.640420", "-60.673908", "ISM", 0, "Entrada ISM", -1));
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 241, "-31.640272", "-60.673908", "ISM", 0, "", -1)); //adentro ISM
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 242, "-31.640420", "-60.673283", "Ciudad Universitaria", 0, "", -1));
        //Entrada Cubo
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 243, "-31.639964", "-60.674034", "Cubo", 0, "Entrada Cubo", -1));
        //nodo que conecta el cubo con el predio
        sqLiteDatabase.execSQL(String.format(INSERT_PUNTO_TEMPLATE, 244, "-31.639901", "-60.674016", "Ciudad Universitaria", 0, "", -1));


        //CONEXIONES DE NODOS
        //conexion a la entrada de ISM
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 240, 241));//
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 241, 240));//
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 240, 242));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 242, 240));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 242, 89));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 89, 242));
        //conexion ISM con el interior del edificio
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 241, 147));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 147, 241));

        //conexion entrada cubo con lo demas (excepcion del predio)
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 243, 166));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 166, 243));
        //conexion entrada cubo con predio
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 243, 244));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 244, 243));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 244, 232));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 232, 243));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 243, 88));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 88, 243));
        //conexiones en predio
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 215, 201));//test1
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 215, 216));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 215, 220));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 216, 215));//test2
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 216, 210));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 216, 217));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 217, 216));//test3
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 217, 218));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 217, 219));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 217, 221));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 217, 235));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 218, 217));//test4
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 218, 202));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 218, 205));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 218, 209));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 219, 217));//test5
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 219, 209));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 219, 235));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 220, 201));//test6
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 220, 212));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 220, 210));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 220, 215));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 220, 228));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 220, 223));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 221, 217));//test7
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 221, 211));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 221, 222));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 222, 221));//test8
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 222, 207));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 222, 223));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 222, 224));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 223, 222));//test9
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 223, 220));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 223, 207));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 223, 210));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 223, 214));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 224, 222));//test10
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 224, 223));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 224, 225));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 224, 231));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 224, 214));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 224, 207));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 225, 224));//test11
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 225, 214));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 225, 213));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 225, 228));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 225, 226));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 226, 225));//test12
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 226, 227));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 227, 226));//test13
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 227, 228));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 227, 213));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 227, 204));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 227, 229));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 227, 230));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 228, 227));//test14
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 228, 229));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 228, 220));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 228, 213));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 228, 214));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 229, 228));//test15
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 229, 230));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 229, 227));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 229, 213));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 229, 203));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 230, 231));//test16
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 230, 229));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 230, 227));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 231, 230));//test17
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 231, 224));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 231, 208));

        //fuera del predio
        //conexiones predio-unl
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 232, 233));//test18
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 232, 201));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 233, 234));//test19
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 233, 232));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 233, 239));

        //conexiones FADU, FCBC con predio a traves del nodo 233
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 233, 87));//87: entrada FBCB
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 87, 233));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 233, 89));//89: entrada FADU
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 89, 233));


        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 234, 0));//nodo para el predio (+ cerca de la entrada a la CU)
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 234, 1));//(entrada fich)
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 234, 233));

        //hotel, salida exterior
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 237, 236));//test21
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 237, 238));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 238, 237));//test22
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 238, 239));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 239, 238));//test22
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 239, 233));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 239, 0));

        //conexiones entre las locaciones del predio
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 207, 222));//canchas f5
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 207, 223));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 207, 224));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 201, 0));//entrada este
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 201, 215));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 201, 220));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 202, 218));//portico

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 205, 206));//gimnasio
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 205, 209));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 205, 218));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 206, 209));//cancha cubierta
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 206, 205));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 209, 219));//cancha f7 oeste
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 209, 205));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 209, 206));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 210, 216));//cancha f7 este
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 210, 220));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 210, 223));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 212, 220));//cancha beach voley
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 212, 223));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 212, 228));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 211, 221));//cancha Basket/Voley

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 207, 222));//canchas f5
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 207, 223));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 207, 224));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 214, 223));//vestuarios
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 214, 224));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 214, 225));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 213, 225));//comedor
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 213, 227));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 213, 228));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 204, 227));//piletas

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 203, 229));//pergolas

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 208, 231));//cancha f11

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 235, 217));//hotel (entrada predio)
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 235, 219));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 236, 237)); //hotel (salida calle)
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 0, 234)); //entrada facu - entrada este predio
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 0, 239));
        //
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 0, 200));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 1, 2));//entrada fich
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 1, 15));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 1, 17));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 1, 200));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 1, 234));//nodo pal predio

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 2, 1));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 2, 3));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 3, 2));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 3, 4));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 3, 20));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 4, 3));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 4, 5));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 5, 4));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 5, 6));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 6, 5));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 6, 7));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 6, 14));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 7, 6));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 7, 8));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 8, 7));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 8, 9));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 8, 11));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 9, 8));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 9, 10));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 10, 9));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 11, 8));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 11, 12));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 12, 11));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 12, 13));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 12, 92));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 13, 12));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 14, 6));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 14, 32));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 14, 45));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 15, 1));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 15, 16));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 15, 97));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 16, 15));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 17, 1));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 17, 18));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 17, 93));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 18, 17));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 18, 19));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 18, 83));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 19, 18));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 20,3));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 20,21));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 20,33));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 20,34));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 21,20));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 21,22));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 21,25));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 22,21));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 22,23));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 23,22));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 23,24));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 24,23));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 25,21));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 25,26));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 26,25));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 26,27));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 27,26));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 27,28));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 28,27));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 28,29));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 29,28));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 29,30));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 30,29));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 30,31));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 30,51));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 31,30));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 31,32));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 32,14));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 32,31));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 32,33));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 32,52));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 32,65));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 33,32));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 33,20));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 34,20));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 34,35));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 34,39));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 34,59));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 35,34));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 35,36));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 36,35));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 36,37));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 37,36));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 37,38));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 38,37));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 34,39));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 39,34));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 39,40));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 39,75));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 40,39));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 40,41));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 41,40));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 41,42));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 42,41));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 42,43));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 43,42));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 43,44));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 44,43));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 45,14));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 45,46));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 46,45));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 46,47));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 47,46));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 47,48));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 47,49));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 48,47));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 49,47));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 49,50));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 50, 49));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 50, 87));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 51,30));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 52,32));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 52,53));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 53,52));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 53,54));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 54,53));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 54,55));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 55,54));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 55,56));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 55,58));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 55,71));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 56,55));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 56,57));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 57,56));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 58,55));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 59,34));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 59,60));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 59,65));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 60,59));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 60,61));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 61,60));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 61,62));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 62,61));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 62,63));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 63,62));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 63,64));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 64,63));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 65,32));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 65,59));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 65,66));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 65,78));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 66,65));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 66,67));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 66,71));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 67,66));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 67,68));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 68,67));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 68,69));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 69,68));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 69,70));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 70,69));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 71,55));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 71,66));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 71,72));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 71,79));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 72,71));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 72,73));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 73,72));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 73,74));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 74,73));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 75,39));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 75,76));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 76,75));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 76,77));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 77,76));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 77,78));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 78,65));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 78,77));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 78,79));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 79,71));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 79,78));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 79,80));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 80,79));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 80,81));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 80,82));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 81,80));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 82,80));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 83, 18));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 83, 84));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 84, 83));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 84, 85));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 85, 84));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 85, 86));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 86, 85));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 87, 50));//entrada fcbc
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 87, 88));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 87, 90));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 87, 232));//nodo pal predio
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 87, 233));//nodo pal predio

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 88, 87));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 88, 89));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 89, 88));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 89, 98));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 89, 200));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 90, 87));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 90, 91));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 90, 166));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 91, 90));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 91, 92));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 92, 91));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 92, 12));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 92, 93));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 93, 92));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 93, 17));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 93, 94));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 94, 93));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 94, 95));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 95, 94)); //nodo que conecta FCM
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 95, 96));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 95, 97));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 95, 234));//nodo pal predio
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 95, 232));//nodo pal hotel (entrada calle)

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 96, 95)); //entrada FCM

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 97, 95));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 97, 15));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 98, 89));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 98, 99));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 98, 100));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 99, 98));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 99, 140));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 100, 98));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 100, 101));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 101, 100));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 101, 102));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 102, 101));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 102, 103));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 102, 106));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 103, 102));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 103, 104));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 104, 103));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 104, 105));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 105, 104));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 106, 102));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 106, 107));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 106, 108));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 107, 106));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 107, 141));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 107, 142));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 108, 106));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 108, 109));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 108, 110));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 108, 116));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 109, 108));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 109, 148));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 110, 108));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 110, 111));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 110, 113));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 111, 110));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 111, 112));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 112, 111));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 113, 110));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 113, 114));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 114, 113));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 114, 115));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 115, 114));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 116, 108));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 116, 117));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 116, 118));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 116, 119));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 116, 124));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 117, 116));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 117, 154));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 118, 116));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 119, 116));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 119, 120));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 119, 121));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 120, 119));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 121, 119));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 121, 122));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 122, 121));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 122, 123));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 123, 122));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 124, 116));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 124, 125));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 124, 128));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 124, 129));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 124, 132));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 125, 124));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 125, 126));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 126, 125));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 126, 127));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 126, 162));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 127, 126));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 128, 124));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 129, 124));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 129, 130));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 129, 131));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 130, 129));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 131, 129));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 132, 124));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 132, 133));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 132, 137));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 133, 132));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 133, 134));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 134, 133));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 134, 135));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 134, 136));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 135, 134));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 136, 134));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 137, 132));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 137, 138));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 137, 139));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 138, 137));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 139, 137));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 140, 99));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 140, 141));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 141, 140));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 141, 107));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 142, 107));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 142, 143));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 142, 145));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 143, 142));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 143, 144));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 144, 143));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 145, 142));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 145, 146));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 146, 145));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 146, 147));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 147, 146));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 148, 109));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 148, 149));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 148, 153));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 149, 148));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 149, 150));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 150, 149));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 150, 151));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 151, 150));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 151, 152));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 152, 151));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 152, 153));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 153, 152));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 153, 148));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 154, 117));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 154, 155));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 154, 160));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 155, 154));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 155, 156));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 156, 155));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 156, 157));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 157, 156));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 157, 158));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 158, 157));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 158, 159));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 159, 158));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 160, 154));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 160, 161));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 161, 160));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 162, 126));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 162, 163));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 163, 162));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 163, 164));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 164, 163));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 164, 165));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 165, 164));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 166, 90));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 166, 167));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 167, 166));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 167, 168));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 168, 167));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 168, 169));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 168, 170));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 169, 168));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 170, 168));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 170, 171));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 170, 183));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 171, 170));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 171, 172));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 172, 171));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 172, 173));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 172, 177));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 173, 172));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 173, 174));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 173, 181));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 174, 173));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 174, 175));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 175, 174));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 175, 176));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 176, 175));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 177, 172));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 177, 179));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 177, 178));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 177, 185));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 178, 180));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 179, 177));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 180, 178));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 181, 173));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 181, 182));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 182, 181));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 183, 170));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 183, 184));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 184, 183));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 185, 177));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 185, 186));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 185, 187));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 185, 189));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 186, 185));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 187, 185));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 187, 188));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 188, 187));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 189, 185));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 189, 190));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 189, 194));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 189, 196));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 190, 189));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 190, 191));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 191, 190));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 191, 192));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 192, 191));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 192, 193));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 193, 192));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 194, 189));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 194, 195));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 195, 194));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 196, 189));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 196, 197));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 196, 198));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 197, 196));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 198, 196));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 198, 199));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 199, 198));

        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 200, 0));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 200, 1));
        sqLiteDatabase.execSQL(String.format(INSERT_CONEXION_TEMPLATE, 200, 89));

    }
}
