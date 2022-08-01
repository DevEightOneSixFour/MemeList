package com.example.memelist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.memelist.databinding.FragmentMemeListBinding
import com.example.memelist.model.MemeItem
import com.example.memelist.model.MemeResponse
import com.example.memelist.model.UIState

class MemeListFragment: ViewModelFragment() {
    lateinit var binding: FragmentMemeListBinding

    private val memeAdapter by lazy {
        MemeAdapter(openDetails = ::openDetails)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemeListBinding.inflate(layoutInflater)
        configureObserver()
        return binding.root
    }

    private fun configureObserver() {
        viewModel.memeListData.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.Loading -> {
                    viewModel.getDemMemes(viewModel.currentPage)
                    binding.rvMemes.adapter = memeAdapter
                    binding.rvMemes.addOnScrollListener(object: RecyclerView.OnScrollListener(){
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            if (!recyclerView.canScrollVertically(1)) {
                                viewModel.getDemMemes(++viewModel.currentPage)
                            }
                        }
                    })
                }
                is UIState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvLoadingText.text = uiState.error.message
                }
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvLoadingText.visibility = View.GONE
                        memeAdapter.setMemeList((uiState.response as MemeResponse).data)
                    }
                }
            }
        }
    }

    private fun openDetails(memeItem: MemeItem) {
        viewModel.setLoadingForDetails()
        findNavController().navigate(
            MemeListFragmentDirections.actionMemeListToMemeDetail(
                memeItem,
                0
            )
        )
    }
}