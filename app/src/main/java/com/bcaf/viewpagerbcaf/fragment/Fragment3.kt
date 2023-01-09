package com.bcaf.viewpagerbcaf.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcaf.bcafrecycleview.adapter.BCAFAdapter
import com.bcaf.viewpagerbcaf.R
import com.bcaf.viewpagerbcaf.adapter.BCAFListListener
import com.bcaf.viewpagerbcaf.data.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_3.*
import kotlinx.android.synthetic.main.itemuser.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment3 : Fragment(),BCAFListListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        loadData()


        return view


    }

    fun loadData(){
        val db = Firebase.firestore

        val datas = arrayListOf<User>()



        db.collection("user")
            .get()
            .addOnSuccessListener{
                result ->
                for (document in result){
                    datas.add(User(document.id, document.data.get("nama").toString(),document.data.get("alamat").toString(),document.data.get("telepon").toString(),document.data.get("gambar").toString()))

//                    btnDelete.setOnClickListener({
//                        db.document("user/"+document.id).delete()
//                    })
                }

                var bcafAdapter = BCAFAdapter(datas)
                bcafAdapter.setListener(this)
                recycleView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = bcafAdapter
                }
            }
            .addOnFailureListener{ exception ->
                Log.w("error","Error getting documents.",exception)
            }
    }

    override fun onRefresh(){
loadData()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}