package ipvc.estg.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.room.adapters.CityAdapter
import ipvc.estg.room.entities.City
import ipvc.estg.room.viewModel.CityViewModel


class MainActivity : AppCompatActivity() {
    private val newWordActivityRequestCode = 1
    private lateinit var cityViewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CityAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //ViewModel
        cityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)
        cityViewModel.allCities.observe(this, Observer { cities ->
            // Update the cached copy of the words in the adapter.
            cities?.let { adapter.setCities(it) }
        })

        //fab
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddCity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(AddCity.EXTRA_REPLY)?.let {
                val city = City(city=it,capital = "Portugal")
                cityViewModel.insert(city)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Cidade vazia: nao inserida",
                Toast.LENGTH_LONG).show()
        }

    }
}

