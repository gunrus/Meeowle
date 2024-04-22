package ru.tinkoff.fintech.meowle.presentation.shared.settings

import android.content.Context
import android.content.Intent
import ru.tinkoff.fintech.meowle.presentation.MainActivity
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity

/**
 * @author Ruslan Ganeev
 */
fun launchAuthActivity(context: Context) {
    val intent = Intent(context, AuthActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    context.startActivity(intent)
}

fun launchMainActivity(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    context.startActivity(intent)
}