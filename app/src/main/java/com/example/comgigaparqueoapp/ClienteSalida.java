package com.example.comgigaparqueoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

public class ClienteSalida extends AppCompatActivity {

    TextView placa;
    TextView cobro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_salida);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String minutosp=getIntent().getStringExtra("minutosparqueo");
        String placap=getIntent().getStringExtra("placa");

        placa=(TextView) findViewById(R.id.tv_placasa);
        cobro=(TextView) findViewById(R.id.tv_preciocobra);

        placa.setText(placap);
        cobro.setText(minutosp);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}