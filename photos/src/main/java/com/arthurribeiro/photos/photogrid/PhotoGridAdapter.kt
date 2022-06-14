package com.arthurribeiro.photos.photogrid

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthurribeiro.photos.databinding.PhsPhotoItemBinding
import com.arthurribeiro.photos.model.UnsplashDTO
import com.arthurribeiro.photos.util.getImageFromUrl

class PhotoGridAdapter(
    private val photoList: List<UnsplashDTO>,
    private val activity: Activity,
    private val clickCallBack: ((UnsplashDTO) -> Unit)? = null,
) :
    RecyclerView.Adapter<PhotoGridAdapter.PhotoGridViewHolder>() {

    inner class PhotoGridViewHolder(private val binding: PhsPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: UnsplashDTO) {
            binding.phsImage.apply {
                setOnClickListener {
                    clickCallBack?.invoke(photo)
                }
                getImageFromUrl(activity, photo.urls?.small ?: "") {
                    binding.phsImage.setImageDrawable(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridViewHolder {
        val binding =
            PhsPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoGridViewHolder, position: Int) {
        val item = photoList[position]
        holder.bind(item)
    }

    override fun getItemCount() = photoList.size
}