package com.estoque.app.ui.addproduto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.estoque.app.R;
import com.estoque.app.data.local.entity.Produto;
import com.estoque.app.viewmodel.ProdutoViewModel;

public class AddProdutoActivity extends AppCompatActivity {

    private EditText editNome, editCodigoBarras, editQuantidade,
            editPrecoCusto, editPrecoVenda, editDescricao;
    private Button btnSalvar;
    private LinearLayout btnVoltar;
    private ProdutoViewModel produtoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produto);

        produtoViewModel = new ViewModelProvider(this).get(ProdutoViewModel.class);

        // Botão Voltar
        btnVoltar = findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(v -> finish());

        // Inicializar campos
        editNome = findViewById(R.id.edit_nome);
        editCodigoBarras = findViewById(R.id.edit_codigo_barras);
        editQuantidade = findViewById(R.id.edit_quantidade);
        editPrecoCusto = findViewById(R.id.edit_preco_custo);
        editPrecoVenda = findViewById(R.id.edit_preco_venda);
        editDescricao = findViewById(R.id.edit_descricao);
        btnSalvar = findViewById(R.id.btn_salvar);

        btnSalvar.setOnClickListener(v -> salvarProduto());
    }

    private void salvarProduto() {
        String nome = editNome.getText().toString().trim();
        String codigoBarras = editCodigoBarras.getText().toString().trim();
        String quantidadeStr = editQuantidade.getText().toString().trim();
        String precoVendaStr = editPrecoVenda.getText().toString().trim();

        if (nome.isEmpty() || quantidadeStr.isEmpty() || precoVendaStr.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade = Integer.parseInt(quantidadeStr);
        double precoVenda = Double.parseDouble(precoVendaStr);

        Produto produto = new Produto(nome, quantidade, precoVenda, 1);

        if (!codigoBarras.isEmpty()) {
            produto.setCodigoBarras(codigoBarras);
        }

        String precoCustoStr = editPrecoCusto.getText().toString().trim();
        if (!precoCustoStr.isEmpty()) {
            produto.setPrecoCusto(Double.parseDouble(precoCustoStr));
        }

        String descricao = editDescricao.getText().toString().trim();
        if (!descricao.isEmpty()) {
            produto.setDescricao(descricao);
        }

        produtoViewModel.inserir(produto);
        Toast.makeText(this, "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}