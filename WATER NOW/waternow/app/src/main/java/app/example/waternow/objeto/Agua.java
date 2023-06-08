package app.example.waternow.objeto;
import java.util.Date;

public class Agua {
    private String idUsuario;
    private Date data;
    private Float quantidade;

    public Agua() {
    }

    public Agua(Date data, Float agua) {
        this.data = data;
        this.quantidade = agua;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }
}