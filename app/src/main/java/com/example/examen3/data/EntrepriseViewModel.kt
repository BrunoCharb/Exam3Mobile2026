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
        }
    }

    /**
     * Fonction qui affiche la liste de tous les employés
     * Récupère les employés de la base de données et modifie l'écran actif.
     */
    private fun afficherListeEmployes() {
        getAllEmployes()
        changerEcran("Employés")
    }

    /**
     * Fonction qui permet d'afficher toutes les équipes.
     * Récupères les équipes de la base de données et change l'écran actif.
     */
    private fun afficherListeEquipes() {
        viewModelScope.launch {
            getAllEquipes()
            changerEcran("Équipes")
        }

    }

    /**
     * Fonction qui permet d'afficher les détails d'une équipe
     * Récupère l'équipe et modifie l'écran actif.
     */
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
                    equipeChoisie = equipe ?:  null,
                )
            }
        }

    }

    /**
     * Fonction qui permet d'assurer la navigation dans l'application en modifiant la variable d'état
     * ecranActif
     * @param ecran: Le nom de l'écran à afficher
     */
    private fun changerEcran(ecran: String) {
        _uiState.update {
            it.copy(
                ecranActif = ecran
            )
        }
    }

    // Appels à la base de données
    /**
     * Fonction qui récupère l'ensemble des employés stockés dans la base de données.
     */
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

    /**
     * Fonction qui récupère l'ensemble des équipes de la base de données.
     */
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

    /**
     * Fonction qui récupère une équipe spécifique de la base de données
     * @param id: l'identifiant unique de l'équipe
     */
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

    /**
     * TODO : Compléter
     * Fonction qui gère l'ajout ou la modification d'un employé dans la base de données.
     * Notez qu'aucun URL n'est attendu pour l'image, il n'est pas nécessaire de l'ajouter
     * à quelconque endroit dans le code.
     */
    private fun soumettreFormulaire() {

    }
}