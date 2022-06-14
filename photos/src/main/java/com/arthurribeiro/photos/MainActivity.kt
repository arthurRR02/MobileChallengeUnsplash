package com.arthurribeiro.photos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.arthurribeiro.photos.databinding.ActivityMainPhotosBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainPhotosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPhotosBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
            configureNavGraph()
        }
    }

    private fun configureNavGraph(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController =  navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.photoGridFragment)
        navController.graph = navGraph
    }
}