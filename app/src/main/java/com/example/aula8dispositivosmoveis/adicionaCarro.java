package com.example.aula8dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

public class adicionaCarro extends AppCompatActivity {

    private EditText modelo, ano, valor;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_carro);

        modelo = findViewById(R.id.textAdicionaModelo);
        ano = findViewById(R.id.textAdicionaAno);
        valor = findViewById(R.id.textAdicionaValor);

        databaseHelper = new DatabaseHelper(this);

    }

    public void clickSalvar(View view) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("modelo", modelo.getText().toString());
        cv.put("ano", Integer.parseInt(ano.getText().toString())) ;
        cv.put("valor", Double.parseDouble(valor.getText().toString()));

        long res = db.insert("carro", null, cv);
        if(res != -1){
            Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
            modelo.setText("");
            ano.setText("");
            valor.setText("");
        }
        else
        {
            Toast.makeText(this, "Ocorreu um erro, não foi possível salvar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }

}