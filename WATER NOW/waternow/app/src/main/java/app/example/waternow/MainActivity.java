package app.example.waternow;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

import app.example.waternow.objeto.Usuario;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private FirebaseFirestore db;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new FirebaseAuthUIActivityResultContract(), result -> onSignInResult(result));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer);
        db = FirebaseFirestore.getInstance();

        ((Button) findViewById(R.id.btnLoginGoogle)).setOnClickListener(e -> {
            List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build());
            Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build();
            signInLauncher.launch(signInIntent);
        });

        ((Button) findViewById(R.id.btnCriarConta)).setOnClickListener(e -> {
            redirectActivity(this, CriarConta.class);
        });

        ((Button) findViewById(R.id.btnLogin)).setOnClickListener(e -> {
            String userEmail = ((EditText) findViewById(R.id.inputEmail)).getText().toString();
            String userPass = ((EditText) findViewById(R.id.inputSenha)).getText().toString();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(this,
            task -> {
                if (task.isSuccessful()) {
                    redirectActivity(this, DashBoard.class);
                } else {
                    Toast.makeText(MainActivity.this, "Erro no login.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            redirectActivity(this, DashBoard.class);
        }
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            db.collection(Usuario.END_FIREBASE).document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener((e) -> {
                if (e.isSuccessful())
                {
                    if (e.getResult().exists())
                        redirectActivity(this, DashBoard.class);
                    else
                        redirectActivity(this, CriarContaParte2.class);
                }
            });
        } else {
            Log.i("user", "erro no login");
        }
    }

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void Clickhome(View view) {
        recreate();
    }

    public void clickDashboard(View view) {
        redirectActivity(this, DashBoard.class);
    }

    public void Notification1(View view) {
        redirectActivity(this, Notificacao.class);
    }

    public void Conta1(View view) {
        redirectActivity(this, Conta.class);
    }

    public void Relatorio1(View view) {
        redirectActivity(this, Relatorio.class);
    }

    public void Metas1(View view) {
        redirectActivity(this, Metas.class);
    }

    public void Configuracoes1(View view) {
        redirectActivity(this, Configuracoes.class);
    }

    public void clickAboutUs(View view) {
        redirectActivity(this, AboutUs.class);
    }

    public void ClickLogout(View view) {
        logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Sair");
        builder.setMessage(activity.getString(R.string.mensagem_logout));
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                redirectActivity(activity, MainActivity.class);
//                System.exit(0);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class Class) {
        Intent intent = new Intent(activity, Class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}