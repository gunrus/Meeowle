package ru.tinkoff.fintech.meowle.presentation.view.fragments

import ru.tinkoff.fintech.meowle.domain.cat.Cat

interface FragmentItemClickListener {
        fun onButtonClicked(cat: Cat)
}
interface FragmentOnNavigationClose {
        fun onClosePressed()
}