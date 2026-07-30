package com.estoque.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.estoque.app.data.local.entity.Categoria;
import java.util.List;

@Dao
public interface CategoriaDao {

    @Insert
    long inserir(Categoria categoria);

    @Update
    void atualizar(Categoria categoria);

    @Delete
    void deletar(Categoria categoria);

    @Query("SELECT * FROM categorias ORDER BY nome ASC")
    LiveData<List<Categoria>> getAllCategorias();
}