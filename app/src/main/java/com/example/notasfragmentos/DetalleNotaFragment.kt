package com.example.notasfragmentos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class DetalleNotaFragment : Fragment() {

    private lateinit var editTitulo: EditText
    private lateinit var editContenido: EditText
    private lateinit var editFecha: EditText
    private lateinit var btnGuardar: Button

    private var notaActual: Nota? = null
    private var onGuardarCambios: ((Nota) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalle_nota, container, false)

        editTitulo = view.findViewById(R.id.editTitulo)
        editContenido = view.findViewById(R.id.editContenido)
        editFecha = view.findViewById(R.id.editFecha)
        btnGuardar = view.findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            guardarCambios()
        }

        return view
    }

    fun mostrarNota(nota: Nota) {
        notaActual = nota
        editTitulo.setText(nota.titulo)
        editContenido.setText(nota.contenido)
        editFecha.setText(nota.fecha)
    }

    private fun guardarCambios() {
        notaActual?.let { nota ->
            nota.titulo = editTitulo.text.toString()
            nota.contenido = editContenido.text.toString()
            nota.fecha = editFecha.text.toString()

            onGuardarCambios?.invoke(nota)

            // Mostrar confirmación
            Snackbar.make(requireView(), "Nota actualizada", Snackbar.LENGTH_SHORT).show()

            // Cerrar teclado
            val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    fun setOnGuardarCambiosListener(listener: (Nota) -> Unit) {
        onGuardarCambios = listener
    }
}
