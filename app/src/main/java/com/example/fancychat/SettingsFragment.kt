package com.example.fancychat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            findNavController().navigateUp()
        }

        view.findViewById<View>(R.id.btnAccountSettings).setOnClickListener {
            findNavController().navigate(R.id.action_SettingsFragment_to_AccountSettingsFragment)
        }

        view.findViewById<View>(R.id.btnNotifications).setOnClickListener {
            findNavController().navigate(R.id.action_SettingsFragment_to_NotificationsFragment)
        }
    }
}
