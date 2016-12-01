package com.tcc.dagon.opus;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CertificadoIncompleto extends AppCompatActivity {

    private Button btnCertificadoIncompleto;
    private TextView txtTituloCertificado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado);

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Typeface harabara = Typeface.createFromAsset(getAssets(), "fonts/harabara.ttf");

        btnCertificadoIncompleto = (Button) findViewById(R.id.btnCertificadoIncompleto);
        txtTituloCertificado = (TextView) findViewById(R.id.txtTituloCertificado);

        if(btnCertificadoIncompleto != null) {
            btnCertificadoIncompleto.setTypeface(harabara);
        }

        if(txtTituloCertificado != null) {
            txtTituloCertificado.setTypeface(harabara);
        }

        btnCertificadoIncompleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // MÉTODO QUE VOLTA PRA TELA APRENDER QUANDO CLICAR NA SETA LA EM CIMA
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
