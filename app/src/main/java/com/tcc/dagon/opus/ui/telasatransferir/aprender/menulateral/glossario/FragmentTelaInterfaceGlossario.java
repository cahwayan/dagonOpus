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
 * Created by cahwayan on 04/12/2016.
 */

public class FragmentTelaInterfaceGlossario extends LicaoFragment {

    private Button btnVoltar2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*super.viewRoot = inflater.inflate(R.layout.fragment_glossario_interface,container,false);

        this.accessViews();

        btnVoltar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        return this.viewRoot;*/
        return null;
    }

    protected void accessViews() {
       // mViewPager = ((ContainerComandosGlossario)getActivity()).getPager();
        //btnVoltar2 = (Button) viewRoot.findViewById(R.id.btnVoltar2);
        //super.init();
    }

}
