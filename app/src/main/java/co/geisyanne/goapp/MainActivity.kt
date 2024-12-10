package co.geisyanne.goapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geisyanne.goapp.databinding.ActivityMainBinding
import co.geisyanne.goapp.ui.travelRequest.TravelRequestFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment_main, TravelRequestFragment())
                .commit()
        }

    }


}