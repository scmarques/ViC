package com.sandyara.aula18

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {

    lateinit private var textAlert : TextView
    private val message = "Ainda em construção."

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textAlert = view.findViewById(R.id.txt_sec_screen)
        textAlert.text = message

        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()


}
    override fun onResume() {
        super.onResume()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    }
}