package com.estoque.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import com.estoque.app.R;
import com.estoque.app.ui.addproduto.AddProdutoActivity;
import com.estoque.app.viewmodel.ProdutoViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ProdutoViewModel produtoViewModel;

    // Indicadores
    private TextView txtTotalProdutos, txtEmEstoque, txtEstoqueBaixo, txtSemEstoque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar ViewModel
        produtoViewModel = new ViewModelProvider(this).get(ProdutoViewModel.class);

        // Inicializar Indicadores
        txtTotalProdutos = findViewById(R.id.txt_total_produtos);
        txtEmEstoque = findViewById(R.id.txt_em_estoque);
        txtEstoqueBaixo = findViewById(R.id.txt_estoque_baixo);
        txtSemEstoque = findViewById(R.id.txt_sem_estoque);

        // Observar dados
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


        // Ações Rápidas
        findViewById(R.id.card_cadastrar).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddProdutoActivity.class));
        });

        findViewById(R.id.card_movimentacoes).setOnClickListener(v -> {
            // TODO: Abrir movimentações
        });

        findViewById(R.id.card_estoque_baixo).setOnClickListener(v -> {
            // TODO: Filtrar estoque baixo
        });

        findViewById(R.id.card_relatorios).setOnClickListener(v -> {
            // TODO: Abrir relatórios
        });

        findViewById(R.id.card_fornecedores).setOnClickListener(v -> {
            // TODO: Abrir fornecedores
        });

        findViewById(R.id.card_categorias).setOnClickListener(v -> {
            // TODO: Abrir categorias
        });

        // Botão Scanner
        FloatingActionButton fabScanner = findViewById(R.id.fab_scanner);
        fabScanner.setOnClickListener(v -> {
            // TODO: Abrir scanner de código de barras
        });

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                // Já está na home
                return true;
            } else if (id == R.id.nav_produtos) {
                // TODO: Abrir lista de produtos
                return true;
            } else if (id == R.id.nav_scanner) {
                // TODO: Abrir scanner
                return true;
            } else if (id == R.id.nav_relatorios) {
                // TODO: Abrir relatórios
                return true;
            } else if (id == R.id.nav_mais) {
                // TODO: Abrir menu mais
                return true;
            }
            return false;
        });

        // Selecionar Home por padrão
        bottomNav.setSelectedItemId(R.id.nav_home);
    }
}