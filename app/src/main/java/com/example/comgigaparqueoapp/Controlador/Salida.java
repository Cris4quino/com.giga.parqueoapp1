package com.example.comgigaparqueoapp.Controlador;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comgigaparqueoapp.ClienteSalida;
import com.example.comgigaparqueoapp.R;
import com.example.comgigaparqueoapp.bdAutomoviles;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Salida#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Salida extends Fragment {

    Button btBuscar;
    Button btRegistra;

    View vista;
    EditText laPlaca;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Salida() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Salida.
     */
    // TODO: Rename and change types and number of parameters
    public static Salida newInstance(String param1, String param2) {
        Salida fragment = new Salida();
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
        vista=inflater.inflate(R.layout.fragment_salida, container, false);


        btBuscar=(Button) vista.findViewById(R.id.bt_buscar);
        btBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    bdAutomoviles db=new bdAutomoviles(getContext());
                    db.apertura();
                    EditText placaB = (EditText) vista.findViewById(R.id.et_buscar);
                    TextView pago=(TextView) vista.findViewById(R.id.tv_preciototal);


                    if(!(placaB.getText().toString().equals(""))){


                       String placabusca=placaB.getText().toString();
                        placabusca=placabusca.toUpperCase().trim();
                        String[] datos;
                        datos=db.buscar(placabusca);

                        String mihora= DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                        int tamañoHora=mihora.length();
                        String horaActual=mihora.substring(tamañoHora-8,tamañoHora);
                        horaActual=horaActual.trim();///////////////////hora actual

                        String dia, mes, year;

                        Calendar calendar = Calendar.getInstance();
                        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                        currentDate=currentDate.toUpperCase();

                        int coma=currentDate.indexOf(",");
                        dia=currentDate.substring(coma+2,coma+4).trim();
                        dia=dianum(dia);
                        int de=currentDate.indexOf("DE");
                        mes=currentDate.substring(de+2,de+6);
                        mes=mes.trim();
                        mes=añonum(mes);

                        int tamaño=currentDate.length();
                        year=currentDate.substring(tamaño-4,tamaño);

//                        ////////fecha y hora para la bd
//
                        String tfecha1=year+"/"+mes+"/"+dia;////////////////fecha actual
                        String tfecha2=datos[1];
                        tfecha2=tfecha2.trim();
                        String dia2, mes2, year2;
                        dia2=tfecha2.substring(0,2);
                        dia2=dianum(dia2);
                        mes2=tfecha2.substring(3,7).trim();
                        mes2=añonum(mes2);
                        year2=tfecha2.substring(8,12).trim();
                        String tfecha3=year2+"/"+mes2+"/"+dia2;
                        tfecha3=tfecha3.trim();


                        long total = new Date(tfecha1+" "+horaActual.substring(0,horaActual.length()-3).trim()).getTime() - new Date(tfecha3+" "+datos[2].substring(0,datos[2].length()-3).trim()).getTime();
                        long horas = TimeUnit.HOURS.convert(total, TimeUnit.MINUTES);
                        String totalminutos=String.valueOf(horas);
                        totalminutos=totalminutos.substring(0,totalminutos.length()-3).trim();////////////////////////////////total de minutos en el parqueo
                        String placaet=datos[0];
////////////////////////////////////////
                        String[] datos2;
                        datos2=db.buscarP(placaet);

                        if (datos2[1].equals("hora")) {

                            //Double totalacobrar = sacartiempohoras(totalminutos);
                            Double totalacobrar=Double.parseDouble(totalminutos);
                            totalacobrar=totalacobrar/60;

                            double enteroN=totalacobrar/1;
                            double decimalN=totalacobrar%1;
                            decimalN=decimalN*100;

                            String str1 = String.valueOf(enteroN).trim();
                            String str2 = String.valueOf(decimalN).trim();

                            int intNumber=0;
                            int intNumberd=0;
                            intNumber= Integer.parseInt(str1.substring(0, str1.indexOf('.')));
                            intNumberd= Integer.parseInt(str2.substring(0, str2.indexOf('.')));

                            //intNumberd= Integer.parseInt(str.substring(0, str.indexOf('.')));

                            String precioportiempo = cobroBoliviano(intNumber, intNumberd, datos2[2]);
                            mostrarDialogoBasico(placaet, precioportiempo);
                            pago.setText("PLACA:\n" +"\t\t\t\t"+ placaet + "\n" + "TOTAL A PAGAR:\n" + "\t\t\t\t"+precioportiempo + " Bs");
                            //pago.setText(intNumber+" "+intNumberd+"="+precioportiempo);
                            //pago.setText(intNumber+"   "+intNumberd+"="+precioportiempo);
                            placaB.setText("");

                            //mostrarDialogoBasico(placaet,totalminutos);
                        }
                        if (datos2[1].equals("dia")){
                            if(tfecha3.equals(tfecha1)){
                                mostrarDialogoBasico(placaet, datos2[2]);
                                //pago.setText("PLACA:\n" +"\t\t\t\t"+ placaet + "\n" + "TOTAL A PAGAR:\n" +"\t\t\t\t"+ datos2[2] + " Bs");
                            }
                            else{
                                pago.setText(datos[1]+"     "+tfecha1);
                                //pago.setText("PLACA:\n" + placaet + "\n" + "TOTAL A PAGAR:\n" + "falta considerar" + " Bs");
                            }
                            placaB.setText("");
                        }
                        if (datos2[1].equals("noche")){

                            if(tfecha3.equals(tfecha1)){
                                mostrarDialogoBasico(placaet, datos2[2]);
                                //pago.setText("PLACA:\n" + "\t\t\t\t"+placaet + "\n" + "TOTAL A PAGAR:\n" + "\t\t\t\t"+datos2[2] + " Bs");
                            }
                            else{
                                pago.setText(datos[1]+"     "+tfecha1);
                                //pago.setText("PLACA:\n" + placaet + "\n" + "TOTAL A PAGAR:\n" + "falta considerar" + " Bs");
                            }
                            placaB.setText("");
                        }
                    }else{
                        Toasty.error(getContext(),"Escribir en placa",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    EditText placaB = (EditText) vista.findViewById(R.id.et_buscar);
                    placaB.setText("");
                    Toasty.error(getContext(),"no se encontro tabla",Toast.LENGTH_LONG).show();
                }

            }
        });

        return vista;
    }
    public String añonum(String mes) {
        String num="";
        switch (mes)
        {
            case "ENE":
                num="01";
                break; // break es opcional
            case "FEB":
                num="02";
                break; // break es opcional
            case "MAR":
                num="03";
                break; // break es opcional
            case "ABR":
                num="04";
                break; // break es opcional
            case "MAY":
                num="05";
                break; // break es opcional
            case "JUN":
                num="06";
                break; // break es opcional
            case "JUL":
                num="07";
                break; // break es opcional
            case "AGO":
                num="08";
                break; // break es opcional
            case "SEP":
                num="09";
                break; // break es opcional
            case "OCT":
                num="10";
                break; // break es opcional
            case "NOV":
                num="11";
                break; // break es opcional
            case "DIC":
                num="12";
                break; // break es opcional
        }
        return num;
    }
    public String dianum(String dia){
        String num="";
        switch (dia)
        {
            case "1":
                num="01";
                break; // break es opcional
            case "2":
                num="02";
                break; // break es opcional
            case "3":
                num="03";
                break; // break es opcional
            case "4":
                num="04";
                break; // break es opcional
            case "5":
                num="05";
                break; // break es opcional
            case "6":
                num="06";
                break; // break es opcional
            case "7":
                num="07";
                break; // break es opcional
            case "8":
                num="08";
                break; // break es opcional
            case "9":
                num="09";
                break; // break es opcional
        }
        if (!(num.equals(""))){
            return num;
        }return dia;
    }

    private void mostrarDialogoBasico(String placaet, String totalminutos){
        AlertDialog.Builder builder = new AlertDialog.Builder(vista.getContext());
        builder.setTitle("TOTAL A COBRAR");
        builder.setMessage("PLACA: "+placaet+"\n"+"TOTAL: "+totalminutos+"\n")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edplaca=(EditText) vista.findViewById(R.id.et_buscar);
                        edplaca.setText("");
                        Toast.makeText(getContext(),"Eliminamos datos...",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"Cancel...",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
    private void deseaimprimir(String placaet, String totalminutos){
        AlertDialog.Builder builder = new AlertDialog.Builder(vista.getContext());
        builder.setMessage("¿IMPRIMIR?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edplaca=(EditText) vista.findViewById(R.id.et_buscar);
                        edplaca.setText("");
                        Toast.makeText(getContext(),"Imprimiendo...",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"Cancelado...",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
    public Double sacartiempohoras(String totalminutos){
        Double tminutos=Double.parseDouble(totalminutos);
        tminutos=tminutos/60;
        return tminutos;
    }
//    public int sacarpartentera(Double totalminutos){
//        String str = String.valueOf(totalminutos+"").trim();
//        int intNumberd = Integer.parseInt(str.substring(0, str.indexOf('.')));
//
//        return intNumberd;
//    }
//    public int sacarpartdecimal(Double totalminutos){
//        String str = String.valueOf(totalminutos);
//        float decNumbert = Float.parseFloat(str.substring(str.indexOf('.')));
//        int decNumberInt = Integer.parseInt(str.substring(str.indexOf('.') + 1));
//        return decNumberInt;
//    }

    public String cobroBoliviano(int hora, int minuto, String cobro){
        int totalporhora=0;
        int totalpormin=0;
        int totalcobra=0;
        int decre=0;
        if(hora>0) {
            totalporhora = Integer.parseInt(cobro) * hora;
        }
        if (minuto>0){
            decre=minuto;
            while (decre>0){
                totalpormin++;
                decre=decre-15;
            }
        }

        totalcobra=totalporhora+totalpormin;


        return totalcobra+"";
    }
}