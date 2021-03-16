package com.example.comgigaparqueoapp;

public class vehiculoModelo {
    private String cplaca, cfecha, chora;
    public vehiculoModelo(){

    }



    public vehiculoModelo(String cplaca, String cfecha, String chora){
        this.cplaca=cplaca;
        this.cfecha=cfecha;
        this.chora=chora;
    }

    public String getCplaca() {
        return cplaca;
    }

    public String getCfecha() {
        return cfecha;
    }

    public String getChora() {
        return chora;
    }

    public void setCplaca(String cplaca) {
        this.cplaca = cplaca;
    }

    public void setCfecha(String cfecha) {
        this.cfecha = cfecha;
    }

    public void setChora(String chora) {
        this.chora = chora;
    }
}
