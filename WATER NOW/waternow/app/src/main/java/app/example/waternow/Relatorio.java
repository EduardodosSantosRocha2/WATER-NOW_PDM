package app.example.waternow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import app.example.waternow.R;
import app.example.waternow.objeto.Agua;
import app.example.waternow.objeto.Usuario;


public class Relatorio extends AppCompatActivity {
    private AguaAdapter aguaAdapter;
    public ArrayList<AguaList> listaAgua = new ArrayList<>();
    DrawerLayout drawerLayout;
    private Usuario usuarioAtual;
    private Agua usuarioAtualAgua;
    private FirebaseFirestore db;
    private DateFormat df = DateFormat.getDateTimeInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        drawerLayout = findViewById(R.id.relatorio1);

        db = FirebaseFirestore.getInstance();

        DocumentReference usuarioDB = db.collection(Usuario.END_FIREBASE).document(FirebaseAuth.getInstance().getUid());
        usuarioDB.get().addOnCompleteListener((a) -> {
            if (a.isSuccessful()) {
                DocumentSnapshot d = a.getResult();
                if (d.exists()) {
                    usuarioAtual = d.toObject(Usuario.class);
                    usuarioDB.addSnapshotListener((info, err) -> PreencherLista());
                }
            }
        });
    }

    public void ClickMenu(View view) {
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        MainActivity.closeDrawer(drawerLayout);
    }

    public void Clickhome(View view) {
        MainActivity.redirectActivity(this, MainActivity.class);
    }

    public void clickDashboard(View view) {
        recreate();
    }

    public void clickAboutUs(View view) {
        MainActivity.redirectActivity(this, AboutUs.class);
    }

    public void ClickLogout(View view) {
        MainActivity.logout(this);
    }

    public void Conta1(View view) {
        MainActivity.redirectActivity(this, Conta.class);
    }

    public void Relatorio1(View view) {
        MainActivity.redirectActivity(this, Relatorio.class);
    }

    public void Metas1(View view) {
        MainActivity.redirectActivity(this, Metas.class);
    }

    public void Configuracoes1(View view) {
        MainActivity.redirectActivity(this, Configuracoes.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }


    private void PreencherLista() {
        //((LinearLayout) findViewById(R.id.layoutListaAguaRelatorio)).removeAllViews();


        LocalDate dataAtual;
        String dataFormatadaSistema = "";

        //
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dataAtual = LocalDate.now();

            DateTimeFormatter formatoDataSistema = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataFormatadaSistema = dataAtual.format(formatoDataSistema);
        }

        for (Agua a : usuarioAtual.getAgua()) {
            String dataFormatadaUser = formatoData.format(a.getData());

            if (dataFormatadaSistema.equals(dataFormatadaUser)) {
                String item1 = df.format(a.getData());
                String item2 = Float.toString(a.getQuantidade());
                String item3 = Float.toString(usuarioAtual.getPeso());

                AguaList p1 = new AguaList(item1,item2,item3);
                listaAgua.add(p1);

                //TextView teste = new TextView(this);
                //teste.setText(String.format("Agua: %s \t Qtd: %.2f \t Peso: %.2f ", df.format(a.getData()), a.getQuantidade(), usuarioAtual.getPeso()));

                //((LinearLayout) findViewById(R.id.layoutListaAguaRelatorio)).addView(teste);
            }
        }
        ListView listView1 = findViewById(R.id.listView01);
        aguaAdapter = new AguaAdapter(Relatorio.this,listaAgua);
        listView1.setAdapter(aguaAdapter);
    }
}