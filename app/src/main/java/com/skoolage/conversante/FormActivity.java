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
    private Button btnInserir, btnAtualizar, btnExcluir;
    private ConversanteDAO cDAO;
    private Conversantes conversante;
    private int conversanteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edtNome = findViewById(R.id.edtNome);
        edtCelular = findViewById(R.id.edtCelular);
        edtEmail = findViewById(R.id.edtEmail);
        btnInserir = findViewById(R.id.btnInserir);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnExcluir = findViewById(R.id.btnExcluir);

        cDAO = new ConversanteDAO(this);

        if (getIntent().hasExtra("CONVERSANTE")) {
            conversante = (Conversantes) getIntent().getSerializableExtra("CONVERSANTE");
            if (conversante != null) {
                conversanteId = conversante.getId();
                edtNome.setText(conversante.getNome());
                edtCelular.setText(conversante.getCelular());
                edtEmail.setText(conversante.getEmail());
            }
        }

        boolean hasID = conversanteId != -1;
        btnInserir.setVisibility(hasID ? View.GONE : View.VISIBLE);
        btnAtualizar.setVisibility(hasID ? View.VISIBLE : View.GONE);
        btnExcluir.setVisibility(hasID ? View.VISIBLE : View.GONE);
    }

    public void btnInserirClick(View v) {
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
            Toast.makeText(this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void btnAtualizarClick(View v) {
        String nome = edtNome.getText().toString();
        String celular = edtCelular.getText().toString();
        String email = edtEmail.getText().toString();

        if (nome.isEmpty() || celular.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Conversantes c = new Conversantes(nome, celular, email);
        c.setId(conversanteId);

        cDAO.abrir();
        long res = cDAO.Alterar(c);
        cDAO.fechar();

        if (res > 0) {
            Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void btnLimparClick(View v) {
        edtNome.setText("");
        edtCelular.setText("");
        edtEmail.setText("");
        edtNome.requestFocus();

        conversanteId = -1;
        conversante = null;

        // Atualiza botões manualmente
        btnInserir.setVisibility(View.VISIBLE);
        btnAtualizar.setVisibility(View.GONE);
        btnExcluir.setVisibility(View.GONE);
    }

    public void btnExcluirClick(View v) {
        if (conversanteId != -1) {
            cDAO.abrir();
            long res = cDAO.Excluir(conversanteId);
            cDAO.fechar();

            if (res > 0) {
                Toast.makeText(this, "Excluído com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}