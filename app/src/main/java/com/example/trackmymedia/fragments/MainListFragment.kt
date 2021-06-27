package com.example.trackmymedia.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmymedia.R
import com.example.trackmymedia.database.AppDatabase
import com.example.trackmymedia.database.entities.MediaEntity
import com.example.trackmymedia.databinding.FragmentMainListBinding
import com.example.trackmymedia.recycler_view.MainListAdapter
import com.example.trackmymedia.utilits.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainListFragment(private val typeMedia: TypesMedia, private val typeLists: TypesLists) :
    Fragment() {

    private lateinit var binding: FragmentMainListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val liveData = MutableLiveData<MutableList<MediaEntity>>()

    private val idMenuDelete = 1
    private var selectedView: View? = null

    private var typeSort: TypesSort = TypesSort.SORT_BY_DATE_OLD_FIRST

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainListBinding.inflate(inflater, container, false)
        liveData.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter?.notifyDataSetChanged()
        })
        initSort()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
        addButtonBack()
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    override fun onStop() {
        super.onStop()
        saveSortState()
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerView
        adapter = MainListAdapter(liveData)
        layoutManager = LinearLayoutManager(APP_ACTIVITY)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollPosition = layoutManager.findFirstVisibleItemPosition()
            }
        })
        getEntities()
        recyclerView.scrollToPosition(scrollPosition)
    }

    private fun initViews() {
        binding.buttonAdd.setOnClickListener {
            replaceFragment(EditingFragment(typeMedia, typeLists), true)
        }
    }

    private fun getEntities() {
        GlobalScope.launch {
            liveData.postValue(sortData(AppDatabase.getInstance(APP_ACTIVITY).getMediaDao().getAll(typeMedia, typeLists).toMutableList()))
        }
    }

    companion object {
        var scrollPosition = 0
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(Menu.NONE, idMenuDelete, Menu.NONE, "Удалить")
        selectedView = v
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(selectedView != null) {
            when (item.itemId) {
                idMenuDelete -> {
                    GlobalScope.launch {
                        val id = selectedView!!.contentDescription.toString().toInt()
                        val entity = AppDatabase.getInstance(APP_ACTIVITY).getMediaDao().findById(id)
                        liveData.value?.remove(entity)
                        AppDatabase.getInstance(APP_ACTIVITY).getMediaDao().delete(entity)
                        getEntities()
                    }
                }
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuInflater: MenuInflater = APP_ACTIVITY.menuInflater
        menuInflater.inflate(R.menu.main_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if(typeLists == TypesLists.PLANNING) {
            menu.findItem(R.id.sort_by_rating_bad_first).isEnabled = false
            menu.findItem(R.id.sort_by_rating_good_first).isEnabled = false
        } else {
            menu.findItem(R.id.sort_by_rating_bad_first).isEnabled = true
            menu.findItem(R.id.sort_by_rating_good_first).isEnabled = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        typeSort =
            when(item.itemId) {
                R.id.sort_by_date_new_first -> TypesSort.SORT_BY_DATE_NEW_FIRST
                R.id.sort_by_date_old_first -> TypesSort.SORT_BY_DATE_OLD_FIRST
                R.id.sort_by_alphabet_a -> TypesSort.SORT_BY_ALPHABET_A
                R.id.sort_by_alphabet_z -> TypesSort.SORT_BY_ALPHABET_Z
                R.id.sort_by_rating_good_first -> TypesSort.SORT_BY_RATING_GOOD_FIRST
                R.id.sort_by_rating_bad_first -> TypesSort.SORT_BY_RATING_BAD_FIRST
                else -> typeSort
            }
        liveData.postValue(sortData(liveData.value))
        return super.onOptionsItemSelected(item)
    }

    private fun sortData(list: MutableList<MediaEntity>?): MutableList<MediaEntity>? {
        when(typeSort) {
            TypesSort.SORT_BY_DATE_NEW_FIRST -> list?.sortByDescending { it.date }
            TypesSort.SORT_BY_DATE_OLD_FIRST -> list?.sortBy { it.date }
            TypesSort.SORT_BY_ALPHABET_A -> list?.sortBy { it.name }
            TypesSort.SORT_BY_ALPHABET_Z -> list?.sortByDescending { it.name }
            TypesSort.SORT_BY_RATING_GOOD_FIRST -> list?.sortByDescending { it.rating }
            TypesSort.SORT_BY_RATING_BAD_FIRST -> list?.sortBy { it.rating }
        }
        return list
    }

    private fun saveSortState() {
        val sPref = APP_ACTIVITY.getPreferences(Context.MODE_PRIVATE)
        val editor = sPref.edit()
        editor.putString(typeMedia.toString() + typeLists.toString(), typeSort.toString())
        editor.apply()
    }

    private fun initSort() {
        val sPref = APP_ACTIVITY.getPreferences(Context.MODE_PRIVATE)
        val type = sPref.getString(typeMedia.toString() + typeLists.toString(), typeSort.toString())
        typeSort = TypesSort.valueOf(type!!)
    }

}