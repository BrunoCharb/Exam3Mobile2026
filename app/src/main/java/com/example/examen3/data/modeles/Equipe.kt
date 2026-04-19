package com.example.examen3.data.modeles

import kotlinx.serialization.Serializable

@Serializable
data class Equipe(
    val id: String,
    val nom: String,
    val departement: String,
    val description: String,
    val employes: List<Employe>
)

