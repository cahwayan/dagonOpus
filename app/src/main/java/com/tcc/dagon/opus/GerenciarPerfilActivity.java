package com.tcc.dagon.opus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



public class GerenciarPerfilActivity extends AppCompatActivity {

    private ImageView foto;
    private String encoded_string, nome_imagem,email_user;
    private Bitmap bitmap;
    private File arquivo;
    private Uri file_uri;
    private Handler handler = new Handler();
    private TextView ca;
    RequestQueue requestQueue;
    String URLMOSTRA = "http://dagonopus.esy.es/phpAndroid/mostrarFoto.php?EMAIL_MOSTRA=";
    String URLFIM,imgfim;
    String urlTeste = "http://dagonopus.esy.es/phpAndroid/";
    String caminho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_perfil);
        ca=(TextView)findViewById(R.id.textViewca);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Intent i= getIntent();
        Bundle b = i.getExtras();





        if(b!=null)
        {
            email_user =(String) b.get("id");
        }
        URLFIM = URLMOSTRA+email_user;

        foto = (ImageView)findViewById(R.id.iconPerfil);


        carregaLink();

        ca.setText(imgfim);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri();
                i.putExtra(MediaStore.EXTRA_OUTPUT,file_uri);

                startActivityForResult(i,10);
            }
        });



    }

    private void getFileUri() {
        nome_imagem = "dagon.jpg";
        arquivo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        + File.separator + nome_imagem
        );

        file_uri = Uri.fromFile(arquivo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==10&&resultCode==RESULT_OK){
            new Encode_image().execute();

        }
    }
//teste
    private class Encode_image extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            bitmap.recycle();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            makeRequest();
        }
    }


    private void makeRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "http://dagonopus.esy.es/phpAndroid/insereFoto.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("email_user",email_user);
                map.put("encoded_string",encoded_string);
                map.put("imagem_nome",nome_imagem);


                return map;
            }
        };
        requestQueue.add(request);
    }

    public void loadImg(){

        new Thread(){
            public void run(){
                Bitmap img = null;

                try{
                    URL url = new URL(imgfim);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream input = conexao.getInputStream();
                    img = BitmapFactory.decodeStream(input);
                }
                catch(IOException e){}

                final Bitmap imgAux = img;
                handler.post(new Runnable(){
                    public void run(){
                        ImageView iv = new ImageView(getBaseContext());
                        foto.setImageBitmap(imgAux);


                    }
                });
            }
        }.start();
    }




        public void carregaLink() {
            System.out.println("ww");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URLFIM, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {

                    System.out.println(response.toString());
                    try {


                        JSONArray students = response.getJSONArray("students");
                        for (int i = 0; i < students.length(); i++) {
                            JSONObject student = students.getJSONObject(i);

                            caminho = student.getString("END_FOTO");
                            imgfim=urlTeste+caminho;
                            if(imgfim.equals("http://dagonopus.esy.es/phpAndroid/null")){
                                Toast.makeText(getApplicationContext(),
                                        "TESTE",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                ca.append(imgfim);
                                loadImg();
                            }


                        }
                        //result.append("===\n");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.append(error.getMessage());

                }
            }){



            };

            requestQueue.add(jsonObjectRequest);
        }

}






