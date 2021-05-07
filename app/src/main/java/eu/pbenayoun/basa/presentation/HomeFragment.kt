package eu.pbenayoun.basa.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import eu.pbenayoun.basa.R
import eu.pbenayoun.basa.databinding.ActivityMainBinding
import eu.pbenayoun.basa.databinding.FragmentHomeBinding
import eu.pbenayoun.basa.referencerepository.FetchingState
import eu.pbenayoun.basa.referencerepository.ReferencesRepositoryViewModel
import eu.pbenayoun.repository.referencesrepository.domain.WikiReferencesModel

@AndroidEntryPoint
class HomeFragment() : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? =null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var referencesRepositoryViewModel: ReferencesRepositoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        referencesRepositoryViewModel = ViewModelProvider(this).get(ReferencesRepositoryViewModel::class.java)
        setObservers()
        setViews()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers(){
        referencesRepositoryViewModel.fetchingState.observe(viewLifecycleOwner, { fetchingState ->
            when (fetchingState) {
                is FetchingState.Fetching -> {
                    binding.btnSearch.visibility= View.GONE
                    binding.progressCircular.visibility = View.VISIBLE
                }
                else -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.btnSearch.visibility = View.VISIBLE
                }
            }
            if (fetchingState is FetchingState.Error){
                val snackBarString = getString(R.string.research_error,fetchingState.referencesErrorResponseModel.query)
                snackIt(snackBarString)
                referencesRepositoryViewModel.onErrorProcessed()
            }
        })

        referencesRepositoryViewModel.lastReferencesSuccessReferencesSuccessModel.observe(viewLifecycleOwner,{ lastSuccessReferencesModel->
            val lastSearchVisibility= when(lastSuccessReferencesModel.references){
                0 -> View.GONE
                else -> View.VISIBLE
            }
            binding.txtLastSearchTitle.visibility=lastSearchVisibility
            binding.txtLastSearchContent.visibility=lastSearchVisibility
            binding.txtLastSearchContent.text=getSearchResultString(lastSuccessReferencesModel)
        })
    }


    private fun setViews() {
        binding.editSearch.doAfterTextChanged {
            referencesRepositoryViewModel.setCurrentQuery(it.toString())
        }

        binding.btnSearch.setOnClickListener { view ->
            launchSearch(view)
        }

        // to manage hardware button "Enter" via actionDone
        binding.editSearch.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d("TMP_DEBUG", "MainActivity.setViews: IME_ACTION_DONE")
                launchSearch(view)
                true
            } else {
                Log.d("TMP_DEBUG", "MainActivity.setViews: $actionId")
                false
            }
        }
    }

    private fun launchSearch(view : View){
        // hide Keyboard
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        when {
            referencesRepositoryViewModel.getCurrentQuery().isEmpty() -> snackIt(getString(R.string.empty_query_error))
            else -> referencesRepositoryViewModel.getReferences()
        }
    }

    private fun getSearchResultString(wikiReferencesSuccessSuccessModel: WikiReferencesModel) : String{
        return when(wikiReferencesSuccessSuccessModel.references)
        {
            //Trick for 0 reference : resources.getQuantityString do not manage 0 quantity properly
            0 -> resources.getString(R.string.last_search_no_result)
            else -> resources.getQuantityString(R.plurals.last_search_result,wikiReferencesSuccessSuccessModel.references, wikiReferencesSuccessSuccessModel.query, wikiReferencesSuccessSuccessModel.references)
        }
    }

    private fun snackIt(snackText: String){
        Snackbar.make(binding.root,snackText, Snackbar.LENGTH_LONG).show()

    }

}