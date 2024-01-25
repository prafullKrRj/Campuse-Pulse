package com.prafullkumar.campusepulse.data.teacherRepos

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

interface TakeAttendanceRepository {

}

class TakeAttendanceRepositoryImpl (
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : TakeAttendanceRepository {

}