package eu.pbenayoun.wikireferences2

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import eu.pbenayoun.repository.referencesrepository.ReferencesSuccessModel
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
        referencesRepositoryViewModel.fetchingState.observe(this, { fetchingState ->
            when (fetchingState) {
                is FetchingState.Fetching -> binding.progressCircular.visibility = View.VISIBLE
                else -> binding.progressCircular.visibility = View.GONE
            }
            if (fetchingState is FetchingState.Error){
                val snackbarString = getString(R.string.research_error)
                Snackbar.make(binding.root,snackbarString, Snackbar.LENGTH_LONG).show()
            }
        })

        referencesRepositoryViewModel.lastReferencesSuccessReferencesModel.observe(this,{ lastSuccessReferencesModel->
            var lastSearchVisibility= when(lastSuccessReferencesModel.references){
                0 -> View.GONE
                else -> View.VISIBLE
            }
            binding.txtLastSearchTitle.visibility=lastSearchVisibility
            binding.txtLastSearchContent.visibility=lastSearchVisibility
            binding.txtLastSearchContent.text=getSearchResultString(lastSuccessReferencesModel)
        })


        // views

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

    private fun getSearchResultString(referencesSuccessModel: ReferencesSuccessModel) : String{
        return when(referencesSuccessModel.references)
        {
            //Trick for 0 reference : resources.getQuantityString do not manage 0 quantity properly
            0 -> resources.getString(R.string.last_search_no_result)
            else -> resources.getQuantityString(R.plurals.last_search_result,referencesSuccessModel.references, referencesSuccessModel.query, referencesSuccessModel.references)
        }
    }


}