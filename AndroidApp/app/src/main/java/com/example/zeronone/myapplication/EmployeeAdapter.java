package com.example.zeronone.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {

    JSONArray employdata;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, email;
        public CardView card_view;
        public ImageView deleteicon;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            card_view = (CardView) view.findViewById(R.id.card_view);
            deleteicon = (ImageView)view.findViewById(R.id.delete);

        }
    }


    public EmployeeAdapter(JSONArray employdata) {
        this.employdata = employdata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false);
        context = itemView.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final JSONObject jsonObject ;
        try{
            jsonObject = employdata.getJSONObject(position);

            holder.name.setText(jsonObject.getString("name"));
            holder.email.setText(jsonObject.getString("email"));

            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Intent intent =new Intent(context,UpdateActivity.class);
                        intent.putExtra("emp_id",jsonObject.getString("emp_id"));
                        intent.putExtra("name",jsonObject.getString("name"));
                        intent.putExtra("email",jsonObject.getString("email"));
                        intent.putExtra("phone_no",jsonObject.getString("phone_no"));
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }catch (Exception e){
                        Log.d("sad",e.getMessage());
                    }

                }
            });

            holder.deleteicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        //Yes button clicked
                                        try{
                                            deleterecode(jsonObject.getString("emp_id"));
                                        }catch (Exception e){
                                            Log.d("sad",e.getMessage());
                                        }
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                }
            });

        }catch (Exception e){
            Log.d("sad",e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return employdata.length();
    }

   public void deleterecode(String empid){
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.POST,
                Util.Delete_Empoyee+empid, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            Log.d("sad", response.toString());
                          //  Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                            employdata = response;
                            notifyDataSetChanged();
//                            finish();
                        }catch (Exception E){
                            Log.d("sad",E.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                // hide the progress dialog
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(jsonObjReq);

    }
}
