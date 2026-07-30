package com.estoque.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.estoque.app.R;
import com.estoque.app.ui.addproduto.AddProdutoActivity;
import com.estoque.app.viewmodel.ProdutoViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ProdutoViewModel produtoViewModel;
    private TextView txtTotalProdutos, txtEmEstoque, txtEstoqueBaixo, txtSemEstoque;
    private EditText editBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        produtoViewModel = new ViewModelProvider(this).get(ProdutoViewModel.class);

        // Indicadores
        txtTotalProdutos = findViewById(R.id.txt_total_produtos);
        txtEmEstoque = findViewById(R.id.txt_em_estoque);
        txtEstoqueBaixo = findViewById(R.id.txt_estoque_baixo);
        txtSemEstoque = findViewById(R.id.txt_sem_estoque);

        // Campo de busca
        editBusca = findViewById(R.id.edit_busca);
        editBusca.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = v.getText().toString().trim();
                buscarProdutos(query);
                return true;
            }
            return false;
        });

        editBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscarProdutos(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Observar produtos
        produtoViewModel.getAllProdutos().observe(this, produtos -> {
            if (produtos != null) {
                int total = produtos.size();
                int emEstoque = 0;
                int baixo = 0;
                int zerado = 0;

                for (com.estoque.app.data.local.entity.Produto p : produtos) {
                    if (p.getQuantidade() == 0) {
                        zerado++;
                    } else if (p.getQuantidade() <= p.getQuantidadeMinima()) {
                        baixo++;
                    } else {
                        emEstoque++;
                    }
                }

                txtTotalProdutos.setText(String.valueOf(total));
                txtEmEstoque.setText(String.valueOf(emEstoque));
                txtEstoqueBaixo.setText(String.valueOf(baixo));
                txtSemEstoque.setText(String.valueOf(zerado));
            }
        });

        // Card Cadastrar
        findViewById(R.id.card_cadastrar).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddProdutoActivity.class));
        });

        // Cards de ações rápidas
        findViewById(R.id.card_movimentacoes).setOnClickListener(v -> {});
        findViewById(R.id.card_estoque_baixo).setOnClickListener(v -> {});
        findViewById(R.id.card_relatorios).setOnClickListener(v -> {});
        findViewById(R.id.card_fornecedores).setOnClickListener(v -> {});
        findViewById(R.id.card_categorias).setOnClickListener(v -> {});

        // Scanner
        FloatingActionButton fabScanner = findViewById(R.id.fab_scanner);
        fabScanner.setOnClickListener(v -> {});

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_produtos) {
                startActivity(new Intent(MainActivity.this, AddProdutoActivity.class));
                return true;
            }
            return false;
        });
        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    private void buscarProdutos(String query) {
        if (query.isEmpty()) {
            produtoViewModel.getAllProdutos();
        } else {
            produtoViewModel.searchProdutos("%" + query + "%");
        }
    }
}