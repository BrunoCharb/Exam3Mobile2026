package com.example.examen3.ui.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.examen3.data.EntrepriseActions
import com.example.examen3.data.EntrepriseUiState
import com.example.examen3.data.modeles.Employe
import com.example.examen3.data.modeles.Equipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaireEmploye(
    equipes: List<Equipe>,
    employe: Employe?,
    state: EntrepriseUiState,
    onAction: (EntrepriseActions) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onAction(EntrepriseActions.AfficherEmployes)
    }

    // TODO gérer les champs du formulaire (modifications de valeur et erreurs)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = state.champPrenom,
            onValueChange = {
                // TODO gérer le changement d'état du formulaire
            },
            // S'il y a une erreur changer à true
            isError = false,
            label = {
                Text(
                    "Prénom"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )
        OutlinedTextField(
            value = state.champNom,
            onValueChange = {
                // TODO gérer le changement d'état du formulaire
            },
            isError = false,
            label = {
                Text(
                    "Nom"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )
        OutlinedTextField(
            value = state.champCourriel,
            onValueChange = {
                // TODO gérer le changement d'état du formulaire
            },
            isError = false,
            label = {
                Text(
                    "Courriel"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        OutlinedTextField(
            value = state.champRole,
            onValueChange = {
                // TODO gérer le changement d'état du formulaire
            },
            isError = false,
            label = {
                Text(
                    "Rôle"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )
        OutlinedTextField(
            value = state.champSalaire.toString(),
            onValueChange = {
                // TODO gérer le changement d'état du formulaire
            },
            isError = false,
            label = {
                Text(
                    "Salaire"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )

        )

        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = state.equipeChoisie?.nom ?: "Choisir une équipe",
                onValueChange = {
                    // NE PAS AJOUTER DE GESTION ICI
                },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                equipes.forEach { equipe ->
                    DropdownMenuItem(
                        text = { Text(equipe.nom) },
                        onClick = {
                            // TODO gérer le changement d'état du formulaire, ne pas toucher à expanded
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                    // TODO Gérer le clic. S'assurer qu'on puisse ajouter ou modifier un employé avec ce formulaire seulement
                },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(
                "Enregistrer"
            )
        }
    }
}