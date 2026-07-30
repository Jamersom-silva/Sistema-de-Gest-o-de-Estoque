package com.estoque.app.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.estoque.app.data.local.dao.ProdutoDao;
import com.estoque.app.data.local.dao.CategoriaDao;
import com.estoque.app.data.local.entity.Produto;
import com.estoque.app.data.local.entity.Categoria;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {Produto.class, Categoria.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract ProdutoDao produtoDao();
    public abstract CategoriaDao categoriaDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "estoque_database"
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(popularBancoDados())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback popularBancoDados() {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@androidx.annotation.NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                databaseWriteExecutor.execute(() -> {
                    CategoriaDao categoriaDao = INSTANCE.categoriaDao();

                    // Categorias padrão estilo Americanas
                    categoriaDao.inserir(new Categoria("Eletrônicos", "TVs, celulares, tablets"));
                    categoriaDao.inserir(new Categoria("Vestuário", "Roupas e acessórios"));
                    categoriaDao.inserir(new Categoria("Casa & Decoração", "Móveis e decoração"));
                    categoriaDao.inserir(new Categoria("Brinquedos", "Brinquedos e jogos"));
                    categoriaDao.inserir(new Categoria("Beleza & Saúde", "Cosméticos e saúde"));
                });
            }
        };
    }
}