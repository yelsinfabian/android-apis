package com.example.miapp0.modelos;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.miapp0.MainActivity;
import com.example.miapp0.helpers.queueutils;
import com.example.miapp0.lista_productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class producto {
    private String imagen;
    private String nombre;
    private String precio;
    private String descripcion;


    //constructor de la clase producto

    public producto(String _imagen, String _nombre, String _precio, String _descripcion){
        this.imagen = _imagen;
        this.nombre = _nombre;
        this.precio = _precio;
        this.descripcion = _descripcion;
    }

    //metodos de la clase producto

    public String getImagen() {return this.imagen; }

    public String getNombre() {return nombre; }

    public String getPrecio() {return precio; }

    public String getDescripcion() {return descripcion; }

    public static ArrayList getCollection() {
        ArrayList<producto> collection = new ArrayList<>();
        collection.add(new producto("", "Bichito", "18", "pollarida"));
        collection.add(new producto("", "Bichito", "18", "pollarida"));
        collection.add(new producto("", "Bichito", "18", "pollarida"));
        return collection;
    }

    //hacer metodo post
    public static void sendRequestPOST(queueutils.QueueObject o, final lista_productos _interface) {
        String url = "http://rrojasen.alwaysdata.net/purchaseorders.json";
        url = "http://fipo.equisd.com/api/users/new.json";
        //url = "http://192.168.58.3:8056/api/users/new.json";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Do it with this it will work
                            JSONObject _response = new JSONObject(response);
                            if (_response.has("object")) {
                                JSONObject object_response = null;
                                try {
                                    object_response = _response.getJSONObject("object");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if ( object_response != null ) {
                                    try {
                                        System.out.println(object_response.getInt("id"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("first_name","yelsin");
                params.put("last_name","fabian");
                params.put("avatar","xxx");

                return params;
            }
        };
        o.addToRequestQueue(stringRequest);
    }


//Metodo get
    public static void injectContactsFromCloud(final queueutils.QueueObject o,
                                               final ArrayList<producto> contactos,
                                               final lista_productos _interface) {
        String url = "http://fipo.equisd.com/api/users.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("objects")) {

                            try {
                                JSONArray list = response.getJSONArray("objects");
                                for (int i=0; i < list.length(); i++) {
                                    JSONObject o = list.getJSONObject(i);
                                    contactos.add(new producto(o.getString("first_name"),
                                            o.getString("last_name"),"",""));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            _interface.refreshList(); // Esta función debemos implementarla
                            // en nuestro activity
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        o.addToRequestQueue(jsonObjectRequest);
    }


}
