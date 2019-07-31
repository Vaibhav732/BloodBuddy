package com.example.bloodbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Display_requester_adapter extends RecyclerView.Adapter<Display_requester_adapter.MyViewHolder> {

    Context context;
    ArrayList<Requester_info> requester_infos;
    public Display_requester_adapter(Context c , ArrayList<Requester_info> r){

        context = c;
        requester_infos = r;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.display_requester_card,  parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText("Name: " +requester_infos.get(position).getName());
        holder.phone.setText("Contact: " +requester_infos.get(position).getPhone());
        holder.email.setText("E-mail id: " +requester_infos.get(position).getEmail());
        holder.address.setText("Addresss: " + requester_infos.get(position).getAddress());
        holder.blood_grp.setText("Blood Group: " + requester_infos.get(position).getBlood_grp());
    }

    @Override
    public int getItemCount() {
        return requester_infos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, phone, email, address, blood_grp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.requester_name_card);
            phone= (TextView) itemView.findViewById(R.id.requester_phone_card);
            email = (TextView) itemView.findViewById(R.id.requester_email_card);
            address = (TextView) itemView.findViewById(R.id.requester_address_card);
            blood_grp = (TextView) itemView.findViewById(R.id.requester_blood_card);


        }
    }




}
