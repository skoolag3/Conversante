package com.skoolage.conversante;

import android.content.Intent;
import android.os.Build;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            conversante = getIntent().getParcelableExtra("CONVERSANTE", Conversantes.class);
        } else {
            conversante = getIntent().getParcelableExtra("CONVERSANTE");
        }

        if (conversante != null && conversante.getId() > 0) {
            conversanteId = conversante.getId();
            edtNome.setText(conversante.getNome());
            edtCelular.setText(conversante.getCelular());
            edtEmail.setText(conversante.getEmail());
        } else {
            conversante = null;
        }

        boolean hasID = conversanteId != -1;
        btnInserir.setVisibility(hasID ? View.GONE : View.VISIBLE);
        btnAtualizar.setVisibility(hasID ? View.VISIBLE : View.GONE);
        btnExcluir.setVisibility(hasID ? View.VISIBLE : View.GONE);
    }

    public void btnInserirClick(View v) {

        Conversantes c = new Conversantes(
                edtNome.getText().toString(),
                edtCelular.getText().toString(),
                edtEmail.getText().toString()
        );

        long id = cDAO.inserir(c);

        if (id <= 0) {
            Toast.makeText(this, "Erro ao inserir", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("RESULT", "INSERIU");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void btnAtualizarClick(View v) {

        conversante.setNome(edtNome.getText().toString());
        conversante.setCelular(edtCelular.getText().toString());
        conversante.setEmail(edtEmail.getText().toString());

        cDAO.alterar(conversante);

        Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    public void btnExcluirClick(View v) {

        if (conversante == null) return;

        cDAO.excluir(conversante);

        Intent intent = new Intent();
        intent.putExtra("RESULT", "EXCLUIU");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void btnLimparClick(View v) {
        edtNome.setText("");
        edtCelular.setText("");
        edtEmail.setText("");
        edtNome.requestFocus();

        conversanteId = -1;
        conversante = null;

        btnInserir.setVisibility(View.VISIBLE);
        btnAtualizar.setVisibility(View.GONE);
        btnExcluir.setVisibility(View.GONE);
    }
}