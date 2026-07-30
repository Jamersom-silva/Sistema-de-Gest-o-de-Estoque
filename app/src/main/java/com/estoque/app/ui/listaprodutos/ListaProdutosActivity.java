package com.estoque.app.ui.listaprodutos;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.estoque.app.R;
import com.estoque.app.data.local.entity.Produto;
import com.estoque.app.ui.adapters.ProdutoAdapter;
import com.estoque.app.viewmodel.ProdutoViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;

public class ListaProdutosActivity extends AppCompatActivity {

    private ProdutoViewModel produtoViewModel;
    private RecyclerView recyclerView;
    private ProdutoAdapter adapter;
    private LinearLayout layoutCategorias;

    private List<Produto> todosProdutos = new ArrayList<>();
    private String categoriaSelecionada = "Todos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        produtoViewModel = new ViewModelProvider(this).get(ProdutoViewModel.class);

        // Botão Voltar
        findViewById(R.id.btn_voltar_lista).setOnClickListener(v -> finish());

        // RecyclerView
        recyclerView = findViewById(R.id.recycler_produtos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProdutoAdapter();
        recyclerView.setAdapter(adapter);

        // Layout de Categorias
        layoutCategorias = findViewById(R.id.layout_categorias);

        // Criar chips de categoria
        criarChipsCategoria();

        // Observar produtos
        produtoViewModel.getAllProdutos().observe(this, produtos -> {
            if (produtos != null) {
                todosProdutos = produtos;
                filtrarProdutos();
            }
        });
    }

    private void criarChipsCategoria() {
        String[] categorias = {"Todos", "Eletrônicos", "Vestuário", "Casa & Decoração", "Brinquedos", "Beleza & Saúde"};

        for (String cat : categorias) {
            Chip chip = new Chip(this);
            chip.setText(cat);
            chip.setCheckable(true);
            chip.setClickable(true);

            if (cat.equals("Todos")) {
                chip.setChecked(true);
            }

            chip.setOnClickListener(v -> {
                categoriaSelecionada = cat;
                filtrarProdutos();
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 8, 0);
            chip.setLayoutParams(params);

            layoutCategorias.addView(chip);
        }
    }

    private void filtrarProdutos() {
        if (categoriaSelecionada.equals("Todos")) {
            adapter.setProdutos(todosProdutos);
        } else {
            // TODO: Filtrar por categoria quando implementado
            adapter.setProdutos(todosProdutos);
        }
    }
}