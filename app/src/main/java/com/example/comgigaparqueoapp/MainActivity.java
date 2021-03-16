package com.example.comgigaparqueoapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.comgigaparqueoapp.Controlador.PagerController;
import com.example.comgigaparqueoapp.Controlador.Salida;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.example.comgigaparqueoapp.Controlador.Salida;

public class MainActivity extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewPager;
    TabItem tab1, tab2, tab3;
    PagerController pagerAdapter;
    TextView titulo;
    TextView limpiar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        titulo=(TextView) findViewById(R.id.tv_titulo);
        limpiar=(TextView) findViewById(R.id.tv_preciototal);
        tablayout =findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewpager);
        tab1=findViewById(R.id.tabIngresa);
        tab2=findViewById(R.id.tabSalida);
        tab3=findViewById(R.id.tabParqueo);

        pagerAdapter=new PagerController(getSupportFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        iconColor(tablayout.getTabAt(tablayout.getSelectedTabPosition()), "#ffffff");
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                iconColor(tab, "#ffffff");
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){

                    titulo.setText("   REGISTRO DE PARQUEO");
                    pagerAdapter.notifyDataSetChanged();


                }
                if(tab.getPosition()==1){
                    titulo.setText("   REGISTRO DE SALIDA");
                    pagerAdapter.notifyDataSetChanged();

                }
                if(tab.getPosition()==2){

                    titulo.setText("   VEHICULOS EN PARQUEO");
                    pagerAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                iconColor(tab, "#AEB0B1");

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

    }

    private void iconColor(TabLayout.Tab tab, String color){
        tab.getIcon().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
    }
}
