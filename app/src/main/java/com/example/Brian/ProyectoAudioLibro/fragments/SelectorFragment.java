package com.example.Brian.ProyectoAudioLibro.fragments;

/*import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
*/
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.Brian.ProyectoAudioLibro.AdaptadorLibrosFiltro;
import com.example.Brian.ProyectoAudioLibro.Aplicacion;
import com.example.Brian.ProyectoAudioLibro.Libro;
import com.example.Brian.ProyectoAudioLibro.MainActivity;
import com.example.Brian.ProyectoAudioLibro.R;

import java.util.Vector;

public class SelectorFragment extends Fragment {

    private Activity actividad;
    private RecyclerView recyclerView;
    private AdaptadorLibrosFiltro adaptador;
    private Vector<Libro> vectorLibros;

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.menu_buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                adaptador.setBusqueda(query);
                adaptador.notifyDataSetChanged();
                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ultimo) {
            ((MainActivity) actividad).irUltimoVisitado();
            return true;
        } else if (id == R.id.menu_buscar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context contexto) {
        super.onAttach(contexto);
        if (contexto instanceof Activity) {
            this.actividad = (Activity) contexto;
            Aplicacion app = (Aplicacion) actividad.getApplication();
            adaptador = app.getAdaptador();
            vectorLibros = app.getVectorLibros();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
                setHasOptionsMenu(true);
                View vista = inflador.inflate(R.layout.fragment_selector, contenedor, false);
                recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new GridLayoutManager(actividad,2));
                recyclerView.setAdapter(adaptador);
        adaptador.setOnItemLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(final View v) {
                final int id = recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(actividad);
                CharSequence[] opciones = { "Compartir", "Borrar ", "Insertar" };
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion) { case 0: //Compartir
                            Libro libro = vectorLibros.elementAt(id);
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain"); i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                            i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);
                            startActivity(Intent.createChooser(i, "Compartir"));
                            break;
                            case 1: //Borrar
                                 //vectorLibros.remove(id);
                                adaptador.borrar(id);
                                 adaptador.notifyDataSetChanged();
                                Snackbar.make(v,"¿Estás seguro?", Snackbar.LENGTH_LONG) .setAction("SI", new View.OnClickListener() {
                                    @Override public void onClick(View view) { vectorLibros.remove(id); adaptador.notifyDataSetChanged();
                                } }) .show();
                                 break;
                                 case 2: //Insertar
                                     //vectorLibros.add(vectorLibros.elementAt(id));
                                     int posicion = recyclerView.getChildLayoutPosition(v);
                                     adaptador.insertar((Libro) adaptador.getItem(posicion));
                                     adaptador.notifyDataSetChanged();
                                     Snackbar.make(v,"Libro insertado", Snackbar.LENGTH_INDEFINITE) .setAction("OK", new View.OnClickListener() {
                                         @Override public void onClick(View view) { } }) .show();
                                     break;
                        }
                    }
                });
                menu.create().show();
                return true;
            }
        });
        adaptador.setOnItemClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(actividad, "Seleccionado el elemento: " + recyclerView.getChildAdapterPosition(view), Toast.LENGTH_SHORT).show();
                //((MainActivity) actividad).mostrarDetalle( recyclerView.getChildAdapterPosition(view));
                ((MainActivity) actividad).mostrarDetalle(
                        (int) adaptador.getItemId(
                                recyclerView.getChildAdapterPosition(view)));
            }

        });
                return vista;
    }

    @Override public void onResume(){ ((MainActivity) getActivity()).mostrarElementos(true); super.onResume(); }
}