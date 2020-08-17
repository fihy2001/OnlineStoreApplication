package com.example.onlinestoreapplication

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_e_products.*

class FetchEProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_e_products)

        val selectedBrand:String? = intent.getStringExtra("BRAND")
        txtBrandName.text = "Products of $selectedBrand"
        var productsList = ArrayList<EProduct>()

        val productsUrl =
            "http://" + getString(R.string.url) + "/OnlineStoreApp/fetch_eproducts.php?brand=$selectedBrand"
        val requestQ = Volley.newRequestQueue(this@FetchEProductsActivity)
        val jsonAR = JsonArrayRequest(Request.Method.GET, productsUrl, null, Response.Listener{response ->

            for (productJOIndex in 0.until(response.length())){

                productsList.add(EProduct(response.getJSONObject(productJOIndex).getInt("id"),
                response.getJSONObject(productJOIndex).getString("name"),
                response.getJSONObject(productJOIndex).getInt("price"),
                response.getJSONObject(productJOIndex).getString("picture")))

            }

            val pAdapter = EProductAdapter(this@FetchEProductsActivity, productsList)
            productsRV.layoutManager = LinearLayoutManager(this@FetchEProductsActivity)
            productsRV.adapter = pAdapter

        }, Response.ErrorListener { error ->
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })

        requestQ.add(jsonAR)

    }
}