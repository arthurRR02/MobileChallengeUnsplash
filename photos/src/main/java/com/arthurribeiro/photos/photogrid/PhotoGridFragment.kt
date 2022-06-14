package com.arthurribeiro.photos.photogrid

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurribeiro.photos.PhotoViewModel
import com.arthurribeiro.photos.R
import com.arthurribeiro.photos.databinding.PhsFragmentPhotoGridBinding
import com.arthurribeiro.photos.model.UnsplashDTO
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoGridFragment : Fragment() {

    private var _binding: PhsFragmentPhotoGridBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PhotoViewModel by sharedViewModel()
    private var gridLayoutManager: GridLayoutManager? = null
    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) viewModel.scrolling =
                true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            handleScroll()
        }
    }

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
        setLayoutManager()
        setObservers()
        setSearch()
        if (viewModel.comingFromPhotoDetail){
            viewModel.comingFromPhotoDetail = false
            binding.recyclerView.layoutManager?.onRestoreInstanceState(viewModel.recyclerState)
        } else viewModel.getList(viewModel.page.toString(), viewModel.perPage.toString())
    }

    private fun setObservers() {
        viewModel.photoList.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                it.body()?.forEach { photo -> viewModel.adapterPhotoList.add(photo) }
                setViewsSucceeded(viewModel.adapterPhotoList)
            }
        }

        viewModel.errorValue.observe(viewLifecycleOwner) {
            setViewsError(it)
        }

        viewModel.searchedPhotoList.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                setViewsSucceeded(it.body()?.results ?: listOf())
            }
        }
    }

    private fun setLayoutManager(){
        gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.layoutManager = gridLayoutManager
    }

    private fun setViewsSucceeded(photoList: List<UnsplashDTO>) {
        with(binding) {
            binding.photoGridContainer.visibility = View.VISIBLE
            binding.errorContainer.visibility = View.GONE

            val adapter = PhotoGridAdapter(photoList, requireActivity(), ::setItemClick)
            adapter.notifyDataSetChanged()

            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(scrollListener)
            recyclerView.layoutManager?.onRestoreInstanceState(viewModel.recyclerState)
        }
    }

    private fun setViewsError(errorText: String) {
        with(binding) {
            photoGridContainer.visibility = View.GONE
            errorContainer.visibility = View.VISIBLE

            textError.text = errorText
            refreshButton.setOnClickListener {
                viewModel.getList(viewModel.page.toString(), viewModel.perPage.toString())
            }
        }
    }

    private fun setSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.resetItems()
                if (newText == "") viewModel.getList(
                    viewModel.page.toString(),
                    viewModel.perPage.toString()
                )
                else viewModel.getSearchedList(
                    viewModel.page.toString(),
                    viewModel.perPage.toString(),
                    newText ?: ""
                )
                return true
            }
        })
    }

    private fun setItemClick(unsplashDTO: UnsplashDTO) {
        viewModel.recyclerState = binding.recyclerView.layoutManager?.onSaveInstanceState()
        findNavController().navigate(PhotoGridFragmentDirections.actionPhotoGridToPhotoDetail(unsplashDTO))
    }

    private fun handleScroll() {
        viewModel.currentItem = gridLayoutManager?.childCount ?: 0
        viewModel.totalItem = gridLayoutManager?.itemCount ?: 0
        viewModel.scrollOut = gridLayoutManager?.findFirstVisibleItemPosition() ?: 0

        if (viewModel.scrolling == true && (viewModel.currentItem + viewModel.scrollOut == viewModel.totalItem)) {
            viewModel.scrolling = false
            viewModel.page++
            if (binding.searchView.query.isNotEmpty()) {
                viewModel.getSearchedList(
                    viewModel.page.toString(),
                    viewModel.perPage.toString(),
                    binding.searchView.query.toString()
                )
            } else {
                viewModel.getList(viewModel.page.toString(), viewModel.perPage.toString())
            }
        }
    }
}