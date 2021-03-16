package com.example.comgigaparqueoapp.Controlador;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comgigaparqueoapp.R;
import com.example.comgigaparqueoapp.TarifaAutos;
import com.example.comgigaparqueoapp.bdAutomoviles;

import java.text.DateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ingreso#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Ingreso extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Double tPrecio=6.0;

    RadioButton h1;
    RadioButton h2;
    RadioButton d1;
    RadioButton d2;
    RadioButton n1;
    RadioButton n2;



    TextView tarifa;
    TextView prueba;
    Button btGuardar;

    View vista;
    TextView tHora;
    TextView tFecha;

    EditText edPlaca;
    EditText ednombre;
    EditText edcelular;

    int hora=0, minuto =0, segundo = 0;
    Intent i;
    Thread iniReloj = null;
    Runnable r;
    boolean isUpdate = false;
    String sec, min, hor;
    String curTime;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Ingreso() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ingreso.
     */
    // TODO: Rename and change types and number of parameters



    public static Ingreso newInstance(String param1, String param2) {
        Ingreso fragment = new Ingreso();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_ingreso, container, false);



//        tHora = (TextView) vista.findViewById(R.id.tv_hora);
//        tFecha= (TextView) vista.findViewById(R.id.tv_fecha);
//        tarifa= (TextView) vista.findViewById(R.id.tv_tarifa);
        edPlaca=(EditText) vista.findViewById(R.id.et_placa);

        r = new Ingreso.RefreshClock();
        iniReloj= new Thread(r);
        iniReloj.start();


        String dia, mes, year;

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        currentDate=currentDate.toUpperCase();

        int coma=currentDate.indexOf(",");
        dia=currentDate.substring(coma+2,coma+4);
        int de=currentDate.indexOf("DE");
        mes=currentDate.substring(de+2,de+6);
        int tamaño=currentDate.length();
        year=currentDate.substring(tamaño-4,tamaño);
