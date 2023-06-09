package app.example.waternow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import app.example.waternow.objeto.Usuario;

public class Conta extends AppCompatActivity {

    DrawerLayout drawerLayout;
    FirebaseFirestore db;
    private Usuario usuarioLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        drawerLayout = findViewById(R.id.conta1);
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            MainActivity.redirectActivity(this, MainActivity.class);
        } else {
            Uri photoURL = user.getPhotoUrl();
            if (photoURL != null)
                Glide.with(this).load(photoURL).into((ImageView) findViewById(R.id.imgUserFoto));

            String nome = user.getDisplayName();
            //((TextView) findViewById(R.id.textUserName)).setText(getString(R.string.textUserName, nome == null ? "" : nome));
            ((TextView) findViewById(R.id.textUserEmail)).setText(getString(R.string.textUserEmail, user.getEmail()));
            db.collection(Usuario.END_FIREBASE).document(user.getUid()).get().addOnCompleteListener((e) -> {
                if (e.isSuccessful()) {
                    usuarioLocal = e.getResult().toObject(Usuario.class);
                    PreencherView();
                }
            });
        }

        ((Button) findViewById(R.id.btnEdit)).setOnClickListener((f) -> {
            PreencherEdit();
            TrocarCampos();
        });

        ((Button) findViewById(R.id.btnEditCCL)).setOnClickListener((f) -> {
            TrocarCampos();
        });

        ((Button) findViewById(R.id.btnEditOK)).setOnClickListener((f) -> {
            ConfirmarEdit();
            TrocarCampos();
            PreencherView();
        });
    }

    private void PreencherView() {
        ((TextView) findViewById(R.id.textUserNOME)).setText(getString(R.string.textUserNAMEUsuario, usuarioLocal.nome));
        ((TextView) findViewById(R.id.textUserAltura)).setText(getString(R.string.textUserAltura, usuarioLocal.altura));
        ((TextView) findViewById(R.id.textUserPeso)).setText(getString(R.string.textUserPeso, usuarioLocal.peso));
        ((TextView) findViewById(R.id.textUserSexo)).setText(getString(R.string.textUserSexo, usuarioLocal.sexo));
    }

    private void TrocarCampos() {
        if (findViewById(R.id.editaInfo).getVisibility() == View.VISIBLE) {
            findViewById(R.id.mostraInfo).setVisibility(View.VISIBLE);
            findViewById(R.id.editaInfo).setVisibility(View.GONE);
        } else {
            findViewById(R.id.mostraInfo).setVisibility(View.GONE);
            findViewById(R.id.editaInfo).setVisibility(View.VISIBLE);
        }
    }

    private void PreencherEdit() {
        ((EditText) findViewById(R.id.edtPeso)).setText(Float.toString(usuarioLocal.peso));
        ((EditText) findViewById(R.id.edtAltura)).setText(Float.toString(usuarioLocal.altura));
        if (usuarioLocal.getSexo().contains("feminino"))
            ((RadioButton) findViewById(R.id.rdFeminino)).setChecked(true);
        else
            ((RadioButton) findViewById(R.id.rdMasculino)).setChecked(true);
    }

    private void ConfirmarEdit() {
        Float novoPeso = Float.parseFloat(((EditText) findViewById(R.id.edtPeso)).getText().toString());
        Float novaAltura = Float.parseFloat(((EditText) findViewById(R.id.edtAltura)).getText().toString());
        String novoSexo = ((RadioButton) findViewById(R.id.rdFeminino)).isChecked() ? "feminino" : "masculino";
        usuarioLocal.setPeso(novoPeso);
        usuarioLocal.setAltura(novaAltura);
        usuarioLocal.setSexo(novoSexo);
        db.collection(Usuario.END_FIREBASE).document(usuarioLocal.getId()).set(usuarioLocal);
    }

//
//    public void Registrar(View view) {
//        Intent intent = new Intent(this, CriarContaParte2.class);
//        startActivity(intent);
//    }

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