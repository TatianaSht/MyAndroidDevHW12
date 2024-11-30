package com.example.myandroiddevhw12.ui.main

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.myandroiddevhw12.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textInput.addTextChangedListener {
            binding.buttonSearch.isEnabled = binding.textInput.text.toString().length >= 3
        }

        binding.buttonSearch.setOnClickListener {
            viewModel.onStartSearch(binding.textInput.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {

                    is State.NoSearch -> {
                        binding.progressBar.isVisible = false
                        binding.buttonSearch.isEnabled = false
                        binding.resultView.text = "Отсутствуют данные для запроса"
                    }

                    is State.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.buttonSearch.isEnabled = false
                    }

                    is State.Success -> {
                        binding.progressBar.isVisible = false
                        binding.buttonSearch.isEnabled = true
                        binding.resultView.text = "По запросу '${state.resultSearch}' ничего не найдено"
                    }
                }
            }
        }
    }
}
