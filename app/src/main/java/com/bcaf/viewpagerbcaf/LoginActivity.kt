package com.bcaf.viewpagerbcaf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSinginClient : GoogleSignInClient
    private lateinit var firebaseAuth:FirebaseAuth

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, NavBarActivity::class.java))
            finish()
        }

        if(firebaseAuth.currentUser!=null){
            startActivity(Intent(this, NavBarActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("129392762639-avsp2tv2l991osjuaan99dnljm6gubb1.apps.googleusercontent.com")
            .requestIdToken("129392762639-3f2do63bcnes5hhec1b008nd4ur2gc8p.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSinginClient= GoogleSignIn.getClient(this,gso)
        firebaseAuth = FirebaseAuth.getInstance()
        btnGoogle.setOnClickListener(View.OnClickListener {
            val signInIntent: Intent = mGoogleSinginClient.signInIntent
            startActivityForResult(signInIntent, 100)
        })

        btnLoginCustom.setOnClickListener({
            firebaseAuth.signInWithEmailAndPassword(txtEmailForm.text.toString(),txtPasswordForm.text.toString()).addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        })

        btnToRegis.setOnClickListener({
            startActivity(Intent(this, RegistrasiUser::class.java))

        })

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100){
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account:GoogleSignInAccount = task.getResult(ApiException::class.java)
                Log.d("debug :", account.toString() )
                if (account !=null){
                authToken(account)
                }
            }catch (e:ApiException){
            Log.e("Exception API ",e.message.toString())
            }

        }
    }
    fun authToken (account:GoogleSignInAccount){
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener{task ->

            if (task.isSuccessful){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }
    }
}