package com.github.kazenetu.multiactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * エフェクト設定ViewModel
 */
class EffectViewModel: ViewModel() {
    companion object {
        private var instance =
            ViewModelProvider.NewInstanceFactory().create(EffectViewModel::class.java)

        fun getInstance() = instance
    }

    var useEffect: Boolean = false;
}