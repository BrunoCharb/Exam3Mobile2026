package com.example.examen3.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examen3.data.EntrepriseViewModel

@Composable
fun ExamenApp(
    modifier: Modifier = Modifier,
    viewModel: EntrepriseViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                ongletActif = state.ecranActif,
                onNavigate = viewModel::onAction
            )
        },
        topBar = {
            ExamenTopBar(
                state.ecranActif
            )
        }
    ) { innerPadding ->
        when(state.ecranActif) {
            "Employés" -> {
                EcranListeEmployes(
                    lsEmployes = state.listeEmployes,
                    onAction = viewModel::onAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
            "Équipes" -> {
                EcranListeEquipes(
                    equipes =  state.listeEquipes,
                    onAction = viewModel::onAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
            "Détails" -> {
                EcranDetailsEquipe(
                    equipe =  state.equipe,
                    onAction = viewModel::onAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
            "Formulaire" -> {
                FormulaireEmploye(
                    state.listeEquipes,
                    null,
                    onAction = viewModel::onAction,
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    }
}