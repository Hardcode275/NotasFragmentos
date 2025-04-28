package com.example.notasfragmentos

import com.android.identity.util.UUID

data class Nota(
    var titulo: String,
    var contenido: String,
    var fecha: String,
    val id: String = UUID.randomUUID().toString() // ID único automático

)
