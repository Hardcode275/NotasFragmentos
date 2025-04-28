package com.example.notasfragmentos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotasViewModel : ViewModel() {

    // 🔥 Lista de notas como LiveData
    private val _notas = MutableLiveData<MutableList<Nota>>(mutableListOf())
    val notas: LiveData<MutableList<Nota>> get() = _notas

    // 🔥 Nota seleccionada
    private val _notaSeleccionada = MutableLiveData<Nota?>()
    val notaSeleccionada: LiveData<Nota?> get() = _notaSeleccionada

    // 🔥 Agregar una nueva nota
    fun agregarNota(nota: Nota) {
        _notas.value?.add(nota)
        _notas.value = _notas.value // Forzar actualización visual
    }

    // 🔥 Seleccionar una nota (cuando das clic en una)
    fun seleccionarNota(nota: Nota) {
        _notaSeleccionada.value = nota
    }

    // 🔥 Actualizar una nota existente
    fun actualizarNota(notaActualizada: Nota) {
        val listaActual = _notas.value ?: return

        // Buscamos por el ID único de la nota
        val index = listaActual.indexOfFirst { it.id == notaActualizada.id }
        if (index != -1) {
            listaActual[index] = notaActualizada.copy(id = listaActual[index].id) // Mantener mismo ID
            _notas.value = listaActual.toMutableList() // Forzar actualización
        }
    }
}

