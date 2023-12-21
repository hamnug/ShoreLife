package com.hamnug.shorelife.view.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.hamnug.shorelife.R
import com.hamnug.shorelife.data.local.preferences.TokenPreferences
import com.hamnug.shorelife.data.local.preferences.dataStore
import com.hamnug.shorelife.view.activity.camera.OpenCameraActivity
import com.hamnug.shorelife.view.activity.welcome.WelcomeActivity
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout = view.findViewById<ImageView>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            lifecycleScope.launch {
                clearTokenDataStore()
                requireActivity().supportFragmentManager.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                requireActivity().finish()
                startActivity(Intent(requireActivity(), WelcomeActivity::class.java))
            }
        }

        val btnCamera = view.findViewById<Button>(R.id.btn_camera)
        btnCamera.setOnClickListener {
            navigateToCameraActivity()
        }
    }

    private fun navigateToCameraActivity() {
        val intent = Intent(this@HomeFragment.requireContext(), OpenCameraActivity::class.java)
        startActivity(intent)
    }

    private suspend fun clearTokenDataStore() {
        val dataStore = TokenPreferences.getInstance(requireActivity().dataStore)
        return dataStore.clearToken()
    }
}