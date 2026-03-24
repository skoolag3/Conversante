package com.skoolage.conversante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skoolage.conversante.dao.ConversanteDAO;
import com.skoolage.conversante.models.Conversantes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lstConversantes;
    private ConversanteDAO cDAO;
    private List<Conversantes> listaConversantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstConversantes = findViewById(R.id.lstConversantes);
        cDAO = new ConversanteDAO(this);

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

    public void btnNovoClick(View v) {
        Intent intent = new Intent(MainActivity.this, FormActivity.class);
        startActivity(intent);
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

        ArrayAdapter<Conversantes> adapter = new ArrayAdapter<Conversantes>(this, R.layout.contato_item, listaConversantes) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.contato_item, parent, false);
                }

                Conversantes c = getItem(position);
                TextView txtNome = convertView.findViewById(R.id.txtNomeItem);
                TextView txtCelular = convertView.findViewById(R.id.txtCelularItem);

                txtNome.setText(c.getNome());
                txtCelular.setText(c.getCelular());

                return convertView;
            }
        };

        lstConversantes.setAdapter(adapter);
    }
}