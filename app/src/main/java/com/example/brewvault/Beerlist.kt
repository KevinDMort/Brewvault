package com.example.brewvault

import BeerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brewvault.databinding.BeerlistBinding

class Beerlist : Fragment() {

    private var _binding: BeerlistBinding? = null
    private val binding get() = _binding!!

    private val beerViewModel: BeerViewModel by viewModels()
    private lateinit var beerAdapter: BeerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = BeerlistBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        beerAdapter = BeerAdapter()
        binding.recyclerView.adapter = beerAdapter

        // Observe beer list changes
        beerViewModel.beerList.observe(viewLifecycleOwner) { beerList ->
            // Update RecyclerView adapter with the new beer list
            beerAdapter.submitList(beerList)
        }

        // Fetch beer data
        beerViewModel.fetchBeerData()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}