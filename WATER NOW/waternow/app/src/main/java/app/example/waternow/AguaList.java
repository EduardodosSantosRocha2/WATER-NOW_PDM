package app.example.waternow;

public class AguaList {


    private String Cod;
    private String Modelo;
    private String Marca;



    public AguaList(String c, String mo, String ma){
        this.Cod= c;
        this.Modelo= mo;
        this.Marca= ma;

    }

    public String getCod() {
        return Cod;
    }
    public String getModelo() {
        return Modelo;
    }

    public String getMarca() {
        return Marca;
    }

}
