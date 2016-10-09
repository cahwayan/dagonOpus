package com.tcc.dagon.opus.Modulos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tcc.dagon.opus.Adapters.FragmentPageAdapter;
import com.tcc.dagon.opus.R;

public class Modulo_1 extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTabLayout = (TabLayout)findViewById(R.id.tab_layout);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);

        mViewPager.setAdapter(new FragmentPageAdapter(getSupportFragmentManager(),getResources().getStringArray(R.array.titles_tab)));

        mTabLayout.setupWithViewPager(mViewPager);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
