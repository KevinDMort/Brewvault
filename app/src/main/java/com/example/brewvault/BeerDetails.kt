package com.example.brewvault

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.brewvault.databinding.BeerDetailsBinding


class BeerDetails : Fragment() {
    private var _binding: BeerDetailsBinding? = null
    private val binding get() = _binding!!


    private val args: BeerDetailsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = BeerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val beer = args.beer

        (requireActivity() as AppCompatActivity).supportActionBar?.title = beer.name


        binding.textInputEditTextName.setText(beer.name)
        binding.textInputEditTextBrewery.setText(beer.brewery)
        binding.textInputEditTextHowMany.setText(beer.howMany.toString())
        binding.textInputEditTextStyle.setText(beer.style)
        binding.textInputEditTextABV.setText(beer.abv.toString())
        binding.textInputEditTextVolume.setText(beer.volume.toString())

        binding.editButton.setOnClickListener {
           enableEditMode()
        }
        binding.saveButton.setOnClickListener {
            //TODO implement update beer
        }
    }
    private fun enableEditMode() {
        binding.textInputEditTextBrewery.isEnabled = true
        binding.textInputEditTextName.isEnabled = true
        binding.textInputEditTextHowMany.isEnabled = true
        binding.textInputEditTextStyle.isEnabled = true
        binding.textInputEditTextABV.isEnabled = true
        binding.textInputEditTextVolume.isEnabled = true
        binding.saveButton.visibility = View.VISIBLE
        binding.editButton.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}