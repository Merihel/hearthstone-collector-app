package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.lpiem.hearthstonecollectorapp.Manager.FragmentToolbar
import com.example.lpiem.hearthstonecollectorapp.Manager.ToolbarManager

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ToolbarManager(builder(), view).prepareToolbar()
    }

    protected abstract fun builder(): FragmentToolbar
}