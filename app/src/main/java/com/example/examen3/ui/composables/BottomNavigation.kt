package com.example.examen3.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.examen3.data.EntrepriseActions
import com.example.examen3.data.modeles.listeNav

@Composable
fun BottomNavigation(
    ongletActif: String,
    onNavigate: (EntrepriseActions) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        for(item in listeNav) {
            NavigationBarItem(
                selected = ongletActif == item.titre,
                onClick = {
                    when(item.titre) {
                        "Employés" -> onNavigate(EntrepriseActions.AfficherEmployes)
                        "Équipes" -> onNavigate(EntrepriseActions.AfficherEquipes)
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icone,
                        contentDescription = "Icone ${item.titre}"
                    )
                },
                label = {
                    Text(
                        item.titre
                    )
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        }

    }
}