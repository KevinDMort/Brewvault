package com.example.brewvault

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brewvault.databinding.LoginBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Login : Fragment() {

    private var _binding: LoginBinding? = null
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {

            val email = binding.editTextEmail.text?.trim().toString()
            if (email.isEmpty()) {
                binding.editTextEmail.error = "No email"
                return@setOnClickListener
            }
            val password = binding.editTextPassword.text?.trim().toString()
            if (password.isEmpty()) {
                binding.editTextPassword.error = "No password"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        Log.d("APPLE", "login:success")
                        //updateUI(user)
                        findNavController().navigate(R.id.action_Login_to_Beerlist)
                    } else {
                        Log.d("APPLE", "login:failed", task.exception)
                        binding.textViewErrorMessage.text =
                            "Authentication failed: " + task.exception?.message
                    }
                }
        }
        binding.buttonCreateUser.setOnClickListener {
            val email = binding.editTextEmail.text?.trim().toString()
            if (email.isEmpty()) {
                binding.editTextEmail.error = "No email"
                return@setOnClickListener
            }
            val password = binding.editTextPassword.text?.trim().toString()
            if (password.isEmpty()) {
                binding.editTextPassword.error = "No password"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("APPLE", "createUserWithEmail:success")
                        val user = auth.currentUser
                        //updateUI(user)
                        findNavController().navigate(R.id.action_Login_to_Beerlist)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("APPLE", "createUserWithEmail:failure", task.exception)
                        binding.textViewErrorMessage.text =
                            "Registration failed: " + task.exception?.message
                        //updateUI(null)
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}