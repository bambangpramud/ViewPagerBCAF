package com.bcaf.viewpagerbcaf

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bcaf.viewpagerbcaf.fragment.Fragment1
import com.bcaf.viewpagerbcaf.fragment.Fragment2
import com.bcaf.viewpagerbcaf.fragment.Fragment3
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_nav_bar.*
import kotlinx.android.synthetic.main.fragment_2.*
import lib.android.imagepicker.ImagePicker

class NavBarActivity : AppCompatActivity(),ImagePicker.OnImageSelectedListener,CameraInterface
    , NavigationView.OnNavigationItemSelectedListener{
    private lateinit var firebaseAuth : FirebaseAuth
    lateinit var imagePicker: ImagePicker
    lateinit var imagePathFile: String
    var toogle : ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_bar)
        imagePicker= ImagePicker(AppCompatActivity(),BuildConfig.APPLICATION_ID)
        imagePicker.setImageSelectedListener(this)

        setupNavigationDrawer()
        showFirstFragment()
//        imagePicker.takePhotoFromCamera()


    }

    fun showFirstFragment(){
        val ft:FragmentTransaction = supportFragmentManager.beginTransaction()

        ft.add(R.id.container,Fragment1.newInstance("",""))
        ft.commit()
    }

    fun setupNavigationDrawer(){
        setSupportActionBar(toolbar)
        toogle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toogle!!)
        navigationMenu.setNavigationItemSelectedListener(this)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
      var fragment : Fragment? = null

        when(item.itemId){
            R.id.itemWeather -> {
                fragment = Fragment1.newInstance("","")
            }
            R.id.itemMovie -> {
                fragment = Fragment2.newInstance("","")
            }
            R.id.itemSettings -> {
                fragment = Fragment3.newInstance("","")
            }
            R.id.itemAbout -> {
                Toast.makeText(this,"About dipanggil",Toast.LENGTH_LONG).show()
            }
            R.id.itemLogout -> {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("129392762639-avsp2tv2l991osjuaan99dnljm6gubb1.apps.googleusercontent.com")
                    .requestIdToken("129392762639-3f2do63bcnes5hhec1b008nd4ur2gc8p.apps.googleusercontent.com")
                    .requestEmail()
                    .build()
                firebaseAuth = FirebaseAuth.getInstance()
                var mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

                mGoogleSignInClient.signOut().addOnCompleteListener{
                    firebaseAuth.currentUser?.delete()
                    firebaseAuth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()

                }
            }

        }

        drawerLayout.closeDrawer(GravityCompat.START)
        if (fragment!=null){
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container,fragment)
            ft.commit()
        }

        return true

    }

    override fun onImageSelectFailure() {
        Toast.makeText(applicationContext,"Ambil gambar gagal",Toast.LENGTH_LONG).show()
    }

    override fun onImageSelectSuccess(imagePath: String) {
        imagePathFile = imagePath
        Log.d("Photo File", imagePath)
        imgphoto?.let {
            Glide.with(this@NavBarActivity)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .load(imagePath)
                .into(it)
        }
//        Glide.with(this).load(imagePath).into(imgProfileUser);
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        imagePicker.onRequestPermissionsResult(requestCode,permissions,grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun openCamera() {

        imagePicker.takePhotoFromCamera()
    }

    override fun getImagePath(): String {
      return imagePathFile
    }
}