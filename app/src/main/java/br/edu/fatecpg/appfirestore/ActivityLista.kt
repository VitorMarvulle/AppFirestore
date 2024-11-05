package br.edu.fatecpg.appfirestore

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import com.google.firebase.firestore.FirebaseFirestore
import br.edu.fatecpg.appfirestore.databinding.ActivityListaBinding

class ActivityLista : AppCompatActivity() {
    private lateinit var binding: ActivityListaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayout = binding.linearLayoutProdutos

        val db = FirebaseFirestore.getInstance()

        db.collection("Produtos")
            .get()
            .addOnSuccessListener { result ->
                // Limpa conteudo da ultima view (caso haja)
                linearLayout.removeAllViews()

                for (document in result) {
                    val nome = document.getString("Nome") ?: ""
                    val categoria = document.getString("Categoria") ?: ""
                    val preco = document.getDouble("Preco") ?: 0.0

                    // Infla o cardview para cada produto
                    val productCardView = LayoutInflater.from(this).inflate(R.layout.item_product, null)

                    // Insere os dados do produto no textView dentro do cardView
                    val txtNome = productCardView.findViewById<TextView>(R.id.txtNome)
                    val txtCategoria = productCardView.findViewById<TextView>(R.id.txtCategoria)
                    val txtPreco = productCardView.findViewById<TextView>(R.id.txtPreco)

                    txtNome.text = "Nome: $nome"
                    txtCategoria.text = "Categoria: $categoria"
                    txtPreco.text = "Preço: R$ $preco"

                    // Adicionando o cardview do produto ao linearLayout
                    linearLayout.addView(productCardView)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao carregar os produtos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
