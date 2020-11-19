package com.github.kazenetu.listview.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kazenetu.listview.R

/**
 * A simple [Fragment] subclass.
 * Use the [DoneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoneFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DoneFragment().apply {
            }
    }
}