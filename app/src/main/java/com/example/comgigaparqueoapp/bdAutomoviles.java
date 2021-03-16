package com.example.comgigaparqueoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class bdAutomoviles {
    public static final String CODPLACA="placa";
    public static final String CODFECHA="fecha";
    public static final String CODHORA="hora";
    public static final String CODTIPO="tipo";

    private static final String N_TABLA="vehiculos";

    public static final String CODTCOBRO="tcobro";
    public static final String CODPRECIO="precio";

    private static final String NOMBRE_TABLA="parqueo";

    public static final String CODPRE1="precio1";
    public static final String CODPRE2="precio2";

    private static final String NOM_TABLA="tarifa";

    private static final String N_BD="parqueo.db";
    private static final int VERSION_BD=21;

    private Creactua Control;
    private final Context nContexto;
    private SQLiteDatabase pBD;
    private static class Creactua extends SQLiteOpenHelper {

        public Creactua(@Nullable Context context) {
            super(context, N_BD, null, VERSION_BD);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+N_TABLA+" ( "+
                    CODPLACA+" TEXT NOT NULL PRIMARY KEY,"+
                    CODFECHA+" TEXT NOT NULL,"+
                    CODHORA+" TEXT NOT NULL,"+
                    CODTIPO+" TEXT NOT NULL);"
            );
            db.execSQL("CREATE TABLE "+NOMBRE_TABLA+" ( "+
                    CODPLACA+" TEXT PRIMARY KEY,"+
                    CODTCOBRO+" TEXT,"+
                    CODPRECIO+" TEXT);"
            );
            db.execSQL("CREATE TABLE "+NOM_TABLA+" ( "+
                    CODTCOBRO+" TEXT PRIMARY KEY,"+
                    CODPRE1+" TEXT,"+
                    CODPRE2+" TEXT);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+N_TABLA);

            db.execSQL("DROP TABLE IF EXISTS "+NOMBRE_TABLA);

            db.execSQL("DROP TABLE IF EXISTS "+NOM_TABLA);
            onCreate(db);

        }
    }
    public bdAutomoviles(Context c){
        nContexto=c;
    }


    public bdAutomoviles apertura() throws Exception{

        Control=new Creactua(nContexto);
        pBD=Control.getWritableDatabase();
        return this;
    }

    public void cerrar(){
        Control.close();
    }

    public long Insertar(String qpla,String qfecha,String qhora, String qtipo){
        ContentValues cv=new ContentValues();
        cv.put(CODPLACA, qpla);
        cv.put(CODFECHA, qfecha);
        cv.put(CODHORA, qhora);
        cv.put(CODTIPO, qtipo);
        return pBD.insert(N_TABLA, null, cv);

    }

    public String Listar(){
        String[] columnas =new String[]{CODPLACA,CODFECHA,CODHORA,CODTIPO};
        Cursor c=pBD.query(N_TABLA, columnas, null, null, null, null, null);
        String res="";
        int iPla=c.getColumnIndex(CODPLACA);
        int iFec=c.getColumnIndex(CODFECHA);
        int iHor=c.getColumnIndex(CODHORA);
        int itipo=c.getColumnIndex(CODTIPO);


        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            res=res+c.getString(iPla)+"   "+
                    c.getString(iFec)+"   "+
                    c.getString(iHor)+"   "+
                    c.getString(itipo)+"\n";
        }
        return res;
    }


    public String[] buscar(String buscar){
        String[] datos=new String[5];
        SQLiteDatabase bd=Control.getWritableDatabase();
        String q="SELECT * FROM vehiculos WHERE placa='"+buscar+"'";
        Cursor registros=pBD.rawQuery(q,null);
        if (registros.moveToFirst()){
            for (int i=0;i<4;i++){
                datos[i]=registros.getString(i);
            }
            datos[4]="Encontrado";
        }else{
            datos[4]="No se encontro a "+buscar;
        }
        return datos;
    }

    public void actualizar(String splaca, String sfecha, String shora, String stipo){
        SQLiteDatabase bd=Control.getWritableDatabase();
        if(bd!=null){
            bd.execSQL("UPDATE vehiculos SET placa='"+splaca+"', fecha='"+sfecha+"', hora='"+shora+"', tipo='"+stipo+"' WHERE placa='"+splaca+"'");
            cerrar();
        }
    }

    public void Eliminar(){
        SQLiteDatabase bd=Control.getWritableDatabase();
        if(bd!=null){
            bd.execSQL("DELETE FROM vehiculos WHERE placa='"+CODPLACA+"'");
            cerrar();
        }
    }
    public long InsertarP(String qpla,String qtcobro,String qprecio){
        ContentValues cv=new ContentValues();
        cv.put(CODPLACA, qpla);
        cv.put(CODTCOBRO, qtcobro);
        cv.put(CODPRECIO, qprecio);
        return pBD.insert(NOMBRE_TABLA, null, cv);

    }

    public String ListarP(){
        String[] columnas =new String[]{CODPLACA,CODTCOBRO,CODPRECIO};
        Cursor c=pBD.query(NOMBRE_TABLA, columnas, null, null, null, null, null);
        String res="";
        int iPla=c.getColumnIndex(CODPLACA);
        int iTco=c.getColumnIndex(CODTCOBRO);
        int iPre=c.getColumnIndex(CODPRECIO);

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            res=res+c.getString(iPla)+"   "+
                    c.getString(iTco)+"   "+
                    c.getString(iPre)+"\n";
        }
        return res;
    }


    public String[] buscarP(String buscar){
        String[] datos=new String[4];
        SQLiteDatabase bd=Control.getWritableDatabase();
        String q="SELECT * FROM parqueo WHERE placa='"+buscar+"'";
        Cursor registros=pBD.rawQuery(q,null);
        if (registros.moveToFirst()){
            for (int i=0;i<3;i++){
                datos[i]=registros.getString(i);
            }
            datos[3]="Encontrado";
        }else{
            datos[3]="No se encontro a "+buscar;
        }
        return datos;
    }

    public String[] buscarTarifacobrar(String buscar){
        String[] datos=new String[4];
        SQLiteDatabase bd=Control.getWritableDatabase();
        String q="SELECT precio FROM parqueo WHERE placa='"+buscar+"'";
        Cursor registros=pBD.rawQuery(q,null);
        if (registros.moveToFirst()){
            for (int i=0;i<2;i++){
                datos[i]=registros.getString(i);
            }
            datos[3]="Encontrado";
        }else{
            datos[3]="No se encontro a "+buscar;
        }
        return datos;
    }

    public void actualizarP(String splaca, String stcobro, String sprecio){
        SQLiteDatabase bd=Control.getWritableDatabase();
        if(bd!=null){
            bd.execSQL("UPDATE parqueo SET placa='"+splaca+"', tcobro='"+stcobro+"', precio='"+sprecio+"' WHERE placa='"+splaca+"'");
            cerrar();
        }
    }

    public void EliminarP(){
        SQLiteDatabase bd=Control.getWritableDatabase();
        if(bd!=null){
            bd.execSQL("DELETE FROM vehiculos WHERE placa='"+CODPLACA+"'");
            cerrar();
        }
    }

    public long InsertarT(String qtcobro,String qpre1,String qpre2){
        ContentValues cv=new ContentValues();
        cv.put(CODTCOBRO, qtcobro);
        cv.put(CODPRE1, qpre1);
        cv.put(CODPRE2, qpre2);
        return pBD.insert(NOM_TABLA, null, cv);

    }

    public String ListarT(){
        String[] columnas =new String[]{CODTCOBRO,CODPRE1,CODPRE2};
        Cursor c=pBD.query(NOM_TABLA, columnas, null, null, null, null, null);
        String res="";
        int itco=c.getColumnIndex(CODTCOBRO);
        int ipre1=c.getColumnIndex(CODPRE1);
        int ipre2=c.getColumnIndex(CODPRE2);

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            res=res+c.getString(itco)+"   "+
                    c.getString(ipre1)+"   "+
                    c.getString(ipre2)+"\n";
        }
        return res;
    }


    public String[] buscarT(String buscar){
        String[] datos=new String[4];
        SQLiteDatabase bd=Control.getWritableDatabase();
        String q="SELECT * FROM tarifa WHERE tcobro='"+buscar+"'";
        Cursor registros=pBD.rawQuery(q,null);
        if (registros.moveToFirst()){
            for (int i=0;i<3;i++){
                datos[i]=registros.getString(i);
            }
            datos[3]="Encontrado";
        }else{
            datos[3]="No se encontro a "+buscar;
        }
        return datos;
    }

    public void actualizarT(String stcobro, String spre1, String spre2){
        SQLiteDatabase bd=Control.getWritableDatabase();
        if(bd!=null){
            bd.execSQL("UPDATE tarifa SET tcobro='"+stcobro+"', precio1='"+spre1+"', precio2='"+spre2+"' WHERE tcobro='"+stcobro+"'");
            cerrar();
        }
    }

    public void EliminarT(){
        SQLiteDatabase bd=Control.getWritableDatabase();
        if(bd!=null){
            bd.execSQL("DELETE FROM tarifa WHERE tcobro='"+CODTCOBRO+"'");
            cerrar();
        }
    }

}

