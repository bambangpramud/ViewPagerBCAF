package com.bcaf.recycleview.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewParent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.viewpagerbcaf.MainActivity
import com.bcaf.viewpagerbcaf.adapter.BCAFListListener
import com.bcaf.viewpagerbcaf.data.model.User
import com.bcaf.viewpagerbcaf.fragment.Fragment3
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.itemuser.view.*

class BCAFViewHolder (view: View,parent: BCAFListListener):RecyclerView.ViewHolder(view){


//val txtDesc = view.txtAlamatUser
//    val gambar = view.imgProduct
//
//    fun setData(context:Context,data:MainActivity.Product,position: Int){
//        txtDesc.setText(data.description)
//        Glide.with(context).load(data.gambar).into(gambar)
//    }
val view = view;
    val db = Firebase.firestore
    val parent = parent

    fun setData(context:Context,data:User,position:Int){

        view.txtNamaUserItemUser.setText(data.name)
        view.txtTeleponUser.setText(data.telepon)
        view.txtAlamatUser.setText(data.alamat)


        Glide.with(context)
            .load(data.gambar)
            .into(view.imgProduct)

        view.btnDelete.setOnClickListener({

            db.document("user/"+data.id).delete().addOnCompleteListener{
                parent.onRefresh()
                Toast.makeText(context,"data " +data.name+" berhasil di hapus",Toast.LENGTH_LONG).show()

//               loadFragment(Fragment3.newInstance("",""))
            }.addOnFailureListener{
                exception ->
                Log.w("error",exception.message.toString())
            }

        })
    }



//    fun loadFragment(fragment : Fragment){
//        val fragManager = supportFragmentManager.beginTransaction()
//        fragManager.replace(R.id.vFragment,fragment)
//        fragManager.addToBackStack("")
//        fragManager.commit()
//    }
}