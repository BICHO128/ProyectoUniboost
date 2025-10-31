package com.camila.proyectouniboost;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adaptador extends BaseAdapter {

    private final LayoutInflater inflater;
    private final Context contexto;
    private final String[][] datos;
    private final int[] datosImg;

    public Adaptador(Context contexto, String[][] datos, int[] imagenes) {
        this.contexto = contexto;
        this.datos = (datos != null) ? datos : new String[0][0];
        this.datosImg = (imagenes != null) ? imagenes : new int[0];
        this.inflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        // Evita desfaces entre arrays (clave para no salirse del índice)
        int nDatos = (datos != null) ? datos.length : 0;
        int nImgs  = (datosImg != null) ? datosImg.length : 0;
        return Math.min(nDatos, nImgs);
    }

    @Override
    public Object getItem(int position) {
        return (position >= 0 && position < getCount()) ? datos[position] : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView titulo, duracion, director;
        ImageView imagen;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        // Guarda extra (no debería entrar si getCount está bien, pero suma)
        if (i < 0 || i >= getCount()) {
            return (convertView != null) ? convertView : new View(contexto);
        }

        ViewHolder vh;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.elemento_lista, parent, false);
            vh = new ViewHolder();
            vh.titulo   = convertView.findViewById(R.id.tvTitulo);
            vh.duracion = convertView.findViewById(R.id.tvDuracion);
            vh.director = convertView.findViewById(R.id.tvDirector);
            vh.imagen   = convertView.findViewById(R.id.ivImagen);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        String[] fila = datos[i]; // {titulo, director, duracion, ..., ...}

        if (fila != null) {
            if (fila.length > 0) vh.titulo.setText(fila[0]);
            if (fila.length > 1) vh.director.setText(fila[1]);
            if (fila.length > 2) vh.duracion.setText("Duración " + fila[2]);
        }

        vh.imagen.setImageResource(datosImg[i]);
        vh.imagen.setTag(i);
        vh.imagen.setOnClickListener(v -> {
            Intent visorImagen = new Intent(contexto, VisorImagen.class);
            visorImagen.putExtra("IMG", datosImg[(Integer) v.getTag()]);
            contexto.startActivity(visorImagen);
        });

        return convertView;
    }
}
