package com.example.examen3.data

import com.example.examen3.data.modeles.Employe
import com.example.examen3.data.modeles.EmployeAjout
import com.example.examen3.data.modeles.Equipe
import com.example.examen3.data.modeles.EquipeAvecEmployes
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order


const val SUPABASE_URL = "https://mlzvxulbrrmuesxrowis.supabase.co"
const val SUPABASE_KEY = "sb_publishable_7cmSJ_EWhYEBlaPWtHDNAg_QYZ3jpiG"


object SupabaseClient {

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
    }

    // Récupération de données ---- AUCUNE MODIFICATION NÉCESSAIRE DANS CETTE SECTION
    suspend fun getEmployes(): List<Employe> {
        val employes = client.from("employes")
            .select(
                columns = Columns.raw(
                    """
                        *,
                        equipeNom:equipes(
                            nom
                        )
                    """.trimIndent()
                )
            ) {
                order("nom", Order.ASCENDING)
            }
            .decodeList<Employe>()
        return employes
    }

    suspend fun getEquipes(): List<Equipe> {
        val equipes = client.from("equipes")
            .select(
                columns = Columns.raw("""
                    *,
                    employes(
                        *                       
                    )
                """.trimIndent())
            ) {
                order("nom", Order.ASCENDING)
            }
            .decodeList<Equipe>()
        return equipes
    }

    suspend fun getEquipeAvecEmployes(equipeId: String): EquipeAvecEmployes {
        val equipe = client.from("equipes")
            .select(
                columns = Columns.raw("""
                    *,
                    employes (
                        *
                    )
                """.trimIndent())
            ) {
                filter {
                    eq("id", equipeId)
                }
            }
            .decodeSingle<EquipeAvecEmployes>()
        return equipe
    }
    // ---- FIN de section de récupération des données

    // Manipulation des données

    suspend fun ajouterEmploye(employe: EmployeAjout) {
        // TODO compléter
    }

    suspend fun modifierEmploye(employe: Employe) {
        // TODO compléter
    }


}