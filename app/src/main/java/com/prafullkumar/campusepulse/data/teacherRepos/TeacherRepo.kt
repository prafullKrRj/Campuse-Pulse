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
    fun signOut()
}

@Suppress("UNCHECKED_CAST")
class TeacherRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val sharedPrefManager: SharedPrefManager
) : TeacherRepository {

    val id: String = firebaseAuth.currentUser?.email?.substringBefore('@') ?: ""

    /**
     *     used to get the list of classes of the teacher
     * */
    override suspend fun getClassList(): Flow<Result<TeacherDetails>> {
        return callbackFlow {
            trySend(Result.Loading)
            firebaseFirestore.collection("teachers")
                .document(id)
                .get()
                .addOnSuccessListener {
                    val teacherDetails = TeacherDetails(
                        id = it.id,
                        name = it.data?.get("name") as String?,
                        branches = it.data?.get("branches") as List<String>?
                    )
                    trySend(Result.Success(teacherDetails))
                }
                .addOnFailureListener {
                    trySend(Result.Error(it))
                }
            awaitClose { }
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
        sharedPrefManager.setLoggedIn(false)
        sharedPrefManager.setLoggedInUserType("")
    }
}
data class TeacherDetails(
    val id: String?,
    val name: String?,
    val branches: List<String>?
)
