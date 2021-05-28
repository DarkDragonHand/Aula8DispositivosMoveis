package com.example.aula8dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtUsuario, txtSenha;      //activity_main
    EditText txtNovoUsuario, txtNovaSenha, txtNovaSenha2; //cadastro_aula_8
    TextView txtSessao; //home_aula_8

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewTelaLogin();
    }

    private void setViewTelaLogin(){
        setContentView(R.layout.activity_main);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtSenha = findViewById(R.id.txtSenha);
    }

    private void setViewTelaCadastro(){
        setContentView(R.layout.cadastro_aula_8);

        txtNovoUsuario = findViewById(R.id.txtNovoUsuario);
        txtNovaSenha = findViewById(R.id.txtNovaSenha);
        txtNovaSenha2 = findViewById(R.id.txtNovaSenha2);
    }

    private void setViewTelaLogado(){
        setContentView(R.layout.home_aula_8);

        txtSessao = findViewById(R.id.txtSessao);
    }

    public void clickLogin(View view) {
        String usuario = txtUsuario.getText().toString();
        String senha = txtSenha.getText().toString();

        if (usuario.isEmpty() || senha.isEmpty()) {
            messageBox("O campo de usuário ou senha estão vazios, favor preenche-los");
            return;
        }

        SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String usuarioSettings = settings.getString("usuario", "");
        String senhaSettings = settings.getString("senha","");
        int sessao = settings.getInt("sessao", 0);

        if (usuario.equals(usuarioSettings) && senha.equals(senhaSettings)){
            messageBox("Login efetuado com sucesso!");
            setViewTelaLogado();
            sessao++;
            txtSessao.setText("Sessão #" + sessao);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("sessao", sessao);
            editor.commit();
            editor.apply();
        }
        else
        {
            messageBox("Usuário ou senha incorretos.");
            return;
        }
    }

    public void CadastroClick(View view) {
        setViewTelaCadastro();
    }

    public void CadastrarClick(View view) {
        String usuario = txtNovoUsuario.getText().toString();
        String senha = txtNovaSenha.getText().toString();
        String senha2 = txtNovaSenha2.getText().toString();

        if (usuario.isEmpty() || senha.isEmpty()){
            messageBox("O campo de usuário ou senha estão vazios, favor preenche-los");
            return;
        }

        if (!senha.equals(senha2)) { //Se a senha não for igual a senha repetida no cadastro
            messageBox("A senha precisa ser igual a senha repetida.");
            return;
        }

        SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("usuario", usuario);
        editor.putString("senha", senha);
        editor.commit();
        editor.apply();
        messageBox("Cadastro realizado com sucesso!");
        setViewTelaLogin();
    }


    public void clickLogoff(View view) {
        SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("usuario", "");
        editor.putString("senha", "");
        editor.putInt("sessao", 0);
        editor.commit();
        editor.apply();
        messageBox("Logout feito com sucesso!");
        setViewTelaLogin();
    }

    public void clickVoltar(View view) {
        setViewTelaLogin();
    }

    private void messageBox(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }


}