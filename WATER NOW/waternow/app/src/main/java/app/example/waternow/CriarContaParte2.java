package app.example.waternow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import app.example.waternow.objeto.Usuario;

public class CriarContaParte2 extends AppCompatActivity {
    private String sexo;
    private EditText epeso;
    private EditText ealtura;
    public Button button;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrousuario);
        drawerLayout=findViewById(R.id.cadastro2);
    }
    public void radio(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){
            case R.id.feminino:
                if(checked)
                    sexo = "feminino";
                break;
            case R.id.masculino:
                if(checked)
                    sexo = "masculino";
                break;
        }
    }
    public void getViews(){
        epeso = (EditText) findViewById(R.id.Peso);
        ealtura = (EditText) findViewById(R.id.Altura);
        button = (Button) findViewById(R.id.button);
    }



    public void RegistrarUsuario(View view) {
        getViews();
        String peso = epeso.getText().toString();
        String altura = ealtura.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();

        Usuario u = new Usuario(Float.parseFloat(peso),Float.parseFloat(altura),sexo,id);
        db.collection("pessoa")
                .document(id)
                .set(u);
        MainActivity.redirectActivity(this, DashBoard.class);
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

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}
