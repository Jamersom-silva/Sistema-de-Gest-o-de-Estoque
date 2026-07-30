package com.estoque.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.estoque.app.data.local.entity.Produto;
import java.util.List;

@Dao
public interface ProdutoDao {

    @Insert
    long inserir(Produto produto);

    @Update
    void atualizar(Produto produto);

    @Delete
    void deletar(Produto produto);

    @Query("SELECT * FROM produtos ORDER BY nome ASC")
    LiveData<List<Produto>> getAllProdutos();

    @Query("SELECT * FROM produtos WHERE nome LIKE '%' || :searchQuery || '%' OR codigoBarras LIKE '%' || :searchQuery || '%'")
    LiveData<List<Produto>> searchProdutos(String searchQuery);

    @Query("SELECT * FROM produtos WHERE categoriaId = :categoriaId")
    LiveData<List<Produto>> getProdutosPorCategoria(long categoriaId);

    @Query("UPDATE produtos SET quantidade = quantidade + :quantidade WHERE id = :produtoId")
    void adicionarEstoque(long produtoId, int quantidade);

    @Query("UPDATE produtos SET quantidade = quantidade - :quantidade WHERE id = :produtoId AND quantidade >= :quantidade")
    void removerEstoque(long produtoId, int quantidade);

    @Query("SELECT * FROM produtos WHERE quantidade <= quantidadeMinima")
    LiveData<List<Produto>> getProdutosBaixoEstoque();
}