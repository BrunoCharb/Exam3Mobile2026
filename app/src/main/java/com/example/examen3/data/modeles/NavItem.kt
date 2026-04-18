package com.example.examen3.data.modeles

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val titre: String,
    val icone: ImageVector
)

val listeNav = listOf<NavItem>(
    NavItem("Employés", Icons.Default.Person),
    NavItem("Équipes", Icons.Default.PeopleAlt),
)