package com.myapp.updatedbforportalproductionsystem.statisticsPPU;

public class MyData {

    private String Zaregistrirovano;
    private String Soglasovano;
    private String Vnedreno;

    public MyData() {
    }

    public MyData(String zaregistrirovano, String soglasovano, String vnedreno) {
        Zaregistrirovano = zaregistrirovano;
        Soglasovano = soglasovano;
        Vnedreno = vnedreno;
    }

    public String getZaregistrirovano() {
        return Zaregistrirovano;
    }

    public void setZaregistrirovano(String zaregistrirovano) {
        Zaregistrirovano = zaregistrirovano;
    }

    public String getSoglasovano() {
        return Soglasovano;
    }

    public void setSoglasovano(String soglasovano) {
        Soglasovano = soglasovano;
    }

    public String getVnedreno() {
        return Vnedreno;
    }

    public void setVnedreno(String vnedreno) {
        Vnedreno = vnedreno;
    }
}
