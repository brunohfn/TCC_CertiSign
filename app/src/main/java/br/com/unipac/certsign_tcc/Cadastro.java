package br.com.unipac.certsign_tcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.unipac.certsign_tcc.Config.ConfiguracaoFirebase;
import br.com.unipac.certsign_tcc.Model.Usuario;

public class Cadastro extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoNumMatricula, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.CadastroNomeCompleto);
        campoEmail = findViewById(R.id.CadastroEmail);
        campoNumMatricula = findViewById(R.id.CadastroNuMatricula);
        campoSenha = findViewById(R.id.CadastroSenha);

    }

    public void validarCadastroUsuario(View view){

        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoNumMatricula = campoNumMatricula.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if(!textoNome.isEmpty()){
            if(!textoEmail.isEmpty()){
                if(!textoNumMatricula.isEmpty()){
                    if(!textoSenha.isEmpty()){

                        Usuario usuario = new Usuario();
                        usuario.setNome(textoNome);
                        usuario.setEmail(textoEmail);
                        usuario.setNumMatricula(textoNumMatricula);
                        usuario.setSenha(textoSenha);

                        cadastrarUsuario (usuario);

                    }else{
                        Toast.makeText(Cadastro.this, "Preencha a Senha!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Cadastro.this, "Preencha o Numero de Matricula!", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(Cadastro.this, "Preencha o Email!", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(Cadastro.this, "Preencha o nome!", Toast.LENGTH_SHORT).show();
        }

    }

    public void cadastrarUsuario( Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if  ( task.isSuccessful() ){
                    Toast.makeText(Cadastro.this, "Sucesso ao cadastrar Usuario!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}