package com.example.memelist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.memelist.databinding.FragmentMemeDetailsBinding
import com.example.memelist.model.IdResponse
import com.example.memelist.model.MemeItem
import com.example.memelist.model.UIState

class MemeDetailsFragment : ViewModelFragment() {

    private lateinit var binding: FragmentMemeDetailsBinding
    private val args: MemeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemeDetailsBinding.inflate(layoutInflater)
        configureObserver()
        return binding.root
    }

    private fun configureObserver() {
        viewModel.memeID.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    if (viewModel.shouldLoadId) viewModel.getMemeById(args.memeId)
                    else viewModel.setSuccessForId(args.memeItem!!)
                }
                is UIState.Error -> {
                    binding.apply {
                        pbLoadById.visibility = View.GONE
                        tvErrorText.text = state.error.message
                    }
                }
                is UIState.Success<*> -> {
                    val memeItem =
                        if (viewModel.shouldLoadId) (state.response as IdResponse).data
                        else state.response as MemeItem

                    binding.apply {
                        pbLoadById.visibility = View.GONE
                        tvTopText.text = memeItem.topText
                        tvBottomText.text = memeItem.bottomText

                        Glide.with(ivMemeImage)
                            .load(memeItem.image)
                            .into(ivMemeImage)
                    }
                }
            }
        }
    }

}