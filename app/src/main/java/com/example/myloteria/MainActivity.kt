package com.example.myloteria

import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myloteria.databinding.ActivityMainBinding
import com.example.myloteria.viewmodel.CardViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var _cardViewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        _cardViewModel = ViewModelProvider(this)[CardViewModel::class.java]

//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery/*, R.id.nav_slideshow*/
            ), drawerLayout
        )
        loadCards()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun loadCards() {
        _cardViewModel.createCard(1, R.drawable.el_gallo_1, "El Gallo")
        _cardViewModel.createCard(2, R.drawable.el_diablito_2, "El Diablito")
        _cardViewModel.createCard(3, R.drawable.la_dama_3, "La Dama")
        _cardViewModel.createCard(4, R.drawable.el_catrin_4, "El Catrin")
        _cardViewModel.createCard(5, R.drawable.el_paraguas_5, "El Paraguas")
        _cardViewModel.createCard(6, R.drawable.la_sirena_6, "La Sirena")
        _cardViewModel.createCard(7, R.drawable.la_escalera_7, "La Escalera")
        _cardViewModel.createCard(8, R.drawable.la_botella_8, "La Botella")
        _cardViewModel.createCard(9, R.drawable.el_barril_9, "El Barril")
        _cardViewModel.createCard(10, R.drawable.el_arbol_10, "El Arbol")
        _cardViewModel.createCard(11, R.drawable.el_melon_11, "El Melon")
        _cardViewModel.createCard(12, R.drawable.el_valiente_12, "El Valiente")
        _cardViewModel.createCard(13, R.drawable.el_gorrito_13, "El Gorrito")
        _cardViewModel.createCard(14, R.drawable.la_muerte_14, "La Muerte")
        _cardViewModel.createCard(15, R.drawable.la_pera_15, "La Pera")
        _cardViewModel.createCard(16, R.drawable.la_bandera_16, "La Bandera")
        _cardViewModel.createCard(17, R.drawable.el_bandolon_17, "El Bandolon")
        _cardViewModel.createCard(18, R.drawable.el_violoncello_18, "El Violoncello")
        _cardViewModel.createCard(19, R.drawable.la_garza_19, "La Garza")
        _cardViewModel.createCard(20, R.drawable.el_pajaro_20, "El Pajaro")
        _cardViewModel.createCard(21, R.drawable.la_mano_21, "La Mano")
        _cardViewModel.createCard(22, R.drawable.la_bota_22, "La Bota")
        _cardViewModel.createCard(23, R.drawable.la_luna_23, "La Luna")
        _cardViewModel.createCard(24, R.drawable.el_cotorro_24, "El Cotorro")
        _cardViewModel.createCard(25, R.drawable.el_borracho_25, "La Borracho")
        _cardViewModel.createCard(26, R.drawable.el_negrito_26, "El Negrito")
        _cardViewModel.createCard(27, R.drawable.el_corazon_27, "El Corazon")
        _cardViewModel.createCard(28, R.drawable.la_sandia_28, "La Sandia")
        _cardViewModel.createCard(29, R.drawable.el_tambor_29, "El Tambor")
        _cardViewModel.createCard(30, R.drawable.el_camaron_30, "El Camaron")
        _cardViewModel.createCard(31, R.drawable.las_jaras_31, "Las Jaras")
        _cardViewModel.createCard(32, R.drawable.el_musico_32, "El Musico")
        _cardViewModel.createCard(33, R.drawable.la_arana_33, "La Arana")
        _cardViewModel.createCard(34, R.drawable.el_soldado_34, "El Soldado")
        _cardViewModel.createCard(35, R.drawable.la_estrella_35, "La Estrella")
        _cardViewModel.createCard(36, R.drawable.el_cazo_36, "El Cazo")
        _cardViewModel.createCard(37, R.drawable.el_mundo_37, "El Mundo")
        _cardViewModel.createCard(38, R.drawable.el_apache_38, "El Apache")
        _cardViewModel.createCard(39, R.drawable.el_nopal_39, "El Nopal")
        _cardViewModel.createCard(40, R.drawable.el_alacran_40, "El Alacran")
        _cardViewModel.createCard(41, R.drawable.la_rosa_41, "La Rosa")
        _cardViewModel.createCard(42, R.drawable.la_calavera_42, "La Calavera")
        _cardViewModel.createCard(43, R.drawable.la_campana_43, "La Campana")
        _cardViewModel.createCard(44, R.drawable.el_cantarito_44, "El Canarito")
        _cardViewModel.createCard(45, R.drawable.el_venado_45, "El Venado")
        _cardViewModel.createCard(46, R.drawable.el_sol_46, "El Sol")
        _cardViewModel.createCard(47, R.drawable.la_corona__47, "La Corona")
        _cardViewModel.createCard(48, R.drawable.la_chalupa_48, "La Chalupa")
        _cardViewModel.createCard(49, R.drawable.el_pino_49, "El Pino")
        _cardViewModel.createCard(50, R.drawable.el_pescado_50, "El Pescado")
        _cardViewModel.createCard(51, R.drawable.la_palma_51, "La Palma")
        _cardViewModel.createCard(52, R.drawable.la_maceta_52, "La Maceta")
        _cardViewModel.createCard(53, R.drawable.el_arpa_53, "El Arpa")
        _cardViewModel.createCard(54, R.drawable.la_rana_54, "La Rana")

        Cards.addCards(_cardViewModel.retrieveCards())
    }
}
