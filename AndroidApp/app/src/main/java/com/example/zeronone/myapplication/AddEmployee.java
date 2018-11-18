package com.example.zeronone.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class AddEmployee extends AppCompatActivity {

    EditText name,contact,email;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        name =(EditText)findViewById(R.id.name);
        email =(EditText)findViewById(R.id.email);
        contact =(EditText)findViewById(R.id.contact);
        add =(Button)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty()|| contact.getText().toString().isEmpty()){
                    Toast.makeText(AddEmployee.this, "Add All ata", Toast.LENGTH_SHORT).show();
                }else{
                    adddata();
                }
            }
        });
    }

    void adddata(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("name",name.getText().toString());
            jsonObject.put("email",email.getText().toString());
            jsonObject.put("contact",contact.getText().toString());
        }catch (Exception E){

        }


        RequestQueue  mRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Util.ADD_Employee, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d("sad", response.toString());
                            Toast.makeText(AddEmployee.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                            finish();
                        }catch (Exception E){
                            Log.d("sad",E.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                // hide the progress dialog
                Toast.makeText(AddEmployee.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(jsonObjReq);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
