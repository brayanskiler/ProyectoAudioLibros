package com.example.Brian.ProyectoAudioLibro;


import android.annotation.SuppressLint;
import android.app.Application;

import java.util.Vector;

public class Aplicacion extends Application {
    private Vector<Libro> vectorLibros;
    private AdaptadorLibrosFiltro adaptador;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        vectorLibros = Libro.ejemploLibros();
        adaptador = new AdaptadorLibrosFiltro (this, vectorLibros);
    }
    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador;
    }
    public Vector<Libro> getVectorLibros() {
        return vectorLibros;
    }
}
