package com.prafullkumar.campusepulse.data.teacherRepos

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.data.adminRepos.Result
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface TeacherRepository {
    suspend fun getClassList(): Flow<Result<TeacherDetails>>
}

@Suppress("UNCHECKED_CAST")
class TeacherRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val sharedPrefManager: SharedPrefManager,
) : TeacherRepository {

    val id: String = firebaseAuth.currentUser?.email?.substringBefore('@') ?: ""

    /**
     *     used to get the list of classes of the teacher
     * */
    override suspend fun getClassList(): Flow<Result<TeacherDetails>> {
    return callbackFlow {
        trySend(Result.Loading)
        if (id.isEmpty()) {
            trySend(Result.Error(Exception("User not logged in")))
            return@callbackFlow
        }
        val docRef = firebaseFirestore.collection("teachers").document(id)
        val successListener = docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                trySend(Result.Error(e))
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val teacherDetails = TeacherDetails(
                    id = snapshot.id,
                    name = snapshot.data?.get("name") as String?,
                    branches = snapshot.data?.get("branches") as List<String>?
                )
                trySend(Result.Success(teacherDetails))
            } else {
                trySend(Result.Error(Exception("No such document")))
            }
        }
        awaitClose { successListener.remove() }
    }
}

}
data class TeacherDetails(
    val id: String?,
    val name: String?,
    val branches: List<String>?
)
