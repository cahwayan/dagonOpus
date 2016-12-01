package com.tcc.dagon.opus;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

import static java.lang.String.valueOf;

public class CertificadoIncompleto extends AppCompatActivity {

    private Button btnCertificadoIncompleto;
    private TextView txtTituloCertificado;
    private GerenciadorBanco DB_PROGRESSO;
    private RoundCornerProgressBar barraGeralCertificado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado_incompleto);

        if(DB_PROGRESSO != null) {
            DB_PROGRESSO = new GerenciadorBanco(this);
        }


        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        accessViews();

        carregarProgresso();

        btnCertificadoIncompleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void accessViews() {
        btnCertificadoIncompleto = (Button) findViewById(R.id.btnCertificadoIncompleto);
        txtTituloCertificado = (TextView) findViewById(R.id.txtTituloCertificado);
        barraGeralCertificado = (RoundCornerProgressBar) findViewById(R.id.barraGeralCertificado);

        Typeface harabara = Typeface.createFromAsset(getAssets(), "fonts/harabara.ttf");
        btnCertificadoIncompleto.setTypeface(harabara);
        txtTituloCertificado.setTypeface(harabara);

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

    private void carregarProgresso() {
        barraGeralCertificado.setProgress(Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(1) +
                DB_PROGRESSO.verificaProgressoEtapa(2) +
                DB_PROGRESSO.verificaProgressoEtapa(3) +
                DB_PROGRESSO.verificaProgressoEtapa(4) +
                DB_PROGRESSO.verificaProgressoEtapa(5) +
                DB_PROGRESSO.verificaProgressoEtapa(6) +
                DB_PROGRESSO.verificaProgressoEtapa(7) +
                DB_PROGRESSO.verificaProgressoEtapa(8))) );
    }

}
