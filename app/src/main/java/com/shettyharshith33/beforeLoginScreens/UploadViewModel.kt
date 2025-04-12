package com.shettyharshith33.beforeLoginScreens

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class UploadViewModel : ViewModel() {
    private val storageRef = Firebase.storage.reference
    private val databaseRef = Firebase.database.reference

    var questionPdfUri by mutableStateOf<Uri?>(null)
    var answerPdfUri by mutableStateOf<Uri?>(null)

    fun uploadPDFsAndProceed(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val questionUri = questionPdfUri
        val answerUri = answerPdfUri

        if (questionUri == null || answerUri == null) {
            onFailure("Please select both PDFs first.")
            return
        }

        val questionFileName = "questions/${UUID.randomUUID()}.pdf"
        val answerFileName = "answers/${UUID.randomUUID()}.pdf"

        val questionTask = storageRef.child(questionFileName).putFile(questionUri)
        val answerTask = storageRef.child(answerFileName).putFile(answerUri)

        questionTask.continueWithTask { task ->
            if (!task.isSuccessful) throw task.exception ?: Exception("Upload failed.")
            storageRef.child(questionFileName).downloadUrl
        }.addOnSuccessListener { questionUrl ->
            answerTask.continueWithTask { task ->
                if (!task.isSuccessful) throw task.exception ?: Exception("Upload failed.")
                storageRef.child(answerFileName).downloadUrl
            }.addOnSuccessListener { answerUrl ->
                // Store metadata in Realtime DB
                val data = mapOf(
                    "question_pdf_url" to questionUrl.toString(),
                    "answer_pdf_url" to answerUrl.toString()
                )
                databaseRef.child("evaluations").push().setValue(data)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure("Failed to save in DB") }
            }.addOnFailureListener {
                onFailure("Failed to upload answer key PDF.")
            }
        }.addOnFailureListener {
            onFailure("Failed to upload question paper PDF.")
        }
    }
}
