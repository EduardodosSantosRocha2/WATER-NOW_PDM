package app.example.waternow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import app.example.waternow.R;

public class Configuracoes extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        drawerLayout = findViewById(R.id.configuracoes1);
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