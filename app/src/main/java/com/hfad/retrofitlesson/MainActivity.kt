package com.hfad.retrofitlesson
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.hfad.retrofitlesson.databinding.ActivityMainBinding
import com.hfad.retrofitlesson.retrofit.AuthRequest
import com.hfad.retrofitlesson.retrofit.MainApi
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val mainApi = retrofit.create(MainApi::class.java)

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val user = mainApi.auth(
                    AuthRequest(
                        binding.usarename.text.toString(),
                        binding.password.text.toString()
                    )


                )
                runOnUiThread {
                    binding.apply {
                        Picasso.get().load(user.image).into(iv)
                        firstName.text = user.firstName
                        lastName.text = user.lastName
                    }

                }
            }
        }
    }
}

