package com.example.examen3.data.modeles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employe(
    val id: String,
    @SerialName("equipe_id")
    val equipeId: String?,
    val prenom: String,
    val nom: String,
    val courriel: String,
    val role: String,
    val salaire: Int,
    @SerialName("est_actif")
    val actif: Boolean,
    @SerialName("url_photo_profil")
    val photoUrl: String?,
    val equipeNom: EquipeNom? = null
)

@Serializable
data class EquipeNom(
    val nom: String
)