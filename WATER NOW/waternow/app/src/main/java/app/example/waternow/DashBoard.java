package app.example.waternow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;


import java.text.DateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.example.waternow.objeto.Agua;
import app.example.waternow.objeto.Usuario;

public class DashBoard extends AppCompatActivity {

    private AguaAdapter aguaAdapter;
    public ArrayList<AguaList> listaAgua = new ArrayList<>();
    DrawerLayout drawerLayout;
    private Usuario usuarioAtual;
    private FirebaseFirestore db;
    private DateFormat df = DateFormat.getDateTimeInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        drawerLayout = findViewById(R.id.drawer_layout);
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
        ((Button) findViewById(R.id.btnAddAgua)).setOnClickListener((e) -> AdicionarAgua());
    }

    private void PreencherLista() {
        ((LinearLayout) findViewById(R.id.layoutListaAgua)).removeAllViews();
        for (Agua a: usuarioAtual.getAgua()) {

            String item1 = df.format(a.getData());
            String item2 = Float.toString(a.getQuantidade());
            String item3 = Float.toString(usuarioAtual.getPeso());
            AguaList p1 = new AguaList(item1,item2,item3);
            listaAgua.add(p1);
//            TextView teste = new TextView(this);
//            teste.setText(String.format("Agua: %s \t Qtd: %.2f \t Peso: %.2f ", df.format(a.getData()), a.getQuantidade(),usuarioAtual.getPeso()));
//            ((LinearLayout) findViewById(R.id.layoutListaAgua)).addView(teste);

        }

        ListView listView1 = findViewById(R.id.listView01);
        aguaAdapter = new AguaAdapter(DashBoard.this,listaAgua);
        listView1.setAdapter(aguaAdapter);
    }

    private void AdicionarAgua() {
        usuarioAtual.addAgua(new Agua(new Date(), Float.parseFloat(((EditText) findViewById(R.id.editQtdAgua)).getText().toString())));
        db.collection(Usuario.END_FIREBASE)
                .document(usuarioAtual.id)
                .set(usuarioAtual)
                .addOnSuccessListener(dr -> ((EditText) findViewById(R.id.editQtdAgua)).setText("0"))
                .addOnFailureListener(err -> Toast.makeText(this, "Erro na adição de agua.",
                        Toast.LENGTH_SHORT).show());
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

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }

    public void redirecionar(View view) {
        Intent intent = new Intent(this, CriarContaParte2.class);
        startActivity(intent);
    }
}