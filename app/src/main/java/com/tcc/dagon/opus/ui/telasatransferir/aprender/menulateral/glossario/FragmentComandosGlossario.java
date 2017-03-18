package com.tcc.dagon.opus.ui.telasatransferir.aprender.menulateral.glossario;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.tcc.dagon.opus.ui.curso.licao.LicaoFragment;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 25/11/2016.
 */

public class FragmentComandosGlossario extends LicaoFragment {

    private Button btnVoltar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;

        /*super.viewRoot = inflater.inflate(R.layout.fragment_glossario_comandos,container,false);

        this.accessViews();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return this.viewRoot;*/
    }

    protected void accessViews() {
        //mViewPager = ((ContainerComandosGlossario)getActivity()).getPager();
        //btnVoltar = (Button) viewRoot.findViewById(R.id.btnVoltar);
        //super.init();
    }
}
