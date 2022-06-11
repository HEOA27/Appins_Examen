package com.example.appins_examen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView id, username, contenido;
    private Button botonObtener, btnmulti;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = findViewById(R.id.etId);
        username = findViewById(R.id.etusername);
        botonObtener = findViewById(R.id.btnObtener);
        btnmulti = findViewById(R.id.btnMultimedia);
        contenido=findViewById(R.id.idcontenido);
        mQueue = Volley.newRequestQueue(this);
        botonObtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leerwebservice();
            }
        });
        btnmulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webserviceMultimedia();
            }
        });
    }

    private void leerwebservice() {
        String url = "https://graph.instagram.com/me?fields=id,username&access_token=IGQVJYd25IT0hHa1JWeC1OZAUR6cFNQak5PVkxIMEJSN3J5Q0kxemMtZAGsxbDRudDhQWjI4QXNUOTNnM0dQbWNfRGpIRG4xZAldIenFDeFQtSTlmYXZAIQnY5ODJ1QXRoNEFCM3lGU2JZASWVWUlBIRTdMQwZDZD";
        StringRequest post = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //conversion a un objeto para extraer la informacion
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    jsonObject.getString("id");
                    id.setText(jsonObject.getString("id"));
                    String user = jsonObject.getString("username");
                    username.setText(user);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(post);
    }


    private void webserviceMultimedia() {
        String url = "https://graph.instagram.com/me/media?fields=id,caption&access_token=IGQVJYd25IT0hHa1JWeC1OZAUR6cFNQak5PVkxIMEJSN3J5Q0kxemMtZAGsxbDRudDhQWjI4QXNUOTNnM0dQbWNfRGpIRG4xZAldIenFDeFQtSTlmYXZAIQnY5ODJ1QXRoNEFCM3lGU2JZASWVWUlBIRTdMQwZDZD";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        String id = data.getString("id");
                        String caption = data.getString("caption");
                        contenido.append(id + "," + caption + "\n\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            }
        });

        mQueue.add(request);

    }
}

