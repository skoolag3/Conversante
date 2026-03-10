package com.skoolage.conversante;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.skoolage.conversante.dao.ConversanteDAO;
import com.skoolage.conversante.models.Conversantes;

public class FormActivity extends AppCompatActivity {

    private EditText edtNome, edtCelular, edtEmail;
    private Button btnSalvar, btnLimpar;
    private ConversanteDAO cDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edtNome = findViewById(R.id.edtNome);
        edtCelular = findViewById(R.id.edtCelular);
        edtEmail = findViewById(R.id.edtEmail);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnLimpar = findViewById(R.id.btnLimpar);

        cDAO = new ConversanteDAO(this);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });
    }

    private void limparCampos() {
        edtNome.setText("");
        edtCelular.setText("");
        edtEmail.setText("");
        edtNome.requestFocus();
    }

    private void salvar() {
        String nome = edtNome.getText().toString();
        String celular = edtCelular.getText().toString();
        String email = edtEmail.getText().toString();

        if (nome.isEmpty() || celular.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Conversantes c = new Conversantes(nome, celular, email);
        cDAO.abrir();
        long res = cDAO.Inserir(c);
        cDAO.fechar();

        if (res > 0) {
            Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
        }
    }
}