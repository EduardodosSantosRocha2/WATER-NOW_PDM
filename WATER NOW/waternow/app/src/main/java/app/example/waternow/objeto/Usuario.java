package app.example.waternow.objeto;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    public static final String END_FIREBASE = "pessoa";

    private List<Agua> agua = new ArrayList<>();
    public float peso;
    public float altura;
    public String sexo;
    public String id;
    public String nome;

    public Usuario() { }

    public List<Agua> getAgua() {
        return agua;
    }

    public void addAgua(Agua a) {
        a.setIdUsuario(id);
        agua.add(a);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario(float peso, float altura, String sexo, String id,String nome) {
        super();
        this.peso = peso;
        this.altura = altura;
        this.sexo = sexo;
        this.id = id;
        this.nome = nome;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}