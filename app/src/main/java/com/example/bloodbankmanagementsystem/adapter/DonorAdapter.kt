package com.example.bloodbankmanagementsystem.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbankmanagementsystem.R
import com.example.bloodbankmanagementsystem.UpdateDonorActivity
import com.example.bloodbankmanagementsystem.entity.Donors
import com.example.bloodbankmanagementsystem.repository.DonorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DonorAdapter(
    private val context: Context,
    private val lstDonors: MutableList<Donors>
): RecyclerView.Adapter<DonorAdapter.DonorViewHolder>() {
    class DonorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profile: ImageView
        val dname: TextView
        val address: TextView
        val phone: TextView
        val bgroup: TextView
        val age : TextView
        val update: ImageButton
        val delete: ImageButton
        init {
            profile=view.findViewById(R.id.profile)
            dname=view.findViewById(R.id.dname)
            address=view.findViewById(R.id.address)
            phone=view.findViewById(R.id.phone)
            bgroup=view.findViewById(R.id.bgroup)
            age = view.findViewById(R.id.age)
            update=view.findViewById(R.id.update)
            delete=view.findViewById(R.id.delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_custom_layout,parent,false)
        return DonorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonorViewHolder, position: Int) {

        val donor = lstDonors[position]
        holder.dname.text=donor.fullname
        holder.address.text=donor.address
        holder.phone.text=donor.phone
        holder.bgroup.text=donor.Bgroup
        holder.age.text=donor.age

        holder.update.setOnClickListener {
            val intent = Intent(context, UpdateDonorActivity::class.java)
            intent.putExtra("Donor",donor)
            context.startActivity(intent)
        }

        holder.delete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete ${donor.fullname}")
            builder.setMessage("Are you sure do you want to delete ${donor.fullname} ?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") {_,_ ->
                deleteDonor(donor)
            }
            builder.setNegativeButton("No") {_,_ ->
                Toast.makeText(context,"Cancelled", Toast.LENGTH_SHORT).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    private fun deleteDonor(donor: Donors) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val donorRepository = DonorRepository()
                val response = donorRepository.deleteDonor(donor._id!!)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        lstDonors.remove(donor)
                        notifyDataSetChanged()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Error ${ex.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return lstDonors.size
    }
}