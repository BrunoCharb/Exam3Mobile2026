package com.example.examen3.data

import android.util.Log
import com.example.examen3.data.modeles.Employe
import com.example.examen3.data.modeles.EmployeAjout
import com.example.examen3.data.modeles.Equipe
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


    // Manipulation des données

    suspend fun ajouterEmploye(employe: EmployeAjout) {
        // TODO compléter
    }

    suspend fun modifierEmploye(employe: Employe) {
        // TODO compléter
    }

    /**
     * Fonction qui récupère tous les employés de la base de données.
     * TODO modifier la fonction pour également récupérer le nom de l'équipe de l'employé à l'aide de la relation dans la BD
     */
    suspend fun getEmployes(): List<Employe> {
        try {
            val employes = client.from("employes")
                .select() {
                    order("nom", Order.ASCENDING)
                }
                .decodeList<Employe>()
            return employes
        } catch (e: Exception) {
            Log.e("SupabaseClient_getEmployes", e.message.toString())
        }
        return emptyList()
    }

    // Récupération de données ---- AUCUNE MODIFICATION NÉCESSAIRE DANS CETTE SECTION
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

    suspend fun getEquipeAvecEmployes(equipeId: String): Equipe {
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
                .decodeSingle<Equipe>()
            return equipe
        } catch (e: kotlin.Exception) {
            Log.e("SupabaseClient_getEquipeAvecEmployes", e.message.toString())
        }
        return Equipe("Erreur", "Erreur", "Erreur", "Erreur", emptyList())
    }
    // ---- FIN de section de récupération des données


}