//        TextView textViewDate =(TextView) vista.findViewById(R.id.tv_fecha);
//        textViewDate.setText(dia+"-"+mes+"-"+year);

        ////////fecha y hora para la bd

        String tfecha=dia+"-"+mes+"-"+year;

        String mihora=DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        int tamañoHora=mihora.length();
        String horaActual=mihora.substring(tamañoHora-9,tamañoHora);
        horaActual=horaActual.trim();

        btGuardar=(Button) vista.findViewById(R.id.bt_guardar);

        h1=(RadioButton) vista.findViewById(R.id.cb_mediano);
        h2=(RadioButton) vista.findViewById(R.id.cb_grande);
        d1=(RadioButton) vista.findViewById(R.id.cb_diurno);
        d2=(RadioButton) vista.findViewById(R.id.cb_dianoche);
        n1=(RadioButton) vista.findViewById(R.id.cb_medianoN);
        n2=(RadioButton) vista.findViewById(R.id.cb_grandeN);


        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (h1.isChecked()){
                    d1.setChecked(false);
                    d2.setChecked(false);
                    n1.setChecked(false);
                    n2.setChecked(false);
                }
            }
        });

        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (h2.isChecked()){
                    d1.setChecked(false);
                    d2.setChecked(false);
                    n1.setChecked(false);
                    n2.setChecked(false);
                }
            }
        });

        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (d1.isChecked()){
                    h1.setChecked(false);
                    h2.setChecked(false);
                    n1.setChecked(false);
                    n2.setChecked(false);
                }
            }
        });

        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (d2.isChecked()){
                    h1.setChecked(false);
                    h2.setChecked(false);
                    n1.setChecked(false);
                    n2.setChecked(false);
                }
            }
        });

        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (n1.isChecked()){
                    h1.setChecked(false);
                    h2.setChecked(false);
                    d1.setChecked(false);
                    d2.setChecked(false);
                }
            }
        });

        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (n2.isChecked()){
                    h1.setChecked(false);
                    h2.setChecked(false);
                    d1.setChecked(false);
                    d2.setChecked(false);
                }
            }
        });

        btGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    bdAutomoviles db=new bdAutomoviles(getContext());
                    db.apertura();
                    EditText text = (EditText) vista.findViewById(R.id.et_placa);
                    String laPlaca= text.getText().toString();
                    laPlaca=laPlaca.toUpperCase().trim();

                    String dia, mes, year;

                    Calendar calendar = Calendar.getInstance();
                    String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    currentDate=currentDate.toUpperCase();

                    int coma=currentDate.indexOf(",");
                    dia=currentDate.substring(coma+2,coma+4);
                    int de=currentDate.indexOf("DE");
                    mes=currentDate.substring(de+2,de+6);
                    int tamaño=currentDate.length();
                    year=currentDate.substring(tamaño-4,tamaño);

                    ////////fecha y hora para la bd

                    String tfecha=dia+"-"+mes+"-"+year;

                    String mihora=DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    int tamañoHora=mihora.length();
                    String horaActual=mihora.substring(tamañoHora-8,tamañoHora);
                    horaActual=horaActual.trim();
                    h1=(RadioButton) vista.findViewById(R.id.cb_mediano);
                    h2=(RadioButton) vista.findViewById(R.id.cb_grande);
                    d1=(RadioButton) vista.findViewById(R.id.cb_diurno);
                    d2=(RadioButton) vista.findViewById(R.id.cb_dianoche);
                    n1=(RadioButton) vista.findViewById(R.id.cb_medianoN);
                    n2=(RadioButton) vista.findViewById(R.id.cb_grandeN);

                    String tipotiempo="";
                    int cont=0;

                    if (h1.isChecked()){
                        cont++;
                        tipotiempo="hora";
                        String[] datos;
                        datos=db.buscarT("hora");
                        if (edPlaca.getText().toString().length()>0 && edPlaca.getText().toString().length()<=8)
                        {long inparqueo=db.InsertarP(edPlaca.getText().toString().toUpperCase().trim(), datos[0], datos[1]);}


                    }
                    if (h2.isChecked()){
                        cont++;
                        tipotiempo="hora";
                        String[] datos;
                        datos=db.buscarT("hora");
                        if (edPlaca.getText().toString().length()>0 && edPlaca.getText().toString().length()<=8)
                        {long inparqueo=db.InsertarP(edPlaca.getText().toString().toUpperCase().trim(), datos[0], datos[2]);}

                    }
                    if (d1.isChecked()){
                        cont++;
                        tipotiempo="dia";
                        String[] datos;
                        datos=db.buscarT("dia");
                        if (edPlaca.getText().toString().length()>0 && edPlaca.getText().toString().length()<=8)
                        {long inparqueo=db.InsertarP(edPlaca.getText().toString().toUpperCase().trim(), datos[0], datos[1]);}

                    }
                    if (d2.isChecked()){
                        cont++;
                        tipotiempo="dia";
                        String[] datos;
                        datos=db.buscarT("dia");
                        if (edPlaca.getText().toString().length()>0 && edPlaca.getText().toString().length()<=8)
                        {long inparqueo=db.InsertarP(edPlaca.getText().toString().toUpperCase().trim(), datos[0], datos[1]);}


                    }
                    if (n1.isChecked()){
                        cont++;
                        tipotiempo="noche";
                        String[] datos;
                        datos=db.buscarT("noche");
                        if (edPlaca.getText().toString().length()>0 && edPlaca.getText().toString().length()<=8)
                        {long inparqueo=db.InsertarP(edPlaca.getText().toString().toUpperCase().trim(), datos[0], datos[1]);}

                    }
                    if (n2.isChecked()){
                        cont++;
                        tipotiempo="noche";
                        String[] datos;
                        datos=db.buscarT("noche");
                        if (edPlaca.getText().toString().length()>0 && edPlaca.getText().toString().length()<=8)
                        {long inparqueo=db.InsertarP(edPlaca.getText().toString().toUpperCase().trim(), datos[0], datos[1]);}

                    }

                    edcelular=(EditText) vista.findViewById(R.id.et_celular);
                    ednombre=(EditText) vista.findViewById(R.id.et_nombre);

                    if (edPlaca.getText().toString().length()>3 && edPlaca.getText().toString().length()<=8 &&
                            !(tipotiempo.equals("")) && cont==1 && !(edcelular.getText().toString().equals("")) &&
                                !(ednombre.getText().toString().equals(""))) {
                        long nbg = db.Insertar(laPlaca, tfecha, horaActual,tipotiempo);

                        Toasty.success(getContext(), "Se guardo correctamente", Toast.LENGTH_LONG).show();

                        //Toast.makeText(getContext(), "Se guardo correctamente", Toast.LENGTH_LONG).show();

                    }else{
                        Toasty.error(getContext(),"No lleno adecuadamente",Toast.LENGTH_LONG).show();
                        //Toast.makeText(getContext(),"No lleno adecuadamente",Toast.LENGTH_LONG).show();
                    }

                    edPlaca.setText("");
                    edcelular=(EditText) vista.findViewById(R.id.et_celular);
                    ednombre=(EditText) vista.findViewById(R.id.et_nombre);
                    edcelular.setText("");
                    ednombre.setText("");

                    d1.setChecked(false);
                    d2.setChecked(false);
                    h1.setChecked(false);
                    h2.setChecked(false);
                    n1.setChecked(false);
                    n2.setChecked(false);

                }catch (Exception e){
                    edPlaca.setText("");
                    edcelular=(EditText) vista.findViewById(R.id.et_celular);
                    ednombre=(EditText) vista.findViewById(R.id.et_nombre);
                    edcelular.setText("");
                    ednombre.setText("");

                    d1.setChecked(false);
                    d2.setChecked(false);
                    h1.setChecked(false);
                    h2.setChecked(false);
                    n1.setChecked(false);
                    n2.setChecked(false);


                    Toasty.error(getContext(),"Error",Toast.LENGTH_LONG).show();
                    //Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                }

            }
        });


        return vista;
    }

    private void initClock() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try{

                    if(isUpdate){
                        settingNewClock();
                    } else {
                        updateTime();
                    }
                    curTime =hor+ hora + min + minuto + sec + segundo;
                    tHora.setText(curTime);

                }catch (Exception e) {}
            }
        });
    }

    class RefreshClock implements Runnable{
        // @Override
        @SuppressWarnings("unused")
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    initClock();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }


    private void updateTime(){

        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);
        segundo = c.get(Calendar.SECOND);
        setZeroClock();

    }
    private void setZeroClock(){
        if(hora >=0 & hora <=9){
            hor = "0";
        }else{
            hor = "";
        }

        if(minuto >=0 & minuto <=9){
            min = ":0";
        }else{
            min = ":";
        }

        if(segundo >=0 & segundo <=9){
            sec = ":0";

        }else{
            sec = ":";
        }
    }


    private void settingNewClock(){
        segundo +=1;

        setZeroClock();

        if(segundo >=0 & segundo <=59){

        }else {
            segundo = 0;
            minuto +=1;
        }
        if(minuto >=0 & minuto <=59){

        }else{
            minuto = 0;
            hora +=1;
        }
        if(hora >= 0 & hora <= 24){

        }else{
            hora = 0;
        }

    }

    private void mensaje(String m, int i){
        if(i==1){
            Toast.makeText(getActivity().getApplicationContext(), m, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity().getApplicationContext(), m, Toast.LENGTH_LONG).show();
        }

    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.getActivity().onBackPressed();
        this.getActivity().finish();
    }



}