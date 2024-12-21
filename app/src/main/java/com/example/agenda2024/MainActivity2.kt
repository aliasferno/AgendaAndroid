package com.example.agenda2024

import android.annotation.SuppressLint
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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {
    val codigos = ArrayList<String>()

    lateinit var txtnombre: EditText
    lateinit var txtapellido: EditText
    lateinit var txttelefono: EditText
    lateinit var txtcorreo: EditText
    lateinit var txtcodigo: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        txtnombre = findViewById<EditText>(R.id.txt_nombre)
        txtapellido = findViewById<EditText>(R.id.txt_apellido)
        txttelefono = findViewById<EditText>(R.id.txt_telefono)
        txtcorreo = findViewById<EditText>(R.id.txt_correo)
        txtcodigo = findViewById<EditText>(R.id.txt_dato)


        val lista = findViewById<ListView>(R.id.lista)
        lista.setOnItemClickListener{adapterView, view, i, l ->
            val elementoSeleccionado = adapterView.getItemAtPosition(i).toString()

            Log.d("elemento seleccionado", elementoSeleccionado)
            Log.d("elemento seleccionado IIII", codigos.toString())
            consultarDatos(codigos[i])
        }

        val btnconsultar = findViewById<Button>(R.id.btn_consultar)
        btnconsultar.setOnClickListener {
            consultar(lista)
        }

        val btninsertar = findViewById<Button>(R.id.btn_insertar)
        btninsertar.setOnClickListener {
            insertar(txtnombre.text.toString(), txtapellido.text.toString(), txttelefono.text.toString(), txtcorreo.text.toString())
        }

        val btndato = findViewById<Button>(R.id.btn_dato)
        btndato.setOnClickListener {
            consultarDatos(txtcodigo.text.toString())
        }

        val btneliminar = findViewById<Button>(R.id.btn_eliminar)
        btneliminar.setOnClickListener {
            eliminar(txtcodigo.text.toString())
            consultar(lista)
        }

        val btnactualizar = findViewById<Button>(R.id.btn_actualizar)
        btnactualizar.setOnClickListener {
            actualizar()
        }
    }

    private fun insertar(
        nombre: String,
        apellido: String,
        telefono: String,
        correo: String
    ) {
        // Log para verificar el inicio de la función
        Log.d("Insertar", "Iniciando la inserción con los siguientes datos: Nombre: $nombre, Apellido: $apellido, Teléfono: $telefono, Correo: $correo")

        val url = "http://192.168.3.7/webserviceAndroid/controllers/persona.controller.php"
        val datos = JSONObject()
        val codigoPersona = intent.getIntExtra("idPersona", 0)

        try {
            // Se agregan los datos esperados por el servidor
            datos.put("op", "Insertar")  // El campo "op" es el que se usa en el controlador PHP para identificar la acción
            datos.put("nombre", nombre)
            datos.put("apellido", apellido)
            datos.put("telefono", telefono)
            datos.put("correo", correo)
            datos.put("codigoPersona", codigoPersona)

            // Log para verificar los datos que se están enviando
            Log.d("Insertar", "Datos enviados al servidor: $datos")

            val rq = Volley.newRequestQueue(this)
            val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
                Response.Listener { s ->
                    try {
                        Log.d("Insertar", "Respuesta recibida del servidor: $s")

                        val obj = s
                        if (obj.getInt("estado") == 1) {
                            // Si el estado es 1, mostramos el mensaje de éxito
                            Log.d("Insertar", "Inserción exitosa: ${obj.getString("mensaje")}")
                            Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                        } else {
                            // Si el estado es 0, mostramos el mensaje de error
                            Log.d("Insertar", "Error al insertar: ${obj.getString("mensaje")}")
                            Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        // Log para capturar errores de JSON
                        Log.d("Insertar", "Error procesando la respuesta JSON: ${e.message}")
                        Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { volleyError ->
                    // Log para capturar errores de Volley
                    Log.d("Insertar", "Error de Volley: ${volleyError.message}")
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            )

            // Agregar la solicitud a la cola
            rq.add(jsor)
        } catch (e: JSONException) {
            // Log para capturar errores al crear el JSON
            Log.d("Insertar", "Error creando el JSON: ${e.message}")
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }



    private fun actualizar(){
        val url = "http://192.168.3.7/webserviceAndroid/controllers/persona.controller.php"
        val datos = JSONObject()

        datos.put("op", "Actualizar")
        datos.put("nombre", txtnombre.text.toString())
        datos.put("apellido", txtapellido.text.toString())
        datos.put("telefono", txttelefono.text.toString())
        datos.put("correo", txtcorreo.text.toString())
        datos.put("codigo", txtcodigo.text.toString().toIntOrNull())

        val rq = Volley.newRequestQueue(this)
        val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
            Response.Listener { s ->
                try {
                    val obj = (s)
                    if (obj.getInt("estado") == 1){
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e:JSONException){
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }
        )
        rq.add(jsor)
    }

    private fun eliminar(codigo: String){
        val url = "http://10.0.2.2/webserviceAndroid/controllers/persona.controller.php"
        val datos = JSONObject()

        datos.put("op", "Eliminar")
        datos.put("codigo", codigo)

        val rq = Volley.newRequestQueue(this)
        val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
            Response.Listener { s ->
                try {
                    val obj = (s)
                    if (obj.getInt("estado") == 1){
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e:JSONException){
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }
        )
        rq.add(jsor)
    }

    private fun consultarDatos(codigo: String) {
        // URL del WebService
        val url = "http://192.168.3.7/webserviceAndroid/controllers/persona.controller.php"
        val datos = JSONObject()

        try {
            // Preparando los datos para la solicitud
            datos.put("op", "consultarDatos")
            datos.put("codigo", codigo)
            Log.d("ConsultarDatos", "Datos preparados: $datos")

            // Crear la cola de peticiones
            val rq = Volley.newRequestQueue(this)

            // Crear la solicitud JsonObjectRequest
            val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
                Response.Listener { s ->
                    try {
                        Log.d("ConsultarDatos", "Respuesta recibida: $s")
                        val obj = s

                        // Verificar el estado de la respuesta
                        val estado = obj.getString("estado").toInt() == 1
                        Log.d("ConsultarDatos", "Estado: $estado")

                        if (estado) {
                            // Obtener el array de datos
                            val array = obj.getJSONArray("persona")
                            Log.d("ConsultarDatos", "Cantidad de datos recibidos: ${array.length()}")

                            // Obtener el primer objeto del array
                            val dato = array.getJSONObject(0)
                            Log.d("ConsultarDatos", "Primer dato: $dato")

                            // Asignar los datos al UI
                            txtnombre.setText(dato.getString("nom_contacto"))
                            txtapellido.setText(dato.getString("ape_contacto"))
                            txtcorreo.setText(dato.getString("email_contacto"))
                            txttelefono.setText(dato.getString("telefono_contacto"))
                            txtcodigo.setText(dato.getString("cod_contacto"))
                        } else {
                            Log.d("ConsultarDatos", "Error en la respuesta: ${obj.getString("mensaje")}")
                            Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        Log.d("ConsultarDatos", "Error procesando JSON: ${e.message}")
                        Toast.makeText(applicationContext, "Error procesando JSON: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener { volleyError ->
                    Log.d("ConsultarDatos", "Error de Volley: ${volleyError.message}")
                    Toast.makeText(applicationContext, "Error: ${volleyError.message}", Toast.LENGTH_LONG).show()
                }
            )

            // Agregar la solicitud a la cola
            rq.add(jsor)
        } catch (e: Exception) {
            Log.d("ConsultarDatos", "Error en la preparación de la solicitud: ${e.message}")
            Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun consultar(lista: ListView) {
        val al = ArrayList<String>()
        al.clear()

        // URL del WebService
        val url = "http://192.168.3.7/webserviceAndroid/controllers/persona.controller.php"

        // Crear los datos que serán enviados en el cuerpo de la solicitud
        val datos = JSONObject()
        val idPersona = intent.getIntExtra("idPersona", 0)
        datos.put("op", "consultar")
        datos.put("idPersona", idPersona)
        codigos.clear()
        // Crear la cola de peticiones
        val rq = Volley.newRequestQueue(this)

        // Crear la solicitud JsonObjectRequest
        val jsor = JsonObjectRequest(
            Request.Method.POST, url, datos,
            Response.Listener { response ->
                try {
                    val estado = response.getInt("estado")
                    if (estado == 1) {
                        // Extraer el JSONArray "datos"
                        val array = response.getJSONArray("datos")
                        for (i in 0 until array.length()) {
                            val fila = array.getJSONObject(i)
                            codigos.add(fila.getString("cod_contacto"))
                            al.add("${fila.getString("cod_contacto")} ${fila.getString("nom_contacto")} ${fila.getString("ape_contacto")} ${fila.getString("telefono_contacto")} ${fila.getString("email_contacto")}")
                        }

                        // Actualizar el ListView con los datos obtenidos
                        val la = ArrayAdapter(this, android.R.layout.simple_list_item_1, al)
                        lista.adapter = la
                        Log.d("Consultar", "ListView actualizado con ${al.size} contactos.")
                    } else {
                        val mensaje = response.getString("mensaje")
                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Log.e("Consultar", "Error procesando JSON: ${e.message}")
                    Toast.makeText(this, "Error en los datos recibidos.", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { volleyError ->
                // Manejar errores en la solicitud
                Log.e("Consultar", "Error de Volley: ${volleyError.networkResponse?.statusCode}")
                Log.e("Consultar", "Respuesta del servidor: ${String(volleyError.networkResponse?.data ?: ByteArray(0))}")
                Toast.makeText(this, "Error en la solicitud: ${volleyError.message}", Toast.LENGTH_SHORT).show()
            }
        )

        // Agregar la solicitud a la cola
        rq.add(jsor)
    }




}