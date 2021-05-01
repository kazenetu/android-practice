package com.github.kazenetu.listview.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.github.kazenetu.listview.databinding.FragmentDoneBinding
import com.github.kazenetu.listview.domain.domain.TodoItemInterface
import com.github.kazenetu.listview.view.recyclerView.RowItem
import com.github.kazenetu.listview.view.viewmodels.DoneViewModel
import org.koin.android.ext.android.inject

/**
 * DONEリスト用Fragment
 */
class DoneFragment : RecyclerViewFragment() {
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!

    /**
     * TodoViewModelのインスタンス
     */
    private val doneViewModel: DoneViewModel by inject()

    /**
     * fragment生成
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModelの更新監視
        doneViewModel.listItems.asLiveData().observe(this, {
            it.let{adapter.setList(it)}
        })
    }

    /**
     * UI描画
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        // Inflate the layout for this fragment
        _binding = FragmentDoneBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.superConstraintLayout.addView(super.getRecyclerViewLayout())

        return view
    }

    override fun onItemClickEvent(view: View, position: Int, value: TodoItemInterface){
        callDetail(position, RowItem(value.showImage,value.title,value.detail,value.isDone))
    }

    override fun onItemDoneClickEvent(view: View, position: Int, value: TodoItemInterface) {
        doneViewModel.updateDone(position, !value.isDone)
    }

    /**
     * UI削除
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * インスタンス生成
         */
        @JvmStatic
        fun newInstance() =
            DoneFragment().apply {
            }
    }
}