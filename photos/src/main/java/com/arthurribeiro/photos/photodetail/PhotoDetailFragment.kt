package com.arthurribeiro.photos.photodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arthurribeiro.photos.PhotoViewModel
import com.arthurribeiro.photos.databinding.PhsFragmentPhotoDetailBinding
import com.arthurribeiro.photos.util.getImageFromUrl
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PhotoDetailFragment : Fragment(){

    private var _binding: PhsFragmentPhotoDetailBinding? = null
    private val binding get() = _binding!!

    private val args by lazy {
        val args: PhotoDetailFragmentArgs by navArgs()
        args.unsplashDTO
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PhsFragmentPhotoDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActions()
        setViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setViews(){
        with(binding){
            image.getImageFromUrl(requireActivity(), args.urls?.small ?: ""){
                image.setImageDrawable(it)
            }

            photoDetailSponsor.text = args.sponsorship?.sponsor?.name ?: "-"
            photoDetailDescription.text = args.description ?: "-"
            photoDetailLikes.text = args.likes.toString()
        }
    }

    private fun setActions(){
        binding.icReturn.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}