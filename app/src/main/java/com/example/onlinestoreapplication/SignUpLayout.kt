package com.example.onlinestoreapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up_layout.*

class SignUpLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_layout)

        sign_up_layout_btnLogin.setOnClickListener {
            var loginIntent = Intent(this@SignUpLayout, MainActivity::class.java)
            startActivity(loginIntent)
            finish()
        }

        sign_up_layout_btnSignUp.setOnClickListener {
            if (sign_up_layout_edtPassword.text.toString() == sign_up_layout_edtConfirmPassword.text.toString()){

                val signUpURL = "http://" + getString(R.string.url) + "/OnlineStoreApp/join_new_user.php?email=" +
                            sign_up_layout_edtEmail.text.toString() +
                            "&username=" +
                            sign_up_layout_edtUsername.text.toString() +
                            "&pass=" +
                            sign_up_layout_edtPassword.text.toString()
                val requestQ = Volley.newRequestQueue(this@SignUpLayout)
                val stringRequest = StringRequest(Request.Method.GET, signUpURL, Response.Listener {
                    response ->
                    if (response == "A user with this Email Address already exists"){
                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Message")
                        dialogBuilder.setMessage(response)
                        dialogBuilder.create().show()
                    } else {
//                        val dialogBuilder = AlertDialog.Builder(this)
//                        dialogBuilder.setTitle("Message")
//                        dialogBuilder.setMessage(response)
//                        dialogBuilder.create().show()

                        Person.email = sign_up_layout_edtEmail.text.toString()

                        Toast.makeText(this@SignUpLayout, response, Toast.LENGTH_SHORT).show()

                        val homeIntent = Intent(this@SignUpLayout, HomeScreen::class.java)
                        startActivity(homeIntent)
                        finish()
                    }
                },Response.ErrorListener {
                    error ->
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage(error.message)
                    dialogBuilder.create().show()
                })
                requestQ.add(stringRequest)

            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("Password Mismatch")
                dialogBuilder.create().show()

            }
        }
    }
}