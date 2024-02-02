package com.prafullkumar.campusepulse.adminApp.data.repositories

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.prafullkumar.campusepulse.adminApp.domain.models.Branch
import com.prafullkumar.campusepulse.adminApp.domain.models.NewBranch
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.adminApp.domain.repositories.AdminRepository
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import com.prafullkumar.campusepulse.adminApp.ui.addBranchScreen.convertToBranch
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


@Suppress("UNCHECKED_CAST")
class AdminRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val context: Context
) : AdminRepository {

    /**
     *  This function adds the student to the firebase authentication and then adds the student to the firebase firestore
     * */
    override suspend fun addStudent(student: Student, branchStrength: Long) {
        firebaseAuth.createUserWithEmailAndPassword(
            "${student.admNo}@student.com",
            "password"
        )
            .addOnSuccessListener {
                firebaseFirestore.collection("branches")
                    .document(student.branch ?: "").collection("students")
                    .document(student.admNo.toString())
                    .set(student)
                    .addOnSuccessListener {
                        firebaseFirestore.collection("branches")
                            .document(student.branch ?: "")
                            .update("strength", branchStrength + 1)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Student added successfully", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to add student", Toast.LENGTH_SHORT).show()
                    }
            }
    }
    /**
     *  This function gets the branches from the firebase firestore
     * */
    override suspend fun getBranches(): Flow<Result<MutableList<Branch>>> {
        return callbackFlow {
            try {
                trySend(Result.Loading).isSuccess
                firebaseFirestore.collection("branches")
                    .get()
                    .addOnSuccessListener { result ->
                        val branches = mutableListOf<Branch>()  // stores the branches
                        result.forEach { document ->
                            branches.add(
                                Branch(
                                    id = document.id,
                                    name = document.data["name"].toString(),
                                    strength = document.data["strength"]?.toString()?.toLong() ?: 0L,
                                    batches = document.data["batches"] as List<String>,
                                    subjects = document.data["subjects"] as List<String>
                                )
                            )
                        }
                        trySend(Result.Success(branches)).isSuccess // This is the line which send the data to the flow
                    }
                    .addOnFailureListener { exception ->
                        trySend(Result.Error(exception)).isSuccess
                    }
            } catch (e: Exception) {
                trySend(Result.Error(e))
            }
            awaitClose {  }
        }
    }

    override suspend fun addBranch(newBranch: NewBranch): Flow<Result<Boolean>> {
        return callbackFlow {
            trySend(Result.Loading)
            val branch = convertToBranch(newBranch)
            val storageRef = FirebaseStorage.getInstance().reference
            val fileRef = storageRef.child("timeTables/${branch.id}")
            val uploadTask = fileRef.putFile(newBranch.timeTable!!)
            uploadTask.addOnSuccessListener {
                branch.id?.let { it1 ->
                    firebaseFirestore.collection("branches")
                        .document(it1)
                        .set(branch)
                        .addOnSuccessListener {
                            trySend(Result.Success(true)).isSuccess
                            Toast.makeText(context, "Branch added successfully", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            trySend(Result.Error(it)).isSuccess
                        }
                }
            }.addOnFailureListener {
                trySend(Result.Error(it))
            }
            awaitClose {  }
        }
    }
}