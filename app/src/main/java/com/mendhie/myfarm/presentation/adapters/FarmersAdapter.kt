package com.mendhie.myfarm.presentation.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mendhie.myfarm.R
import com.mendhie.myfarm.data.models.Farmer
import com.mendhie.myfarm.databinding.ItemFarmerBinding
import com.mendhie.myfarm.presentation.interfaces.OnDeleteListener

class FarmersAdapter(private val listener: OnDeleteListener): ListAdapter<Farmer, FarmersAdapter.FarmerViewHolder>(FarmerDiffUtilCallback) {
    object FarmerDiffUtilCallback : DiffUtil.ItemCallback<Farmer>() {
        override fun areItemsTheSame(oldItem: Farmer, newItem: Farmer): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Farmer, newItem: Farmer): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmerViewHolder {
        val binding = ItemFarmerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FarmerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FarmerViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class FarmerViewHolder(private val binding: ItemFarmerBinding): ViewHolder(binding.root){
        fun onBind(farmer: Farmer){
            binding.apply {
                txtName.text = buildString { append(farmer.firstName)
                    append(" ")
                    append(farmer.lastName)
                }
                txtCropType.text = farmer.cropName
                txtInitials.text = buildString {
                    append(farmer.firstName[0].toString())
                    append(farmer.lastName[0])
                }

                root.setOnLongClickListener {
                    displayDialogDelete(farmer.id)
                    true
                }
            }

        }

        private fun displayDialogDelete(farmerId: Int) {
            val builder = AlertDialog.Builder(binding.root.context)
            val dialogView = LayoutInflater.from(binding.root.context).inflate(R.layout.dialog_farmer_delete, null)
            builder.setView(dialogView)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val btnDelete = dialog.findViewById<Button>(R.id.btnClose)
            val imgClose = dialog.findViewById<ImageView>(R.id.imgClose)
            btnDelete?.setOnClickListener {
                dialog.cancel()
                listener.onDeleteFarmer(farmerId)
            }
            imgClose?.setOnClickListener {
                dialog.cancel()
            }
        }
    }
}