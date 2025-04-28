package com.example.notasfragmentos

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ListaNotasFragment.OnNoteSelectedListener {

    private lateinit var listaNotasFragment: ListaNotasFragment
    private lateinit var detalleNotaFragment: DetalleNotaFragment

    private val viewModel: NotasViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al botón flotante
        val fab: FloatingActionButton = findViewById(R.id.fabAgregarNota)
        fab.setOnClickListener {
            mostrarDialogoNuevaNota()
        }

        // Si estamos en horizontal, cargar ambos fragmentos
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            listaNotasFragment = supportFragmentManager.findFragmentById(R.id.fragment_lista) as ListaNotasFragment
            detalleNotaFragment = supportFragmentManager.findFragmentById(R.id.fragment_detalle) as DetalleNotaFragment

            detalleNotaFragment.setOnGuardarCambiosListener { notaActualizada ->
                viewModel.actualizarNota(notaActualizada)
            }
        }


        // Observar selección de nota para mostrarla en el detalle
        viewModel.notaSeleccionada.observe(this) { nota ->
            nota?.let {
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    detalleNotaFragment.mostrarNota(it)
                }
            }
        }

        // Inicializar notas solo si está vacío
        if (viewModel.notas.value.isNullOrEmpty()) {
            inicializarNotas()
        }
    }

    private fun inicializarNotas() {
        viewModel.agregarNota(Nota("Tarea Matemática", "Resolver ejercicios del capítulo 5", "2025-04-27"))
        viewModel.agregarNota(Nota("Proyecto Historia", "Investigar sobre la Revolución Industrial", "2025-04-26"))
        viewModel.agregarNota(Nota("Examen Física", "Estudiar leyes de Newton", "2025-04-25"))
    }

    override fun onNoteSelected(nota: Nota) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewModel.seleccionarNota(nota)
        } else {
            // Vertical: Abrir nueva actividad
            val intent = Intent(this, DetalleNotaActivity::class.java)
            intent.putExtra("titulo", nota.titulo)
            intent.putExtra("contenido", nota.contenido)
            intent.putExtra("fecha", nota.fecha)
            startActivity(intent)
        }
    }

    private fun mostrarDialogoNuevaNota() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Nueva Nota")

        val view = layoutInflater.inflate(R.layout.dialog_nueva_nota, null)
        val editTitulo = view.findViewById<android.widget.EditText>(R.id.editTituloDialogo)
        val editContenido = view.findViewById<android.widget.EditText>(R.id.editContenidoDialogo)

        builder.setView(view)

        builder.setPositiveButton("Guardar") { _, _ ->
            val titulo = editTitulo.text.toString()
            val contenido = editContenido.text.toString()
            val fecha = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())

            val nuevaNota = Nota(titulo, contenido, fecha)
            viewModel.agregarNota(nuevaNota)

            android.widget.Toast.makeText(this, "Nota agregada", android.widget.Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarNotas(newText.orEmpty())
                return true
            }
        })

        return true
    }

    private fun filtrarNotas(texto: String) {
        val listaFiltrada = viewModel.notas.value?.filter {
            it.titulo.contains(texto, ignoreCase = true)
        } ?: emptyList()

        if (listaFiltrada.isEmpty()) {
            android.widget.Toast.makeText(this, "No se encontraron resultados", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}


