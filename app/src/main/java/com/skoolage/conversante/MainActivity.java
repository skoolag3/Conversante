package com.skoolage.conversante;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.skoolage.conversante.dao.ConversanteDAO;
import com.skoolage.conversante.models.Conversantes;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lstConversantes;
    private ConversanteDAO cDAO;
    private List<Conversantes> listaConversantes;

    ActivityResultLauncher<Intent> formLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstConversantes = findViewById(R.id.lstConversantes);
        cDAO = new ConversanteDAO(this);

        formLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {

                        // pegando retorno do FormActivity (se quiser usar depois)
                        Intent data = result.getData();
                        if (data != null && data.hasExtra("RESULT")) {
                            String acao = data.getStringExtra("RESULT");
                        }

                        // abre, lista e fecha
                        cDAO.abrir();
                        listar(cDAO);
                        cDAO.fechar();
                    }
                }
        );

        // inicial
        cDAO.abrir();
        listar(cDAO);
        cDAO.fechar();

        lstConversantes.setOnItemClickListener((parent, view, position, id) -> {
            Conversantes selecionado = listaConversantes.get(position);
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            intent.putExtra("CONVERSANTE", (Parcelable) selecionado);
            formLauncher.launch(intent);
        });
    }

    public void btnNovoClick(View v) {
        Intent intent = new Intent(MainActivity.this, FormActivity.class);

        Conversantes novo = new Conversantes(); // objeto vazio
        intent.putExtra("CONVERSANTE", (Parcelable) novo);

        formLauncher.launch(intent);
    }



    private void listar(ConversanteDAO cDAO) {
        listaConversantes = cDAO.listarTudo();

        ArrayAdapter<Conversantes> adapter = new ArrayAdapter<Conversantes>(
                this, R.layout.contato_item, listaConversantes) {

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