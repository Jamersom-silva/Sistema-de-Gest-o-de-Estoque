package com.estoque.app.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.estoque.app.R;
import com.estoque.app.data.local.entity.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {
    private List<Produto> produtos = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.nome.setText(produto.getNome());
        holder.quantidade.setText("Qtd: " + produto.getQuantidade());
        holder.preco.setText(String.format("R$ %.2f", produto.getPrecoVenda()));

        // Alerta de estoque baixo
        if (produto.getQuantidade() <= produto.getQuantidadeMinima()) {
            holder.itemView.setBackgroundColor(0xFFFFCDD2);
        }
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
        notifyDataSetChanged();
    }

    class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView nome, quantidade, preco;

        ProdutoViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.txt_nome_produto);
            quantidade = itemView.findViewById(R.id.txt_quantidade);
            preco = itemView.findViewById(R.id.txt_preco);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(produtos.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Produto produto);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}