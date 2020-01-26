package com.juliano.pet_shopp;

import android.app.ProgressDialog;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.juliano.pet_shopp.Util.Animal;
import com.juliano.pet_shopp.Util.Proprietario;
import com.juliano.pet_shopp.Util.Proprietarios;
import com.juliano.pet_shopp.Util.Raca;
import com.juliano.pet_shopp.Util.Racas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class NovoAnimalActivity extends AppCompatActivity {

    private static final String TAG = NovoAnimalActivity.class.getSimpleName();

    Spinner spinnerRaca;
    Spinner spinnerProprietatio;
    EditText etNome;

    EditText etObs;
    EditText etPorte;
    EditText etPeso;
    EditText etSexo;
    EditText etidade;
    Button btnSalvar;
    EditText etFoto;
    private ProgressDialog progress;

    protected Animal animal = new Animal();


    protected void dismissProgress() {
        progress.dismiss();
        progress = null;
    }

    protected void showProgress() {
        progress = new ProgressDialog(this);
        progress.setTitle("Carregando");
        progress.setMessage("Obtendo dados necessários.");
        progress.setCancelable(false);

        progress.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_animal);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        etNome = findViewById(R.id.etNome);

        etObs = findViewById(R.id.Observavao);
        etPorte = findViewById(R.id.porte);
        etFoto = findViewById(R.id.etFoto);
        etidade = findViewById(R.id.edIdade);
        etSexo = findViewById(R.id.sexo);
        etPeso = findViewById(R.id.peso);

        iniciarSpinnerRaca();
        iniciarSpinnerProprietario();
        iniciarBtnSalvar();
    }


    private void iniciarBtnSalvar() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        };

        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(onClickListener);
    }

    protected String getUrl() {
        return "http://192.168.0.101:8080/WebAppPetShop1.2/webresources/petshop/animal/inserir";
    }

    private void salvar() {
        showProgress();

        final Raca raca = (Raca) spinnerRaca.getSelectedItem();
        //(Raca) spinnerRaca.getSelectedItem();
         final Proprietario proprietario =  (Proprietario) spinnerProprietatio.getSelectedItem();
        final String nome = etNome.getText().toString();

        final String obs = etObs.getText().toString();
        final String porte = etPorte.getText().toString();
        final String foto = etFoto.getText().toString();
        final String sexo = etSexo.getText().toString();
        final String idade = etidade.getText().toString();
        final  String peso = etPeso.getText().toString();
        Log.i(TAG, raca.getRaca());
        Log.i(TAG, proprietario.getProprietario());
        Log.i(TAG, nome);
        Log.i(TAG, peso);
        Log.i(TAG, obs);
        Log.i(TAG, porte);
        Log.i(TAG, idade);
        Log.i(TAG, sexo);
        Log.i(TAG, foto);

        animal.setRaca(raca);
        animal.setNome(nome);
        animal.setProprietario(proprietario);
        animal.setObservacao(obs);
        animal.setPorte(porte);
        animal.setUrlFoto(foto);
        animal.setIdade(Integer.parseInt(idade));
        animal.setPeso(Double.parseDouble(peso));
       animal.setSexo(sexo);
        String url = getUrl();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response);

                dismissProgress();

                Toast.makeText(NovoAnimalActivity.this, "Animal Cadastrado",
                        Toast.LENGTH_LONG).show();

                setResult(RESULT_OK);
                finish();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgress();

                error.printStackTrace();
                try {
                    Log.e(TAG, new String(error.networkResponse.data, "UTF-8"), null);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                listener,
                errorListener
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {

                String body = "";
                try {
                    body = getParamsAnimal().toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return body.getBytes();
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }



    protected JSONObject getParamsAnimal() throws JSONException {

        JSONObject params = new JSONObject();

       JSONObject  jsonracas = new JSONObject();
        jsonracas.put("id",animal.getRaca().getId());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",animal.getProprietario().getId());
       //Log.i(TAG,"error" + racass.put("id",animal.getRaca().getId()));



        params.put("raca",jsonracas);
        params.put("propietario", jsonObject);




        params.put("nome", animal.getNome());

        params.put("observacao", animal.getObservacao());
        params.put("porte", animal.getPorte());
        params.put("peso", animal.getPeso());

        params.put("sexo", animal.getSexo());
        params.put("idade", animal.getIdade());
        params.put("img", animal.getUrlFoto());

        return params;
    }




    private void iniciarSpinnerRaca() {
        spinnerRaca = findViewById(R.id.spinnerRacas);

      showProgress();

        String url = "http://192.168.0.101:8080/WebAppPetShop1.2/webresources/petshop/raca/list";

        Response.Listener<JSONArray> listener
                = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "Response OK");

                Racas racas = new Racas();

                try {


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject racaJson = response.getJSONObject(i);


                        Raca raca = new Raca(
                                racaJson.getInt("id"),
                                racaJson.getString("raca").toUpperCase()
                        );

                        racas.add(raca);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapter = new ArrayAdapter(
                        NovoAnimalActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                       racas
                );
                spinnerRaca.setAdapter(adapter);

                iniciarSpinnerRacaCompleto();
            }
        };
        Response.ErrorListener errorListener
                = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                dismissProgress();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                listener,
                errorListener
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }

    private void iniciarSpinnerProprietario() {
        spinnerProprietatio = findViewById(R.id.spinnerProprietatio);

       // showProgress();

        String url = "http:/192.168.0.101:8080/WebAppPetShop1.2/webresources/petshop/cliente/list";

        Response.Listener<JSONArray> listener
                = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "Response OK");

                Proprietarios proprietarios = new Proprietarios();

                try {


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject animalJson = response.getJSONObject(i);

                        Proprietario propi = new Proprietario(
                                animalJson.getInt("id"),
                                animalJson.getString("nome").toUpperCase()
                        );

                        proprietarios.add(propi);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapter = new ArrayAdapter(
                        NovoAnimalActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        proprietarios
                );

                spinnerProprietatio.setAdapter(adapter);


            }
        };
        Response.ErrorListener errorListener
                = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                showProgress();
                dismissProgress();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                listener,
                errorListener
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

}




    protected void iniciarSpinnerRacaCompleto() {dismissProgress(); }
            }
