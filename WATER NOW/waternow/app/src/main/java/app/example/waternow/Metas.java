package app.example.waternow;

import androidx.appcompat.app.AppCompatActivity;

import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import app.example.waternow.R;
import app.example.waternow.objeto.Agua;
import app.example.waternow.objeto.Usuario;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Metas extends AppCompatActivity {
    float Peso;
    Date Data;
    DrawerLayout drawerLayout;
    TextView consumido, naoconsumido;
    PieChart pieChart;

    private Usuario usuarioAtual;
    private Agua usuarioAtualAgua;
    private FirebaseFirestore db;
    private DateFormat df = DateFormat.getDateTimeInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas);
        drawerLayout = findViewById(R.id.metas1);
        db = FirebaseFirestore.getInstance();

        DocumentReference usuarioDB = db.collection(Usuario.END_FIREBASE).document(FirebaseAuth.getInstance().getUid());
        usuarioDB.get().addOnCompleteListener((a) -> {
            if (a.isSuccessful()) {
                DocumentSnapshot d = a.getResult();
                if (d.exists()) {
                    usuarioAtual = d.toObject(Usuario.class);
                    usuarioAtualAgua = d.toObject(Agua.class);

                    Peso = usuarioAtual.getPeso();
                    List<Agua> registrosAgua = usuarioAtual.getAgua();


                    consumido = findViewById(R.id.C);
                    naoconsumido = findViewById(R.id.NC);
                    pieChart = findViewById(R.id.piechart);
                    setData(Peso, registrosAgua);
                }
            }
        });



    }
    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }
    public  void Clickhome(View view){
        MainActivity.redirectActivity(this,MainActivity.class);
    }
    public  void clickDashboard(View view){
        recreate();
    }
    public void clickAboutUs(View view){
        MainActivity.redirectActivity(this,AboutUs.class);
    }
    public  void ClickLogout(View view){
        MainActivity.logout(this);
    }

    public void Conta1(View view){
        MainActivity.redirectActivity(this,Conta.class);
    }
    public void Relatorio1(View view){MainActivity.redirectActivity(this,Relatorio.class);}
    public void Metas1(View view){
        MainActivity.redirectActivity(this,Metas.class);
    }

    public void Configuracoes1(View view){MainActivity.redirectActivity(this,Configuracoes.class);}
    public void Notification1(View view) {
        MainActivity.redirectActivity(this, Notificacao.class);
    }





    private void setData(float peso, List<Agua> registrosAgua)
    {
        LocalDate dataAtual;
        String dataFormatadaSistema = "";

        float somaQuantidade = 0;
        //
        TextView dataText = (TextView) findViewById(R.id.date);
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dataAtual = LocalDate.now();

            DateTimeFormatter formatoDataSistema = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataFormatadaSistema = dataAtual.format(formatoDataSistema);
        }

        //
        for (Agua a : registrosAgua) {
            String dataFormatadaUser = formatoData.format(a.getData());
            if(dataFormatadaSistema.equals(dataFormatadaUser)){
                somaQuantidade += a.getQuantidade();
                dataText.setText("dataUser:"+dataFormatadaUser+"\ndataSistema"+dataFormatadaSistema+"\niguais:");
            }

            //dataText.setText(dataFormatada);
        }

        // Set the percentage of language used


        consumido.setText(Float.toString(somaQuantidade));

        naoconsumido.setText(Float.toString( peso* 35));


        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Consumido",
                        Float.parseFloat(consumido.getText().toString()),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Meta de consumo",
                        Float.parseFloat(naoconsumido.getText().toString()),
                        Color.parseColor("#66BB6A")));

        // To animate the pie chart
        pieChart.startAnimation();
    }






    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }



}