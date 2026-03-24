package com.skoolage.conversante;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.skoolage.conversante.dao.ConversanteDAO;
import com.skoolage.conversante.models.Conversantes;

import java.util.List;

public class FormActivity extends AppCompatActivity {

    private EditText edtNome, edtCelular, edtEmail;
    private Button btnPronto, btnLimpar, btnExcluir;
    private ConversanteDAO cDAO;
    private int conversanteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edtNome = findViewById(R.id.edtNome);
        edtCelular = findViewById(R.id.edtCelular);
        edtEmail = findViewById(R.id.edtEmail);
        btnPronto = findViewById(R.id.btnPronto);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnExcluir = findViewById(R.id.btnExcluir);

        cDAO = new ConversanteDAO(this);

        // Se recebeu ID, é edição/exclusão
        if (getIntent().hasExtra("ID")) {
            conversanteId = getIntent().getIntExtra("ID", -1);
            carregarDados();
            btnExcluir.setVisibility(View.VISIBLE);
        } else {
            btnExcluir.setVisibility(View.GONE);
        }

        btnPronto.setOnClickListener(new View.OnClickListener() {
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

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluir();
            }
        });
    }

    private void carregarDados() {
        cDAO.abrir();
        List<Conversantes> lista = cDAO.listarTudo();
        cDAO.fechar();

        for (Conversantes c : lista) {
            if (c.getId() == conversanteId) {
                edtNome.setText(c.getNome());
                edtCelular.setText(c.getCelular());
                edtEmail.setText(c.getEmail());
                break;
            }
        }
    }

    private void limparCampos() {
        edtNome.setText("");
        edtCelular.setText("");
        edtEmail.setText("");
        edtNome.requestFocus();
        conversanteId = -1;
        btnExcluir.setVisibility(View.GONE);
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
        long res;
        if (conversanteId == -1) {
            res = cDAO.Inserir(c);
        } else {
            c.setId(conversanteId);
            res = cDAO.Alterar(c);
        }
        cDAO.fechar();

        if (res > 0) {
            Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao processar", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        if (conversanteId != -1) {
            cDAO.abrir();
            long res = cDAO.Excluir(conversanteId);
            cDAO.fechar();

            if (res > 0) {
                Toast.makeText(this, "Excluído!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao excluir", Toast.LENGTH_SHORT).show();
            }
        }
    }
}