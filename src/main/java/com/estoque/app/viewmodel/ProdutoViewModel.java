package com.estoque.app.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.estoque.app.data.local.AppDatabase;
import com.estoque.app.data.local.entity.Produto;
import com.estoque.app.data.repository.EstoqueRepository;
import java.util.List;

public class ProdutoViewModel extends AndroidViewModel {
    private EstoqueRepository repository;
    private LiveData<List<Produto>> todosProdutos;

    public ProdutoViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        repository = new EstoqueRepository(database);
        todosProdutos = repository.getAllProdutos();
    }

    public LiveData<List<Produto>> getAllProdutos() {
        return todosProdutos;
    }

    public void inserir(Produto produto) {
        repository.inserir(produto);
    }

    public void atualizar(Produto produto) {
        repository.atualizar(produto);
    }

    public void deletar(Produto produto) {
        repository.deletar(produto);
    }

    public LiveData<List<Produto>> searchProdutos(String query) {
        return repository.searchProdutos(query);
    }
}