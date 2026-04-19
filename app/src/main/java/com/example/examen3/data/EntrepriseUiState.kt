package com.example.examen3.data

import com.example.examen3.data.modeles.Employe
import com.example.examen3.data.modeles.Equipe

data class EntrepriseUiState(
    val ecranActif: String = "Employés",
    val listeEmployes: List<Employe> = listOf(),
    val listeEquipes: List<Equipe> = listOf(),
    val equipe: Equipe? = null,

    // Formulaire
    val champPrenom: String = "",
    val champNom: String = "",
    val champCourriel: String = "",
    val champRole: String = "",
    val champSalaire: Int = 0,
    val equipeChoisie: Equipe? = null,
    val employeId: String = ""
)
