package com.luckymollo.dogpark.client.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luckymollo.dogpark.client.R
import com.luckymollo.dogpark.core.base.BaseFragment
import com.luckymollo.dogpark.core.utils.createViewModel
import org.koin.android.ext.android.get

class TestFragment : BaseFragment() {

    private lateinit var testViewModel: TestViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ml_fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testViewModel = createViewModel { TestViewModel(get()) }
    }
}
