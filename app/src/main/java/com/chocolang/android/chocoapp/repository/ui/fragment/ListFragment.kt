package com.chocolang.android.chocoapp.repository.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chocolang.android.chocoapp.databinding.FragmentListBinding
import com.chocolang.android.chocoapp.viewmodel.RepositoryViewModel
import com.chocolang.android.chocoapp.BR.repositoryViewModel
import com.chocolang.android.chocoapp.ListAdapter
import com.chocolang.android.chocoapp.R
import com.chocolang.android.chocoapp.repository.SharedValue
import com.chocolang.android.chocoapp.repository.model.GitRepositoryList
import com.chocolang.android.chocoapp.repository.ui.dialog.LoadingDialog
import com.chocolang.android.chocoapp.repository.ui.dialog.PathDialog

class ListFragment : BaseListFragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var sharedValue: SharedValue

    private lateinit var listLayoutManager: LinearLayoutManager
    private lateinit var listAdapter: ListAdapter

    private lateinit var viewModel: RepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedValue = SharedValue(requireContext()).also {
            val path = it.getPath()
        }

        viewModel = ViewModelProvider(this)[RepositoryViewModel::class.java]
        val resultObserver = Observer<List<GitRepositoryList>?> { result ->
            if(result != null) {
                listAdapter.addAll(result)
                sharedValue.setPath(viewModel.searchPath.value)
            } else {
                var builder = AlertDialog.Builder(requireContext())
                builder.setMessage("repository 를 찾지 못했습니다.");
                builder.setPositiveButton("확인") { p0, p1 ->
                    viewModel.setSplitSearchPath(viewModel.oldSearchPath)
                }
                builder.show()
            }
        }
        val isLoadingObserver = Observer<Boolean> { isLoading ->
            loadingDialog?.run {
                if (isLoading) show() else dismiss()
            }
        }
        val pathObserver = Observer<String> { _ ->
            listAdapter.clear()
            viewModel.getRepositoryList()
        }
        viewModel.repo = repo
        viewModel.result.observe(viewLifecycleOwner, resultObserver)
        viewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
        viewModel.searchPath.observe(viewLifecycleOwner, pathObserver)

        listLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listAdapter = ListAdapter(requireContext())
        binding.tvPath.setOnClickListener {
            pathDialog?.show()
            pathDialog?.setOrg(viewModel.getSplitSearchPath()[0])
            pathDialog?.setRepo(viewModel.getSplitSearchPath()[1])
        }
        binding.rvList.apply {
            layoutManager = listLayoutManager
            adapter = listAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = listLayoutManager.itemCount
                    val lastVisibleItemPosition =
                        listLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (((totalItemCount - 1) == lastVisibleItemPosition) && totalItemCount != 0) {
                        viewModel.getRepositoryList()
                    }
                }
            })
        }
        binding.setVariable(repositoryViewModel, viewModel)

        loadingDialog = LoadingDialog(requireContext())
        pathDialog = PathDialog(
            requireContext(),
            viewModel.getSplitSearchPath()[0],
            viewModel.getSplitSearchPath()[1]
        ) { org, repo ->
            viewModel.setSplitSearchPath("$org/$repo")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}