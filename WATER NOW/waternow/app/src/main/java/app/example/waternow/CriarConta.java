package app.example.waternow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CriarConta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);
        ((Button) findViewById(R.id.btnLogin)).setOnClickListener((e) -> RegistrarUsuario());
        ((Button) findViewById(R.id.btnCCL)).setOnClickListener((e) -> MainActivity.redirectActivity(this, MainActivity.class));
    }

    private void RegistrarUsuario() {
        String userEmail = ((EditText) findViewById(R.id.inputEmail)).getText().toString();
        String pass = ((EditText) findViewById(R.id.inputSenha)).getText().toString();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(userEmail, pass).addOnCompleteListener(this,
                task -> {
                    if (task.isSuccessful()) {
                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Log.i("registro", userID + " registro OK");
                        // pegar mais informações sobre o usuario (peso, altura, etc...) e usar essa userID para enviar pro outro banco

                        MainActivity.redirectActivity(this, CriarContaParte2.class);
                    } else {
                        String msgErro = task.getException().getMessage();
                        if (msgErro.contains("6"))
                            ((TextView) findViewById(R.id.textErro)).setText(getString(R.string.erro_senha));
                        else if (msgErro.contains("email"))
                            ((TextView) findViewById(R.id.textErro)).setText(getString(R.string.erro_email));
                        else
                            ((TextView) findViewById(R.id.textErro)).setText(getString(R.string.erro_outro));

                        Toast.makeText(CriarConta.this, "Erro na criação da conta.",
                                Toast.LENGTH_SHORT).show();

                        // mostrar mensagem de erro
                    }
                }
        );
    }
}