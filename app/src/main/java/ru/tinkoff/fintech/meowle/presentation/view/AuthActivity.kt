package ru.tinkoff.fintech.meowle.presentation.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.databinding.ActivityAuthBinding
import ru.tinkoff.fintech.meowle.presentation.MainActivity
import ru.tinkoff.fintech.meowle.presentation.shared.auth.AuthViewModel
import kotlin.math.PI
import kotlin.random.Random

/**
 * @author Ruslan Ganeev
 */
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        val binding: ActivityAuthBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.load()

        val chance = Random.nextInt(0, 99)

        if (chance < 2) {
            binding.submitButton.pivotX = 0f
            binding.submitButton.rotation = 3f
            val submitButtonWidth = windowManager.defaultDisplay.width - (resources.getDimension(R.dimen.medium_horizontal_margin) * 2)
            val distance = ((PI * submitButtonWidth)/ 180) * binding.submitButton.rotation
            binding.sleepyCat.translationY = (binding.sleepyCat.translationY + distance).toFloat()
            binding.sleepyCat.rotation = 3f
        }

        lifecycleScope.launch {
            viewModel.authState.collect { auth ->
                if (auth) {
                    Log.i("Auth", "Login success")
                    launchMainActivity()
                }
            }
        }
    }

    private fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }


    private fun showNotification(context: Context, title: String?, message: String?, intent: Intent?, reqCode: Int) {
        val CHANNEL_ID = "channel_name" // The id of the channel.
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.cat)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Channel Name" // The user-visible name of the channel.
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(reqCode, notificationBuilder.build()) // 0 is the request code, it should be unique id
        Log.d("showNotification", "showNotification: $reqCode")
    }
}
