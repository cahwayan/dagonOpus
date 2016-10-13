package com.tcc.dagon.opus;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class GerenciarPerfilActivity extends AppCompatActivity {
    TextView perfilEmail,perfilNome;
    String imagemUrl;
    ImageView ivProfile;
    ProgressBar pbProfile;


    public void loadImage(final ImageView ivImg, final ProgressBar pbImg, final String urlImg){
        RequestQueue rq = Volley.newRequestQueue(GerenciarPerfilActivity.this);
        ImageLoader il = new ImageLoader(rq, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                pbImg.setVisibility(View.GONE);
            }
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        pbImg.setVisibility(View.VISIBLE);
        il.get(urlImg, il.getImageListener(ivImg, pbImg.getId(), pbImg.getId()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_perfil);
        perfilEmail = (TextView) findViewById(R.id.perfilEmail);
        perfilNome = (TextView) findViewById(R.id.perfilNome);
        pbProfile = (ProgressBar)findViewById(R.id.pbProfile1);

        Bundle extras = getIntent().getExtras();
        perfilEmail.setText(extras.getString("emailBundle"));
        perfilNome.setText(extras.getString("nomeBundle"));
        imagemUrl = (extras.getString("imagemBundle"));

        imagemUrl = imagemUrl.substring(0, imagemUrl.length() - 2)+"200";
        loadImage(ivProfile, pbProfile, imagemUrl);





    }


}
