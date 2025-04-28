package com.example.notasfragmentos

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class DetalleNotaActivity : AppCompatActivity() {

    private lateinit var detalleNotaFragment: DetalleNotaFragment
    private val viewModel: NotasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_nota)

        detalleNotaFragment = supportFragmentManager.findFragmentById(R.id.fragment_detalle) as DetalleNotaFragment

        val notaTitulo = intent.getStringExtra("titulo")
        val notaContenido = intent.getStringExtra("contenido")
        val notaFecha = intent.getStringExtra("fecha")

        val nota = Nota(
            titulo = notaTitulo.orEmpty(),
            contenido = notaContenido.orEmpty(),
            fecha = notaFecha.orEmpty()
        )

        detalleNotaFragment.mostrarNota(nota)

        detalleNotaFragment.setOnGuardarCambiosListener { notaActualizada ->
            viewModel.actualizarNota(notaActualizada)
            finish() // Cierra la actividad al guardar
        }
    }
}
