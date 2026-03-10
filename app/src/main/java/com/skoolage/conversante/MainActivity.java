package com.skoolage.conversante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.skoolage.conversante.dao.ConversanteDAO;
import com.skoolage.conversante.models.Conversantes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lstConversantes;
    private Button btnNovo;
    private ConversanteDAO cDAO;
    private List<Conversantes> listaConversantes;
    private List<String> nomesConversantes;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstConversantes = findViewById(R.id.lstConversantes);
        btnNovo = findViewById(R.id.btnNovo);

        cDAO = new ConversanteDAO(this);
        nomesConversantes = new ArrayList<>();

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        lstConversantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conversantes selecionado = listaConversantes.get(position);
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("ID", selecionado.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listar();
    }

    private void listar() {
        cDAO.abrir();
        listaConversantes = cDAO.listarTudo();
        cDAO.fechar();

        nomesConversantes.clear();
        for (Conversantes c : listaConversantes) {
            nomesConversantes.add(c.getNome() + " - " + c.getCelular());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomesConversantes);
        lstConversantes.setAdapter(adapter);
    }
}