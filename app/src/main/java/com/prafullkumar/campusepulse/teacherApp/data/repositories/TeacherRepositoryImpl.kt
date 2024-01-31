package com.prafullkumar.campusepulse.teacherApp.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import com.prafullkumar.campusepulse.teacherApp.domain.repositories.TeacherDetails
import com.prafullkumar.campusepulse.teacherApp.domain.repositories.TeacherRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


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