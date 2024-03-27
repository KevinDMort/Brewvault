package com.example.brewvault

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
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
    private lateinit var adapter: MyAdapter<Beer>

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

        val userEmail = auth.currentUser?.email
        if (userEmail != null) {
            beersViewModel.reload(userEmail, binding.swiperefresh)
        }
        beersViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // Handle error messages
        }
        beersViewModel.beersLiveData.observe(viewLifecycleOwner) { beers ->
            if (beers == null) {
            } else {
                adapter = MyAdapter(beers) { position ->
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
        binding.spinnerSortCriteria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                sortBeersBy(selectedItem)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter the list based on the search query
                adapter.filter.filter(newText)
                return true
            }
        })
    }
    fun sortBeersBy(criteria: String) {
        // Implement sorting logic based on the selected criteria
        val sortedBeers = when (criteria) {
            "Name" -> beersViewModel.beersLiveData.value?.sortedBy { it.name }
            "ABV" -> beersViewModel.beersLiveData.value?.sortedBy { it.abv }
            "Number" ->beersViewModel.beersLiveData.value?.sortedBy { it.howMany }
            // Add more sorting criteria as needed
            else -> beersViewModel.beersLiveData.value // Default: no sorting
        }?.toList()
        // Update RecyclerView with sorted list
        sortedBeers?.let {
            adapter.updateItems(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}