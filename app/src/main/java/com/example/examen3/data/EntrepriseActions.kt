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

    data class ModifierChampPrenom(val prenom: String): EntrepriseActions
    data class ModifierChampNom(val nom: String): EntrepriseActions
    data class ModifierChampCourriel(val courriel: String): EntrepriseActions
    data class ModifierChampRole(val role: String): EntrepriseActions
    data class ModifierChampSalaire(val salaire: String): EntrepriseActions
    data class ModifierChampEquipe(val equipe: Equipe): EntrepriseActions

}