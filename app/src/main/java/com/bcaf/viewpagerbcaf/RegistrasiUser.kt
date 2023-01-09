package com.bcaf.viewpagerbcaf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registrasi_user.*

class RegistrasiUser : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi_user)
        FirebaseApp.initializeApp(this)
        firebaseAuth=FirebaseAuth.getInstance()

        btnRegis.setOnClickListener({
            if( !txtEmailRegis.text.toString().equals("")&&   !txtPasswordRegis.text.toString().equals("")
                &&   !txtPasswordRegisConfirm.text.toString().equals("")    ){
                if ( txtPasswordRegis.text.toString().equals(txtPasswordRegisConfirm.text.toString()) && txtPasswordRegis.text.toString().length>=6){
                    createUser()
                }else{
                    Toast.makeText(this,"maaf password tidak sama",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"maaf form harus terisi semua",Toast.LENGTH_LONG).show()
            }
        })


    }

    fun createUser(){
        firebaseAuth.createUserWithEmailAndPassword(txtEmailRegis.text.toString(),txtPasswordRegis.text.toString()).addOnCompleteListener{
            task ->

            if (task.isSuccessful){

                Toast.makeText(this,"User berhasil di buat",Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}