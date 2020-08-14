package com.example.onlinestoreapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*

class CartProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        var cartProductsUrl = "http://192.168.68.101/OnlineStoreApp/fetch_temporary_order.php?email=${Person.email}"
        var cartProductList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonAR = JsonArrayRequest(Request.Method.GET, cartProductsUrl, null, Response.Listener {response ->

            for(joIndex in 0.until(response.length())){//id, name, price, email, amount

                cartProductList.add("${response.getJSONObject(joIndex).getInt("id")} \n ${response.getJSONObject(joIndex).getString("name")} \n" +
                        "${response.getJSONObject(joIndex).getInt("price")} \n ${response.getJSONObject(joIndex).getString("email")} \n " +
                        "${response.getJSONObject(joIndex).getInt("amount")}")

                }
            var cartProductsAdapter = ArrayAdapter(this@CartProductsActivity, android.R.layout.simple_list_item_1, cartProductList)
            cartProductsListView.adapter = cartProductsAdapter


        }, Response.ErrorListener {
            error ->
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()

        } )
        requestQ.add(jsonAR)

    }
}