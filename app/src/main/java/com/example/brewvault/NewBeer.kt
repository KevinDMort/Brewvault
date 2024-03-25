package com.example.brewvault

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.brewvault.databinding.NewbeerBinding
import com.example.brewvault.models.BeerViewModel
import com.google.firebase.auth.FirebaseAuth

class NewBeer : Fragment() {

    private var _binding: NewbeerBinding? = null
    private val binding get() = _binding!!
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val beersViewModel: BeerViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = NewbeerBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val brewery = binding.editTextBrewery.text.toString()
            val ABV = binding.editTextABV.text.toString().toDoubleOrNull() ?: 0.0
            val number = binding.editTextNumber.text.toString().toIntOrNull() ?: 0
            val style = binding.editTextStyle.text.toString()
            val volume = binding.editTextVolume.text.toString().toDoubleOrNull() ?: 0.0
            val newBeer = Beer(0, auth.currentUser?.email, brewery, name, style, ABV, volume, null, number)
            beersViewModel.saveBeer(newBeer)
        }

        }

}