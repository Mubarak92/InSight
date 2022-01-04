package com.mubarak.insight

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mubarak.insight.activitys.NavActivity

class BlankFragment : Fragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let{
            val intent = Intent (it, NavActivity::class.java)
            it.startActivity(intent)
        }
    }
}
