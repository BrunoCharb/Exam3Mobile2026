package com.example.examen3.data

import com.example.examen3.data.modeles.Employe
import com.example.examen3.data.modeles.Equipe

sealed interface EntrepriseActions {
    data object AfficherEmployes: EntrepriseActions
    data object AfficherEquipes: EntrepriseActions
    data class AfficherDetailsEquipe(val id: String) : EntrepriseActions
    data object AfficherFormulaire: EntrepriseActions
    data object AjouterEmploye: EntrepriseActions
    data class ModifierEmploye(val employe: Employe): EntrepriseActions

    // TODO gérer les champs du formulaire

}