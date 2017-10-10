package co.edu.ucc.ejercicio2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.edu.ucc.ejercicio2.entidades.Ntareas;

public class RegistrarActivity extends AppCompatActivity {
    @BindView(R.id.REmail)
    EditText REmail;
    @BindView(R.id.RNombre)
    EditText RNombre;
    @BindView(R.id.RPassword)
    EditText RPassword;
    @BindView(R.id.RUsuario)
    EditText RUsuario;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }
    @OnClick (R.id.RRegistrar)
    public void Registrar(){
        final String email=REmail.getText().toString();
        String password=RPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Ntareas numero = new Ntareas();
                            int id=0;
                            numero.setValor(id);
                            myRef = database.getReference("Ntareas");
                            myRef.child(email.replace(".",",")).setValue(numero);
                            Intent intent1 = new Intent(RegistrarActivity.this, LoginActivity.class);
                            intent1.putExtra("email", email);
                            startActivity(intent1);
                            finish();
                        }
                        else{
                            Toast.makeText(RegistrarActivity.this, "Error " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
}
