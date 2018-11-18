package com.example.zeronone.myapplication;

import android.content.Intent;
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

public class UpdateActivity extends AppCompatActivity {
    EditText name,contact,email;
    Button add;
    String empid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name =(EditText)findViewById(R.id.name);
        email =(EditText)findViewById(R.id.email);
        contact =(EditText)findViewById(R.id.contact);
        add =(Button)findViewById(R.id.add);
        Intent intent = getIntent();
        empid = intent.getStringExtra("emp_id");

        name.setText(intent.getStringExtra("name"));
        email.setText(intent.getStringExtra("email"));
        contact.setText(intent.getStringExtra("phone_no"));



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty()|| contact.getText().toString().isEmpty()){
                    Toast.makeText(UpdateActivity.this, "Add All ata", Toast.LENGTH_SHORT).show();
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


        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Util.Update_Employee+empid, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d("sad", response.toString());
                            Toast.makeText(UpdateActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateActivity.this,ListEmployee.class);
                            startActivity(intent);
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
                Toast.makeText(UpdateActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(jsonObjReq);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateActivity.this,ListEmployee.class);
        startActivity(intent);
        finish();
    }
}
