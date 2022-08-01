package com.example.memelist.view

import androidx.fragment.app.Fragment
import com.example.memelist.di.DI

// final or closed
// Every class in Kotlin by default is considered final
// final means it cannot be inherited
// we can use the 'open' keyword to make the class inheritable
open class ViewModelFragment: Fragment() {

    protected val viewModel by lazy {
        DI.provideViewModel(requireActivity())
    }
}