package com.poppo.practise.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.poppo.practise.R

class Fragment4: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("life_cycle", "F onCreateView")

        // Fragment가 인터페이스를 처음으로 그릴 때 호출된다.
        // 그리는 역할을 여기서 하면 된다.
        return inflater.inflate(R.layout.fragment_one, container, false)
    }
}