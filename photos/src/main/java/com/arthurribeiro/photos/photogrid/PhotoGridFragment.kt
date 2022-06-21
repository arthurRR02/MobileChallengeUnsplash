package com.arthurribeiro.photos.photogrid

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurribeiro.photos.PhotoViewModel
import com.arthurribeiro.photos.databinding.PhsFragmentPhotoGridBinding
import com.arthurribeiro.photos.model.UnsplashDTO
import com.arthurribeiro.photos.util.isConnected
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PhotoGridFragment : Fragment() {

    private var _binding: PhsFragmentPhotoGridBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PhotoViewModel by sharedViewModel()
    private lateinit var adapter: PhotoGridAdapter
    private var recyclerState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PhsFragmentPhotoGridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        verifyConnection()
        setViews()
    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }

    private fun setObservers() {
        viewModel.photos.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                it.collectLatest { adapter.submitData(it) }
            }
        }
    }

    private fun setViews() {
        with(binding) {
            binding.photoGridContainer.visibility = View.VISIBLE
            binding.noConnectionErrorContainer.visibility = View.GONE

            adapter = PhotoGridAdapter(requireActivity(), ::setItemClick)
            adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            val gridLayoutManager = GridLayoutManager(requireContext(), 3)
            recyclerView.layoutManager = gridLayoutManager

            setSearch()

            recyclerView.adapter = adapter
        }
    }

    private fun verifyConnection() {
        if (isConnected(requireContext())) {
            viewModel.getPhotos()
            setViews()
        }
        else handleNoConnectionState()
    }

    private fun handleNoConnectionState() {
        with(binding) {
            photoGridContainer.visibility = View.GONE
            setVisibility(false)
            Handler(Looper.getMainLooper()).postDelayed({
                setVisibility(true)
            }, 2000)
            buttonNoConnection.setOnClickListener {
                verifyConnection()
            }
        }
    }

    private fun setVisibility(isVisible: Boolean){
        with(binding){
            noConnectionErrorContainer.isVisible = isVisible
            progress.isVisible = !isVisible
        }
    }

    private fun setSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getPhotos(newText ?: "")
                return true
            }
        })
    }


    private fun setItemClick(unsplashDTO: UnsplashDTO) {
        findNavController().navigate(
            PhotoGridFragmentDirections.actionPhotoGridToPhotoDetail(
                unsplashDTO
            )
        )
    }
}