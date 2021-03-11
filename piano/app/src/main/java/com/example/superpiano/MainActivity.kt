package com.example.superpiano

import android.content.ContentValues.TAG
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.superpiano.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private val TAG:String = "superpiano.MainActivity"
    private lateinit var piano:PianoLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        signInAnonymously()

        piano = supportFragmentManager.findFragmentById(binding.piano.id) as PianoLayout

        piano.onSave = {
            this.uploadFile(it)
        }

    }

    private fun signInAnonymously(){
        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG,"Loging success ${it.user.toString()}" )
            }.addOnFailureListener{
                Log.e(TAG, "Login failed", it)
        }
    }

    private fun uploadFile(file: Uri){
        Log.d(TAG, "Upload file $file")

        val ref = FirebaseStorage.getInstance().reference.child("song/${file.lastPathSegment}")
        val uploadTask = ref.putFile(file)

        uploadTask.addOnSuccessListener {
            Log.d(TAG, "Saved file to Firebase ${it.toString()}")
        }.addOnFailureListener{
            Log.e(TAG,"Error saving file to Firebase", it)
        }

    }
}