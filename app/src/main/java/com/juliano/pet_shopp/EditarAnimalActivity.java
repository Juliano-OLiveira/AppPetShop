package com.juliano.pet_shopp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.juliano.pet_shopp.Util.Animal;
import com.juliano.pet_shopp.Util.Proprietario;
import com.juliano.pet_shopp.Util.Raca;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditarAnimalActivity extends NovoAnimalActivity {

    protected  static final String TAG = EditarAnimalActivity.class.getSimpleName();

    public static final String PARAM_ANIMAL = "animal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        animal = (Animal) intent.getSerializableExtra(PARAM_ANIMAL);

        Log.i("EditarAnimalActivity", animal.toString());

        atualizarTitulo();
    }

    private void atualizarTitulo() {
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        tvTitulo.setText("Atualizar animal");
    }

    @Override
    protected void iniciarSpinnerRacaCompleto() {
        super.iniciarSpinnerRacaCompleto();

        carregarAnimal();
    }

    @Override
    protected String getUrl() {
        return  "http://192.168.0.101:8080/WebAppPetShop1.2/webresources/petshop/animal/get/"+animal.getId() ;

    }

    private void carregarAnimal() {
        showProgress();
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "OnResponse:ok Animal");
                try {

                    // int idRaca = response.getInt("id");
                    // Raca raca = new Raca(idRaca,null);
                    // animal.setRaca(raca);


                    animal.setNome(response.getString("nome"));
                    animal.setObservacao(response.getString("observacao"));
                    animal.setPeso((response.getDouble("peso")));
                    animal.setPorte(response.getString("porte"));
                    animal.setIdade((response.getInt("idade")));
                    animal.setSexo(response.getString("sexo"));
                    animal.setUrlFoto(response.getString("img"));


                    //acessa o adapter as posições
                    ArrayAdapter<Raca> adapter = (ArrayAdapter) spinnerRaca.getAdapter();
                    int pos = adapter.getPosition(animal.getRaca());
                    spinnerRaca.setSelection(pos);

                    ArrayAdapter<Proprietario> adapter2 = (ArrayAdapter) spinnerProprietatio.getAdapter();
                    int pos2 = adapter2.getPosition(animal.getProprietario());
                    spinnerRaca.setSelection(pos2);

                    //pega o valores e mostra na tela de edicão
                    etNome.setText(animal.getNome());
                    etObs.setText(animal.getObservacao());
                    etPorte.setText(animal.getPorte());
                    etPeso.setText(String.valueOf(animal.getPeso()));
                    etidade.setText(String.valueOf(animal.getIdade()));
                    etSexo.setText(animal.getSexo());
                    etFoto.setText(animal.getUrlFoto());


                    //animal.setProprietario(response.getString("propietario"));
                    // animal.setObservacao(response.getString("observacao"));
                    //animal.setPorte(response.getString("porte"));
                    //animal.setIdade(response.getInt("idade"));
                    // animal.setSexo(response.getString("sexo"));
                    // animal.setUrlFoto(response.getString("img"));


                } catch (JSONException e) {
                    e.printStackTrace();

                }

                dismissProgress();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                getUrl(),
                null,
                listener,
                errorListener
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }





    //comando para poder alterar  -- sem isso ele salva
    @Override
    protected JSONObject getParamsAnimal() throws JSONException {
        JSONObject params = super.getParamsAnimal();
        params.put(("_method"),"PUT");
       return params;
    }
}
