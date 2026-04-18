package com.example.examen3.data

import android.util.Log
import com.example.examen3.data.modeles.Employe
import com.example.examen3.data.modeles.EmployeAjout
import com.example.examen3.data.modeles.Equipe
import com.example.examen3.data.modeles.EquipeAvecEmployes
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import java.lang.Exception


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
        try {
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
        } catch (e: Exception) {
            Log.e("SupabaseClient_getEmployes", e.message.toString())
        }
        return emptyList()
    }

    suspend fun getEquipes(): List<Equipe> {
        try {
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
        } catch (e: kotlin.Exception) {
            Log.e("SupabaseClient_getEquipes", e.message.toString())
        }
        return emptyList()
    }

    suspend fun getEquipeAvecEmployes(equipeId: String): EquipeAvecEmployes {
        try {
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
        } catch (e: kotlin.Exception) {
            Log.e("SupabaseClient_getEquipeAvecEmployes", e.message.toString())
        }
        return EquipeAvecEmployes("Erreur", "Erreur", "Erreur", "Erreur", emptyList())
    }
    // ---- FIN de section de récupération des données

    // Manipulation des données

    suspend fun ajouterEmploye(employe: EmployeAjout) {
        // TODO compléter
        client.from("employes").insert(employe)
    }

    suspend fun modifierEmploye(employe: Employe) {
        client.from("employes").upsert(employe)
    }


}