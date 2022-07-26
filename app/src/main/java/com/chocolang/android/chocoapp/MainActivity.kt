package com.chocolang.android.chocoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chocolang.android.chocoapp.databinding.ActivityMainBinding
import com.chocolang.android.chocoapp.repository.ui.fragment.ListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    enum class FragmentTagType(val title: String, val type: Int) {
        REPOSITORY("REPOSITORY", 0),
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, getFragment(FragmentTagType.REPOSITORY))
                .commit()
        }
    }

    private fun getFragment(tag: FragmentTagType): Fragment {
        val fragment = supportFragmentManager.findFragmentByTag(tag.title)
        return if (fragment == null) {
            when (tag) {
                FragmentTagType.REPOSITORY -> ListFragment.newInstance()
            }
        } else {
            fragment
        }
    }
}