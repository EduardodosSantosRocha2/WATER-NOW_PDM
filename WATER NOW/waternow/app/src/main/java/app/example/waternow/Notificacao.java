package app.example.waternow;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;



public class Notificacao extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_id";
    private static final String CHANNEL_NAME = "channel_name";
    private static final String CHANNEL_DESCRIPTION = "channel_description";


    DrawerLayout drawerLayout;

    private static final int REQUEST_CODE_SET_ALARM = 1;

    private Button btnOpenClock;
    private EditText etHours;
    private EditText etMinutes;
    private String alarmLabel = "Meu Alarme"; // Rótulo do alarme



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);
        drawerLayout = findViewById(R.id.notification1);


        btnOpenClock = findViewById(R.id.btnOpenClock);
        etHours = findViewById(R.id.horas1);
        etMinutes = findViewById(R.id.minuto2);

        btnOpenClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClockApp();
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

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }



    private void openClockApp() {
        String clockAppPackage = "com.android.deskclock"; // Substitua pelo pacote correto do aplicativo do relógio no dispositivo

        int hour = Integer.parseInt(etHours.getText().toString());
        int minutes = Integer.parseInt(etMinutes.getText().toString());

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.setPackage(clockAppPackage);
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, alarmLabel);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);

        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_CODE_SET_ALARM);
        } else {
            // O aplicativo do relógio não está instalado no dispositivo
            // Você pode lidar com isso conforme necessário
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SET_ALARM) {
            // O alarme foi configurado automaticamente
            // Faça o que você precisa aqui, por exemplo, retorne à tela principal
            // Por exemplo, descomente a linha abaixo para fechar a MainActivity após configurar o alarme
            // finish();
        }
    }





}



