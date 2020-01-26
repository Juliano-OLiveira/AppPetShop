package com.juliano.pet_shopp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.juliano.pet_shopp.Util.Animais;
import com.juliano.pet_shopp.Util.Animal;
import com.juliano.pet_shopp.Util.AnimalAdapter;
import com.juliano.pet_shopp.Util.Proprietario;
import com.juliano.pet_shopp.Util.Raca;
import com.juliano.pet_shopp.Util.Racas;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    final public static String STATE_ANIMAIS = "STATE_ANIMAIS";
    private static final int ANIMAL_CADASTRAR = 1;
    private static final int ANIMAL_EDITAR = 2;

    ListView listView;
    Animais animais;
    Racas racas;
    Bundle savedInstanceState;
    AnimalAdapter animalAdapter;

    private ProgressDialog progress;

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
        setContentView(R.layout.activity_main);

        this.savedInstanceState = savedInstanceState;

        iniciarLista();
        iniciarBtnAdd();
    }

    private void iniciarBtnAdd() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NovoAnimalActivity.class);
                // startActivity(intent);
                startActivityForResult(intent, ANIMAL_CADASTRAR);
            }
        };

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(onClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Log.i("MainActivity", "onActivityResult: RESULTADO");

        if (requestCode == ANIMAL_CADASTRAR || resultCode == ANIMAL_EDITAR) {
            if (resultCode == RESULT_OK) {
                animais = null;
                iniciarDados();
            }
        }
    }

    private void iniciarLista() {
        listView = findViewById(R.id.lista);

        iniciarDados();

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Animal animal = animais.get(i);

                // Toast.makeText(MainActivity.this, String.format("%d-%s", i, carro), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,
                        EditarAnimalActivity.class);
                intent.putExtra(EditarAnimalActivity.PARAM_ANIMAL, animal);
                startActivityForResult(intent, ANIMAL_EDITAR);
            }
        };
        listView.setOnItemClickListener(itemClickListener);

        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                excluirAnimalConfirmacao( animais.get(i) );

                return true;
            }
        };
        listView.setOnItemLongClickListener(itemLongClickListener);
    }

    private void iniciarDados() {
        if (animais instanceof Animais) {
            return;
        }

        showProgress();

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://192.168.0.101:8080/WebAppPetShop1.2/webresources/petshop/animal/list";

            Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("MainActivity", "Response OK");

                        try {
                            animais = new Animais();

                            for (int i = 0; i< response.length(); i++){

                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONObject jsonracas =jsonObject.getJSONObject ("raca");
                                JSONObject jsonpropri =jsonObject.getJSONObject ("propietario");




                                Animal animal = new Animal();

                                animal.setId(jsonObject.getInt("idAnimal"));
                                animal.setNome(jsonObject.getString("nome"));


                                Raca raca = new Raca(
                                        jsonracas.getInt("id"),
                                    jsonracas.getString("raca")
                                );


                               animal.setRaca(raca);

                                Proprietario proprietario = new Proprietario(
                                        jsonpropri.getInt("id"),
                                        jsonpropri.getString("nome")
                                );
                                   animal.setProprietario(proprietario);


                                animal.setObservacao(jsonObject.getString("observacao"));

                                animal.setUrlFoto(jsonObject.getString("img"));



                               animais.add(animal);

                            }






                        }catch (JSONException e){
                            e.printStackTrace();
                        }







                    animalAdapter = new AnimalAdapter(MainActivity.this, animais);
                    listView.setAdapter(animalAdapter);

                    dismissProgress();
                }

                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
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
        queue.add(request);
            }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // outState.putStringArrayList(STATE_CARROS, carros);
        // outState.putSerializable(STATE_CARROS, carros);
    }

    private void excluirAnimalConfirmacao(final Animal animal) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirmação")
                .setMessage("Deseja apagar este animal?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      animais.remove(animal);
                        animalAdapter.notifyDataSetChanged();

                    }

                })
                .setNegativeButton("Não", null)
                .show();
    }
}
