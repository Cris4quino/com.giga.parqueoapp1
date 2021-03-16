package com.example.comgigaparqueoapp.Controlador;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comgigaparqueoapp.R;
import com.example.comgigaparqueoapp.bdAutomoviles;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Parada#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Parada extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    View vista;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Parada() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Parada.
     */
    // TODO: Rename and change types and number of parameters
    public static Parada newInstance(String param1, String param2) {
        Parada fragment = new Parada();
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
        vista =inflater.inflate(R.layout.fragment_parada, container, false);



        try {
            bdAutomoviles db=new bdAutomoviles(vista.getContext());
            db.apertura();
            long nbg1 = db.InsertarT("hora", "4","5");
            long nbg2 = db.InsertarT("dia", "20","32");
            long nbg3 = db.InsertarT("noche", "12","20");

            String resultado=db.Listar();
            TextView tvVehiculos=(TextView) vista.findViewById(R.id.tv_vehiculos);
            tvVehiculos.setText(resultado);


        }catch (Exception e){
            Toasty.error(getContext(), "Hubo un inconveniente", Toast.LENGTH_LONG).show();
        }


        return vista;
    }
}