package com.example.noteretrieve

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappfull.MainActivity
import com.example.noteappfull.NoteInfo
import com.example.noteappfull.R
import kotlinx.android.synthetic.main.itemlayout.view.*


class adapteritem(val activity: MainActivity, val input:ArrayList<NoteInfo>) : RecyclerView.Adapter<adapteritem.itemViewHolder>() {

    class itemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {

        var obj = input[position]

        holder.itemView.apply {

            tv1.text = obj.note
            imD.setOnClickListener {
                activity.DeleteNote(obj.index)
            }
            imE.setOnClickListener {
                activity.dialog(obj.index)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        return itemViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.itemlayout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = input.size

}





