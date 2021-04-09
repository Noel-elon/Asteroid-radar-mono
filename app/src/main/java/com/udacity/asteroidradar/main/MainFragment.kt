package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapters.AsteroidAdapter
import com.udacity.asteroidradar.asDomainEntity
import com.udacity.asteroidradar.asPicDomain
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import timber.log.Timber

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            Factory(requireActivity().application)
        ).get(MainViewModel::class.java)
    }

    lateinit var adapter: AsteroidAdapter

    private var asteroidDaily = listOf<Asteroid>()
    private var asteroidWeekly = listOf<Asteroid>()
    private var asteroidAll = listOf<Asteroid>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        adapter = AsteroidAdapter {
            val argument = bundleOf(Pair("selectedAsteroid", it))
            findNavController().navigate(R.id.action_showDetail, argument)
        }

        setHasOptionsMenu(true)

        viewModel.dailyAsteroids.observe(viewLifecycleOwner, Observer {
            asteroidDaily = it.asDomainEntity()
        })

        viewModel.weeklyAsteroids.observe(viewLifecycleOwner, Observer {
            asteroidWeekly = it.asDomainEntity()
        })

        viewModel.picOfDay.observe(viewLifecycleOwner, Observer {
            if (it !== null) {
                Picasso.with(requireContext()).load(it.url).into(binding.activityMainImageOfTheDay)
                binding.picture = it.asPicDomain()
            }

        })

        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it.asDomainEntity())
            asteroidAll = it.asDomainEntity()
            binding.asteroidRecycler.adapter = adapter
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_all_menu -> {
                adapter.updateList(asteroidWeekly)
                return true
            }
            R.id.show_buy_menu -> {
                adapter.updateList(asteroidAll)
                return true
            }
            R.id.show_rent_menu -> {
                adapter.updateList(asteroidDaily)
                return true
            }
        }
        return false
    }
}
