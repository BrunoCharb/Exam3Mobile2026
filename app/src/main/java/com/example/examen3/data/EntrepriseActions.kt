package com.example.examen3.data

import com.example.examen3.data.modeles.Employe
import com.example.examen3.data.modeles.Equipe

sealed interface EntrepriseActions {
    data object AfficherEmployes: EntrepriseActions
    data object AfficherEquipes: EntrepriseActions
    data class AfficherDetailsEquipe(val id: String) : EntrepriseActions
    data object AfficherFormulaire: EntrepriseActions
    data class ModifierEmploye(val employe: Employe): EntrepriseActions
    data object SoumettreFormulaireEmploye: EntrepriseActions

    // TODO gérer les champs du formulaire

}