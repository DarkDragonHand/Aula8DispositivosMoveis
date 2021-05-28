package com.example.aula8dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Revenda_De_Carros extends AppCompatActivity {

    private EditText ano, modelo;
    private ListView listaCarros;
    private DatabaseHelper databaseHelper;
    List<Map<String, Object>> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenda_de_carros);

        ano = findViewById(R.id.textAno);
        modelo = findViewById(R.id.textModelo);
        listaCarros = findViewById(R.id.listCarros);
        databaseHelper = new DatabaseHelper(this);

        listaCarros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                TextView txtID = view.findViewById(R.id.txtID);
                TextView txtModelo = view.findViewById(R.id.txtModelo);
                TextView txtAno = view.findViewById(R.id.txtAno);
                TextView txtValor = view.findViewById(R.id.txtValor);

                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                intent.putExtra("id", txtID.getText().toString());
                intent.putExtra("modelo", txtModelo.getText().toString());
                intent.putExtra("ano", txtAno.getText().toString());
                intent.putExtra("valor", txtValor.getText().toString());

                startActivity(intent);
            }
        });
    }

    private void carregaLista(Cursor cursor){
        lista = new ArrayList<>();
        cursor.moveToFirst();

        for(int i = 0; i < cursor.getCount(); i++){
            Map<String, Object> mapa = new HashMap<>();

            int id = cursor.getInt(0);
            String modelo = cursor.getString(1);
            String ano = cursor.getString(2);
            Double valor = cursor.getDouble(3);

            mapa.put("id", id);
            mapa.put("modelo", modelo);
            mapa.put("ano", ano);
            mapa.put("valor", valor);

            lista.add(mapa);
            cursor.moveToNext();
        }
        cursor.close();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, lista, R.layout.linha_carro, new String[]{"id", "modelo", "ano", "valor"}
        ,new int[]{R.id.txtID, R.id.txtModelo, R.id.txtAno, R.id.txtValor});

        listaCarros.setAdapter(simpleAdapter);
    }

    public void clickBuscarAno(View view) {
        String query = "";
        if(ano.getText().toString().isEmpty()){
            query = "SELECT * FROM carro";
        }
        else
        {
            query = "SELECT * FROM carro WHERE ano = " + ano.getText().toString();
        }

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        carregaLista(cursor);
    }

    public void clickBuscarNome(View view) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("carro");

        String[] projecao = new String[] {"*"};
        String selecao = "modelo=?";
        String[] selArgs = new String[] {modelo.getText().toString()};

        Cursor cursor = queryBuilder.query(db, projecao, selecao, selArgs, null, null, "ano DESC");
        carregaLista(cursor);
    }

    public void clickAdicionar(View view) {

        startActivity(new Intent(this, adicionaCarro.class));
    }
}