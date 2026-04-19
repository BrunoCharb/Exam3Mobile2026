package com.example.examen3.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen3.data.modeles.Employe
import com.example.examen3.data.modeles.EmployeAjout
import com.example.examen3.data.modeles.Equipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntrepriseViewModel: ViewModel() {
    private val _uiState  = MutableStateFlow(EntrepriseUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllEmployes()
    }

    fun onAction(action: EntrepriseActions) {
        when(action) {
            EntrepriseActions.AfficherEquipes -> afficherListeEquipes()
            EntrepriseActions.AfficherEmployes -> afficherListeEmployes()
            is EntrepriseActions.AfficherDetailsEquipe -> afficherDetailsEquipe(action.id)
            EntrepriseActions.SoumettreFormulaireEmploye -> soumettreFormulaire()
            EntrepriseActions.AfficherFormulaire -> afficherFormulaire()
            is EntrepriseActions.ModifierEmploye -> afficherFormulaire(action.employe)
            is EntrepriseActions.ModifierChampPrenom -> modifierPrenom(action.prenom)
            is EntrepriseActions.ModifierChampNom -> modifierNom(action.nom)
            is EntrepriseActions.ModifierChampRole -> modifierRole(action.role)
            is EntrepriseActions.ModifierChampCourriel -> modifierCourriel(action.courriel)
            is EntrepriseActions.ModifierChampEquipe -> modifierEquipe(action.equipe)
            is EntrepriseActions.ModifierChampSalaire -> modifierSalaire(action.salaire)
        }
    }

    private fun afficherListeEmployes() {
        getAllEmployes()
        changerEcran("Employés")
    }

    private fun afficherListeEquipes() {
        viewModelScope.launch {
            getAllEquipes()
            changerEcran("Équipes")
        }

    }

    private fun afficherDetailsEquipe(id: String) {
        getEquipe(id)
        changerEcran("Détails")
    }

    /**
     * Fonction qui permet d'afficher le formulaire d'ajout/modification d'un employé.
     * Si un employé est fourni les champs doivent être remplis avec les valeurs courantes
     * de l'employé.
     */
    private fun afficherFormulaire(employe: Employe? = null) {
        changerEcran("Formulaire")

        viewModelScope.launch {
            getAllEquipes()
            var equipe: Equipe? = null
            if(employe != null) {
                equipe = _uiState.value.listeEquipes.find { it.id == employe.equipeId }
            }
            _uiState.update {
                it.copy(
                    employeId = employe?.id ?: "",
                    champCourriel = employe?.courriel ?: "",
                    champSalaire = employe?.salaire ?: 0,
                    champRole = employe?.role ?: "",
                    champPrenom = employe?.prenom ?: "",
                    champNom = employe?.nom ?: "",
                    equipeChoisie = equipe,
                )
            }
        }

    }

    private fun changerEcran(ecran: String) {
        _uiState.update {
            it.copy(
                ecranActif = ecran
            )
        }
    }

    // Appels à la base de données
    private fun getAllEmployes() {
        try {
            viewModelScope.launch {
                val employes = SupabaseClient.getEmployes()
                _uiState.update {
                    it.copy(
                        listeEmployes = employes
                    )
                }
            }
        } catch (e : Exception) {
            Log.e("ViewModel_getAllEmployes", e.message.toString())
        }
    }

    private suspend fun getAllEquipes() {
        try {
            val equipes = SupabaseClient.getEquipes()
            _uiState.update {
                it.copy(
                    listeEquipes = equipes
                )
            }
        } catch (e : Exception) {
            Log.e("ViewModel_getAllEquipes", e.message.toString())
        }
    }

    private fun getEquipe(id: String) {
        try {
            viewModelScope.launch {
                val equipe = SupabaseClient.getEquipeAvecEmployes(id)
                _uiState.update {
                    it.copy(
                        equipe = equipe
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("ViewModel_getEquipe", e.message.toString())
        }
    }

    /**
     * Fonction qui réinitialise l'état du formulaire d'ajout/modification
     */
    private fun resetFormulaire() {
        _uiState.update {
            it.copy(
                champCourriel = "",
                champSalaire = 0,
                champRole = "",
                champPrenom = "",
                champNom = "",
                equipeChoisie = null,
                employeId = ""
            )
        }
    }

    private fun modifierPrenom(prenom: String) {
        _uiState.update {
            it.copy(
                champPrenom = prenom
            )
        }
    }

    private fun modifierNom(nom: String) {
        _uiState.update {
            it.copy(
                champNom = nom
            )
        }
    }

    private fun modifierCourriel(courriel: String) {
        _uiState.update {
            it.copy(
                champCourriel = courriel
            )
        }
    }

    private fun modifierRole(role: String) {
        _uiState.update {
            it.copy(
                champRole = role
            )
        }
    }

    private fun modifierEquipe(equipe: Equipe) {
        _uiState.update {
            it.copy(
                equipeChoisie = equipe
            )
        }
    }

    private fun modifierSalaire(salaire: String) {
        try {
            if(salaire.all { it.isDigit() }) {
                _uiState.update {
                    it.copy(
                        champSalaire = salaire.toInt()
                    )
                }
            }
        } catch (e: NullPointerException) {
            Log.e("ViewModel_modifierSalaire", "Chaîne salaure vide: ${e.message}")
        }

    }

    /**
     * TODO : Compléter
     * Fonction qui gère l'ajout ou la modification d'un employé dans la base de données.
     * Notez qu'aucun URL n'est attendu pour l'image, il n'est pas nécessaire de l'ajouter
     * à quelconque endroit dans le code.
     */
    private fun soumettreFormulaire() {
        if(_uiState.value.employeId.isNullOrBlank()) {
            val employeAjout = EmployeAjout (
                prenom = _uiState.value.champPrenom,
                nom = _uiState.value.champNom,
                courriel = _uiState.value.champCourriel,
                salaire = _uiState.value.champSalaire,
                role = _uiState.value.champRole,
                equipeId = _uiState.value.equipeChoisie?.id,
                actif = true,
                photoUrl = "https://picsum.photos/seed/${_uiState.value.champPrenom}-${_uiState.value.champNom}/200"
            )

            try {
                viewModelScope.launch {
                    SupabaseClient.ajouterEmploye(employeAjout)
                    resetFormulaire()
                    changerEcran("Employés")
                }
            } catch (e: Exception) {
                Log.e("ViewModel_soumettreFormulaire", "Erreur lors de l'ajout ${e.message}")
            }
        }
        else {
            val employe = Employe (
                id = _uiState.value.employeId,
                prenom = _uiState.value.champPrenom,
                nom = _uiState.value.champNom,
                courriel = _uiState.value.champCourriel,
                salaire = _uiState.value.champSalaire,
                role = _uiState.value.champRole,
                equipeId = _uiState.value.equipeChoisie?.id,
                actif = true
            )
            try {
                viewModelScope.launch {
                    SupabaseClient.modifierEmploye(employe)
                    resetFormulaire()
                    changerEcran("Employés")
                }
            } catch (e: Exception) {
                Log.e("ViewModel_soumettreFormulaire", "Erreur lors de la modification ${e.message}")
            }
        }
    }
}