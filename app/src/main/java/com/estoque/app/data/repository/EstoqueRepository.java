package com.estoque.app.data.repository;

import androidx.lifecycle.LiveData;
import com.estoque.app.data.local.AppDatabase;
import com.estoque.app.data.local.dao.ProdutoDao;
import com.estoque.app.data.local.dao.CategoriaDao;
import com.estoque.app.data.local.entity.Produto;
import com.estoque.app.data.local.entity.Categoria;
import java.util.List;

public class EstoqueRepository {
    private ProdutoDao produtoDao;
    private CategoriaDao categoriaDao;

    public EstoqueRepository(AppDatabase database) {
        this.produtoDao = database.produtoDao();
        this.categoriaDao = database.categoriaDao();
    }

    // Operações de Produto
    public LiveData<List<Produto>> getAllProdutos() {
        return produtoDao.getAllProdutos();
    }

    public LiveData<List<Produto>> searchProdutos(String query) {
        return produtoDao.searchProdutos(query);
    }

    public void inserir(Produto produto) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            produtoDao.inserir(produto);
        });
    }

    public void atualizar(Produto produto) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            produtoDao.atualizar(produto);
        });
    }

    public void deletar(Produto produto) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            produtoDao.deletar(produto);
        });
    }

    public void adicionarEstoque(long produtoId, int quantidade) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            produtoDao.adicionarEstoque(produtoId, quantidade);
        });
    }

    // Operações de Categoria
    public LiveData<List<Categoria>> getAllCategorias() {
        return categoriaDao.getAllCategorias();
    }
}