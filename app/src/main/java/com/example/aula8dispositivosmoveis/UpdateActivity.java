package com.example.aula8dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    private TextView textID;
    private EditText textAno, textModelo, textValor;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        textID = findViewById(R.id.textID);
        textModelo = findViewById(R.id.textModelo);
        textAno = findViewById(R.id.textAno);
        textValor = findViewById(R.id.textValor);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        textID.setText(intent.getStringExtra("id"));
        textModelo.setText(intent.getStringExtra("modelo"));
        textAno.setText(intent.getStringExtra("ano"));
        textValor.setText(intent.getStringExtra("valor"));


    }

    public void clickAtualizar(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String[] where = new String[]{textID.getText().toString()};

        ContentValues cv = new ContentValues();
        cv.put("modelo", textModelo.getText().toString());
        cv.put("ano", textAno.getText().toString());
        cv.put("valor", textValor.getText().toString());

        long res = db.update("carro", cv,"id = ?", where);
        if(res != -1){
            Toast.makeText(this, "Alterado com sucesso!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Não foi possível alterar.", Toast.LENGTH_SHORT).show();
        }

    }

    public void clickExcluir(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String[] where = new String[]{textID.getText().toString()};

        long res = db.delete("carro", "id = ?", where);
        if(res != -1){
            Toast.makeText(this, "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Não foi possível excluir.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }
}