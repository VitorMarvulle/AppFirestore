package br.edu.fatecpg.appfirestore

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import br.edu.fatecpg.appfirestore.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Firebase.firestore

        binding.btnSalvar.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val categoria = binding.edtCategoria.text.toString()
            val preco = binding.edtPreco.text.toString().toDouble()
            val tempo = FieldValue.serverTimestamp()
            val produto = hashMapOf(
                "Nome" to nome,
                "Categoria" to categoria,
                "Preco" to preco,
                "Gravacao" to tempo
            )
            db.collection("Produtos")
                .add(produto)
                .addOnSuccessListener {
                    Toast.makeText(this, "Produto Cadastrado com Sucesso!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao Cadastrar!", Toast.LENGTH_SHORT).show()
                }
            binding.edtNome.text.clear()
            binding.edtCategoria.text.clear()
            binding.edtPreco.text.clear()
        }

        binding.btnLista.setOnClickListener {
            val intent = Intent(this, ActivityLista::class.java)
            startActivity(intent)
        }
    }
}