package com.coolgedu.androidtask

import android.content.res.Configuration
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.coolgedu.androidtask.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NumberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val patterns = resources.getStringArray(R.array.pattern)
        val arrayAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, patterns)
        binding.dropDown.setAdapter(arrayAdapter)

        binding.dropDown.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            handleSelection(selectedItem)
        }

        val spanCount: Int = calculateSpanCount()
        binding.recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        adapter = NumberAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun calculateSpanCount(): Int {
        val orientation = resources.configuration.orientation
        return if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            9
        } else {
            15
        }
    }

    private fun handleSelection(selectedItem: String) {
        val highlightedPositions = when (selectedItem) {
            "Odd Numbers" -> (0 until 100).filter { (it + 1) % 2 != 0 }.toSet()
            "Even Numbers" -> (0 until 100).filter { (it + 1) % 2 == 0 }.toSet()
            "Prime Numbers" -> (0 until 100).filter { isPrime(it + 1) }.toSet()
            "Fibonacci Sequence Numbers" -> generateFibonacciSequence().map { it - 1 }.toSet()
            else -> emptySet()
        }
        adapter.updateHighlightedPositions(highlightedPositions)
    }

    private fun isPrime(number: Int): Boolean {
        if (number <= 1) return false
        if (number == 2) return true
        if (number % 2 == 0) return false
        for (i in 3..sqrt(number.toDouble()).toInt() step 2) {
            if (number % i == 0) return false
        }
        return true
    }

    private fun generateFibonacciSequence(): List<Int> {
        val fibs = mutableListOf(0, 1)
        while (true) {
            val next = fibs.last() + fibs[fibs.size - 2]
            if (next >= 100) break
            fibs.add(next)
        }
        return fibs
    }
}
