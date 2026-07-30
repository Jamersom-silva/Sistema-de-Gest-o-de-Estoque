package com.estoque.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.estoque.app.R;
import com.estoque.app.ui.adapters.ProdutoAdapter;
import com.estoque.app.viewmodel.ProdutoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ProdutoViewModel produtoViewModel;
    private ProdutoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProdutoAdapter();
        recyclerView.setAdapter(adapter);

        // Inicializar ViewModel
        produtoViewModel = new ViewModelProvider(this).get(ProdutoViewModel.class);

        // Observar mudanças nos produtos
        produtoViewModel.getAllProdutos().observe(this, produtos -> {
            adapter.setProdutos(produtos);
        });

        // Botão Adicionar
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddProdutoActivity.class);
            startActivity(intent);
        });

        // Clique no item para editar
        adapter.setOnItemClickListener(produto -> {
            Intent intent = new Intent(MainActivity.this, AddProdutoActivity.class);
            intent.putExtra("produto_id", produto.getId());
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Buscar produto...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    produtoViewModel.searchProdutos(newText)
                            .observe(MainActivity.this, produtos -> {
                                adapter.setProdutos(produtos);
                            });
                } else {
                    produtoViewModel.getAllProdutos()
                            .observe(MainActivity.this, produtos -> {
                                adapter.setProdutos(produtos);
                            });
                }
                return true;
            }
        });

        return true;
    }
}