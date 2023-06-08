package app.example.waternow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.net.URL;

import android.content.Intent;

import app.example.waternow.R;
import app.example.waternow.objeto.Usuario;

public class Conta extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        drawerLayout = findViewById(R.id.conta1);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            MainActivity.redirectActivity(this, MainActivity.class);
        } else {
            Uri photoURL = user.getPhotoUrl();
            if (photoURL != null)
                ((ImageView) findViewById(R.id.imgUserFoto)).setImageDrawable(carregarFotoUsuario(user.getPhotoUrl().toString()));

            ((TextView) findViewById(R.id.textUserName)).setText(getString(R.string.textUserName, user.getDisplayName()));
            ((TextView) findViewById(R.id.textUserEmail)).setText(getString(R.string.textUserEmail, user.getEmail()));
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("pessoa").document(user.getUid()).get().addOnCompleteListener((e) -> {
                if (e.isSuccessful()) {
                    Usuario u = e.getResult().toObject(Usuario.class);
                    ((TextView) findViewById(R.id.textUserAltura)).setText(getString(R.string.textUserAltura, u.altura));
                    ((TextView) findViewById(R.id.textUserPeso)).setText(getString(R.string.textUserPeso, u.peso));
                    ((TextView) findViewById(R.id.textUserSexo)).setText(getString(R.string.textUserSexo, u.sexo));
                }
            });
        }
    }

    private static Drawable carregarFotoUsuario(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "photo");
            return d;
        } catch (Exception e) {
            Log.e("erro foto", e.getMessage());
            return null;
        }
    }

    public void Registrar(View view) {
        Intent intent = new Intent(this, CriarContaParte2.class);
        startActivity(intent);
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
}