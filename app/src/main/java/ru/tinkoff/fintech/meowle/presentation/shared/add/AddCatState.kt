package ru.tinkoff.fintech.meowle.presentation.shared.add

/**
 * @author Ruslan Ganeev
 */
sealed class AddCatState(val message: String) {
    class Initial : AddCatState("")
    class Success : AddCatState("Cat added")
    class Error(message: String) : AddCatState(message)
}
