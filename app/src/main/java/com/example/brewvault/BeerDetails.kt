package com.example.brewvault

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.brewvault.databinding.BeerDetailsBinding
import com.example.brewvault.models.BeerViewModel
import com.google.firebase.auth.FirebaseAuth


class BeerDetails : Fragment() {
    private var _binding: BeerDetailsBinding? = null
    private val binding get() = _binding!!
    private val beersViewModel: BeerViewModel by activityViewModels()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    private val args: BeerDetailsArgs by  navArgs()
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
            val name = binding.textInputEditTextName.text.toString()
            val brewery = binding.textInputEditTextBrewery.text.toString()
            val ABV = binding.textInputEditTextABV.text.toString().toDoubleOrNull() ?: 0.0
            val number = binding.textInputEditTextHowMany.text.toString().toIntOrNull() ?: 0
            val style = binding.textInputEditTextStyle.text.toString()
            val volume = binding.textInputEditTextVolume.text.toString().toDoubleOrNull() ?: 0.0
            val newBeer = Beer(beer.id, auth.currentUser?.email, brewery, name, style, ABV, volume, null, number)
            beersViewModel.updateBeer(newBeer)
            disableEditMode()
        }
        binding.deleteButton.setOnClickListener {
            val beerId = beer.id
            beersViewModel.deleteBeer(beerId)
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

    private fun disableEditMode() {
        binding.textInputEditTextBrewery.isEnabled = false
        binding.textInputEditTextName.isEnabled = false
        binding.textInputEditTextHowMany.isEnabled = false
        binding.textInputEditTextStyle.isEnabled = false
        binding.textInputEditTextABV.isEnabled = false
        binding.textInputEditTextVolume.isEnabled = false
        binding.saveButton.visibility = View.GONE
        binding.editButton.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}