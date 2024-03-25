package com.example.brewvault

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brewvault.databinding.BeerlistBinding
import com.example.brewvault.models.BeerViewModel
import com.google.firebase.auth.FirebaseAuth

class Beerlist : Fragment() {

    private var _binding: BeerlistBinding? = null
    private val binding get() = _binding!!

    private val beersViewModel: BeerViewModel by activityViewModels()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = BeerlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userEmail = auth.currentUser.email
        beersViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
        }
        beersViewModel.beersLiveData.observe(viewLifecycleOwner) { beers ->
            if (beers == null) {
            } else {
                val adapter = MyAdapter(beers) { position ->
                    val beer: Beer? = beersViewModel.get(position)
                    if (beer == null) {
                        return@MyAdapter
                    }
                    Log.d("Her sendt", beer.toString())
                    val action = BeerlistDirections.actionBeerlistToBeerDetails(beer)
                    findNavController().navigate(action)
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_Beerlist_to_newBeer)
        }
        binding.swiperefresh.setOnRefreshListener {
            auth.currentUser?.email?.let { beersViewModel.reload(it,binding.swiperefresh) };

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}