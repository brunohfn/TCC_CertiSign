package br.com.unipac.certsign_tcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.unipac.certsign_tcc.Config.ConfiguracaoFirebase;
import br.com.unipac.certsign_tcc.Model.Usuario;

public class MainActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;


    private Usuario usuario;


    private TextView buttonCadastro;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        campoEmail = findViewById(R.id.LoginEmail);
        campoSenha = findViewById(R.id.LoginSenha);


        this.buttonCadastro= findViewById(R.id.buttonCadastro);
        this.buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cadastro.class);
                startActivity(intent);
            }
        });


    }

    public void logarUsuario(Usuario usuario){

        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful() ){

                    abrirTelaInicio();

                }else{
                    Toast.makeText(MainActivity.this, "O endereço de email ou a senha que você inseriu não é válido!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void validarAutenticacaoUsuario(View view){

        //Recuperar os dados do campo
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        //Validar se email e senha foram digitados
        if( !textoEmail.isEmpty()){
            if( !textoSenha.isEmpty()){

                Usuario usuario = new Usuario();
                usuario.setEmail( textoEmail );
                usuario.setSenha( textoSenha );

                logarUsuario( usuario );

            }else{
                Toast.makeText(MainActivity.this, "Preencha a Senha!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "Preencha o Email!", Toast.LENGTH_SHORT).show();
        }

    }

    public void abrirTelaInicio(){
        Intent intent = new Intent(MainActivity.this, Inicio.class);
        startActivity( intent );
    }




}