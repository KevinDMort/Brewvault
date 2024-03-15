package com.example.brewvault

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val beer = args.beer
        super.onViewCreated(view, savedInstanceState)
        binding.textViewNameValue.text = beer.name
        binding.textViewBreweryValue.text = beer.brewery
        binding.textViewStyleValue.text = beer.style
        binding.textViewABVValue.text = beer.abv.toString()


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}