package com.example.agenda2024

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnlogin = findViewById<Button>(R.id.btn_login)

        btnlogin.setOnClickListener {
            val cedula = findViewById<EditText>(R.id.ci_persona).text.toString()
            val clave = findViewById<EditText>(R.id.clave).text.toString()
            login(cedula, clave)
        }
    }

    private fun login(cedula: String, clave: String) {
        val url = "http://192.168.3.7/webserviceAndroid/controllers/persona.controller.php" // Usa la IP correcta de tu máquina local

        // Crear los datos que serán enviados en el cuerpo de la solicitud
        val datos = JSONObject()
        datos.put("op", "consultarDato")
        datos.put("cedula", cedula) // Usando los valores que se pasan como parámetro
        datos.put("clave", clave)   // Usando los valores que se pasan como parámetro

        // Crear la cola de peticiones
        val rq = Volley.newRequestQueue(this)

        // Crear la solicitud JsonObjectRequest
        val jsor = JsonObjectRequest(
            Request.Method.POST, url, datos,
            { response ->
                try {
                    // Analizar la respuesta del servidor
                    val obj = response

                    // Convertir el valor de "estado" de 1/0 a un booleano
                    val estado = obj.getString("estado").toInt() == 1

                    // Verificar si la respuesta contiene un estado positivo
                    if (estado) {
                        val intent = Intent(this, MainActivity2::class.java)
                        intent.putExtra("idPersona", obj.getInt("cod_persona"))
                        startActivity(intent)
                        finish()
                    } else {
                        // Si la respuesta es negativa, mostrar el mensaje
                        Toast.makeText(
                            applicationContext,
                            obj.getString("mensaje"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    // Manejar posibles excepciones durante el parseo del JSON
                    Toast.makeText(
                        applicationContext,
                        "Error en los datos recibidos",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("LoginError", "JSONException: ${e.message}")
                }
            },
            { error ->
                // Manejar errores de la solicitud
                Toast.makeText(
                    applicationContext,
                    "Error en la solicitud: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("LoginError", "VolleyError: ${error.message}")
            }
        )

        // Agregar la solicitud a la cola
        rq.add(jsor)
    }
}