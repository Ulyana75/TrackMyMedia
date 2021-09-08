package com.example.trackmymedia.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmymedia.R
import com.example.trackmymedia.databinding.FragmentMainListBinding
import com.example.trackmymedia.model.entities.MediaEntity
import com.example.trackmymedia.utilits.*
import com.example.trackmymedia.view.recycler_view.MainListAdapter
import com.example.trackmymedia.view.recycler_view.MediaDiffUtilCallback
import com.example.trackmymedia.viewModel.MainViewModel


class MainListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private var typeMedia: TypesMedia? = null
    private var typeLists: TypesLists? = null

    private lateinit var binding: FragmentMainListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var typeSort: TypesSort


    companion object {

        var scrollPosition = 0

        fun newInstance(typeMedia: TypesMedia, typeList: TypesLists): MainListFragment {
            val fragment = MainListFragment()
            fragment.arguments = bundleOf(
                key_type_media to typeMedia,
                key_type_list to typeList
            )

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        typeMedia = arguments?.get(key_type_media) as TypesMedia?
        typeLists = arguments?.get(key_type_list) as TypesLists?

        viewModel = ViewModelProvider(APP_ACTIVITY).get(MainViewModel::class.java)
        viewModel.cleanValue()

        binding = FragmentMainListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (typeMedia != null && typeLists != null) {
            typeSort = viewModel.initSortState(typeMedia!!, typeLists!!)
            viewModel.getEntities(typeMedia!!, typeLists!!)
            initViews()
        }
        addButtonBack()
        setHasOptionsMenu(true)
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveSortState(typeMedia!!, typeLists!!)
    }

    private fun initViews() {
        binding.buttonAdd.setOnClickListener {
            replaceFragment(EditingFragment.newInstance(typeMedia!!, typeLists!!), true)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerView
        adapter = MainListAdapter(
            listOf(),
            this::listClickListener,
            this::listLongClickListener
        )
        layoutManager = LinearLayoutManager(APP_ACTIVITY)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollPosition = layoutManager.findFirstVisibleItemPosition()
            }
        })

        viewModel.mediaLiveData.observe(this) {
            val diffResult = DiffUtil.calculateDiff(
                MediaDiffUtilCallback(
                    adapter.getData(),
                    it
                )
            )
            adapter.setData(it)

            diffResult.dispatchUpdatesTo(adapter)
        }

        recyclerView.scrollToPosition(scrollPosition)
    }

    private fun listClickListener(item: MediaEntity) {
        replaceFragment(EditingFragment.newInstance(item.type, item.typeList, item), true)
    }

    private fun listLongClickListener(item: MediaEntity, view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.item_context_menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    viewModel.delete(item) {
                        viewModel.getEntities(typeMedia!!, typeLists!!)
                    }
                }
            }
            true
        }

        popup.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuInflater: MenuInflater = APP_ACTIVITY.menuInflater
        menuInflater.inflate(R.menu.main_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (typeLists == TypesLists.PLANNING) {
            menu.findItem(R.id.sort_by_rating_bad_first).isEnabled = false
            menu.findItem(R.id.sort_by_rating_good_first).isEnabled = false
        } else {
            menu.findItem(R.id.sort_by_rating_bad_first).isEnabled = true
            menu.findItem(R.id.sort_by_rating_good_first).isEnabled = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        typeSort =
            when (item.itemId) {
                R.id.sort_by_date_new_first -> TypesSort.SORT_BY_DATE_NEW_FIRST
                R.id.sort_by_date_old_first -> TypesSort.SORT_BY_DATE_OLD_FIRST
                R.id.sort_by_alphabet_a -> TypesSort.SORT_BY_ALPHABET_A
                R.id.sort_by_alphabet_z -> TypesSort.SORT_BY_ALPHABET_Z
                R.id.sort_by_rating_good_first -> TypesSort.SORT_BY_RATING_GOOD_FIRST
                R.id.sort_by_rating_bad_first -> TypesSort.SORT_BY_RATING_BAD_FIRST
                else -> typeSort
            }
        viewModel.setSortType(typeSort, typeMedia!!, typeLists!!)
        return super.onOptionsItemSelected(item)
    }

}