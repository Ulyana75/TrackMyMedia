package com.example.trackmymedia.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainListBinding.inflate(inflater, container, false)
        liveData.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter?.notifyDataSetChanged()
        })
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
        addButtonBack()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
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
        Log.d("LOL", scrollPosition.toString())
        recyclerView.scrollToPosition(scrollPosition)
    }

    private fun initViews() {
        binding.buttonAdd.setOnClickListener {
            replaceFragment(EditingFragment(typeMedia, typeLists), true)
        }
    }

    private fun getEntities() {
        GlobalScope.launch {
            liveData.postValue(AppDatabase.getInstance(APP_ACTIVITY).getMediaDao().getAll(typeMedia, typeLists).toMutableList())
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

}