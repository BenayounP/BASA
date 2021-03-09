package eu.pbenayoun.wikireferences2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import eu.pbenayoun.wikireferences2.databinding.ActivityMainBinding
import eu.pbenayoun.wikireferences2.referencerepository.FetchingState
import eu.pbenayoun.wikireferences2.referencerepository.ReferencesRepositoryViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var referencesRepositoryViewModel: ReferencesRepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        referencesRepositoryViewModel = ViewModelProvider(this).get(ReferencesRepositoryViewModel::class.java)
        setViews()
    }


    private fun setViews() {
        // observers
        referencesRepositoryViewModel.fetchingState.observe(this, Observer { fetchingState ->
            when (fetchingState) {
                is FetchingState.Fetching -> binding.progressCircular.visibility = View.VISIBLE
                is FetchingState.Idle -> binding.progressCircular.visibility = View.GONE
            }
        })

        binding.editSearch.doAfterTextChanged {
            referencesRepositoryViewModel.setCurrentQuery(it.toString())
        }

        binding.btnSearch.setOnClickListener { editTextView ->
            // hide Keyboard
            (editTextView.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager).hideSoftInputFromWindow(editTextView.windowToken, 0)
            referencesRepositoryViewModel.getReferences()
        }
    }


}