package com.juliano.pet_shopp.Util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import  com.juliano.pet_shopp.R;

public class AnimalAdapter extends BaseAdapter{

    private Activity activity;
    private  Animais animais;

    public AnimalAdapter(Activity activity, Animais animais) {
        this.activity = activity;
        this.animais = animais;
    }

    @Override
    public int getCount() {
        return animais.size();
    }

    @Override
    public Object getItem(int i) {
        return animais.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Animal animal = (Animal) getItem(i);

        View v = activity.getLayoutInflater()
                .inflate(R.layout.animal, viewGroup, false);

        TextView tvNome = v.findViewById(R.id.nome);
        tvNome.setText(animal.getNome());

        TextView tvTutor = v.findViewById(R.id.tutor);
        tvTutor.setText(( animal.getProprietario().getProprietario()));

        TextView tvRaca = v.findViewById(R.id.raca);
         tvRaca.setText(animal.getRaca().getRaca());

        TextView tvObs = v.findViewById(R.id.obs);
        tvObs.setText(animal.getObservacao());

        ImageView ivFoto = v.findViewById(R.id.ivFoto);

        // Imagem local
        // ivFoto.setImageResource(carro.getFoto());

        // Imagem da internet
        new ImageAsyncTask(ivFoto).execute(animal.getUrlFoto());

        return v;
    }



}
