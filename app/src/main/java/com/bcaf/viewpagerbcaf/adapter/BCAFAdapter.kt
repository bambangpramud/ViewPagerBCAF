package com.bcaf.bcafrecycleview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.recycleview.adapter.BCAFViewHolder
import com.bcaf.viewpagerbcaf.R
import com.bcaf.viewpagerbcaf.adapter.BCAFListListener
import com.bcaf.viewpagerbcaf.data.model.User


class BCAFAdapter(val user:List<User>):RecyclerView.Adapter<BCAFViewHolder>() {

    lateinit var context:Context;
    lateinit var listenerBcaf: BCAFListListener
//    var ganjil:Boolean = true;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BCAFViewHolder {
        context = parent.context;


        return BCAFViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.itemuser,parent,false),listenerBcaf);


    }

    override fun onBindViewHolder(holder: BCAFViewHolder, position: Int) {
        holder.setData(context,user.get(position),position)
    }

    override fun getItemCount(): Int {
        return user.size;
    }

    fun setListener(recyclerView: BCAFListListener){
        listenerBcaf=recyclerView
    }


//
//    override fun getItemViewType(position: Int): Int {
//        if (position % 2 ==0){
//            return 0;
//        }else{
//            return 1;
//        }
//    }

}