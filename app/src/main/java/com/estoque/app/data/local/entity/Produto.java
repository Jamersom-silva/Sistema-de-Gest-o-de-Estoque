package com.estoque.app.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

@Entity(
        tableName = "produtos",
        foreignKeys = @ForeignKey(
                entity = Categoria.class,
                parentColumns = "id",
                childColumns = "categoria_id",  // ✅ CORRIGIDO
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("categoria_id")}  // ✅ CORRIGIDO
)
public class Produto {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String nome;
    private String codigoBarras;
    private int quantidade;

    @ColumnInfo(name = "preco_custo")
    private double precoCusto;

    @ColumnInfo(name = "preco_venda")
    private double precoVenda;

    @ColumnInfo(name = "categoria_id")
    private long categoriaId;

    @ColumnInfo(name = "data_entrada")
    private String dataEntrada;

    @ColumnInfo(name = "quantidade_minima")
    private int quantidadeMinima;

    private String descricao;

    // Construtor vazio (obrigatório para Room)
    public Produto() {}

    // Construtor com parâmetros (ignorado pelo Room)
    @Ignore
    public Produto(String nome, int quantidade, double precoVenda, long categoriaId) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
        this.categoriaId = categoriaId;
    }

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(double precoCusto) { this.precoCusto = precoCusto; }

    public double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(double precoVenda) { this.precoVenda = precoVenda; }

    public long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(long categoriaId) { this.categoriaId = categoriaId; }

    public String getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(String dataEntrada) { this.dataEntrada = dataEntrada; }

    public int getQuantidadeMinima() { return quantidadeMinima; }
    public void setQuantidadeMinima(int quantidadeMinima) { this.quantidadeMinima = quantidadeMinima; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}