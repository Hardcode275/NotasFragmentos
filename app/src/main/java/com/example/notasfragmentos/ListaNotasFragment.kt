package com.example.notasfragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

class ListaNotasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notasAdapter: NotasAdapter
    private var listener: OnNoteSelectedListener? = null

    private val viewModel: NotasViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNoteSelectedListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_notas, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewNotas)
        recyclerView.layoutManager = LinearLayoutManager(context)

        notasAdapter = NotasAdapter(emptyList()) { nota ->
            listener?.onNoteSelected(nota)
        }
        recyclerView.adapter = notasAdapter

        // 🔥 Aquí observamos cambios y actualizamos
        viewModel.notas.observe(viewLifecycleOwner) { notas ->
            notasAdapter.actualizarLista(notas)
        }

        return view
    }

    interface OnNoteSelectedListener {
        fun onNoteSelected(nota: Nota)
    }
}


