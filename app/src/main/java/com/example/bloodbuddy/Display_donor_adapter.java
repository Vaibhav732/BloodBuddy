package com.example.bloodbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Display_donor_adapter extends RecyclerView.Adapter<Display_donor_adapter.MyViewHolder>  {
    Context context;
    ArrayList<Donor_info> donor_infos;
    public Display_donor_adapter(Context c , ArrayList<Donor_info> r){

        context = c;
        donor_infos = r;
    }

    @NonNull
    @Override
    public Display_donor_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Display_donor_adapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.display_requester_card,  parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Display_donor_adapter.MyViewHolder holder, int position) {
        holder.name.setText("Name: " + donor_infos.get(position).getName());
        holder.phone.setText("Contact: " + donor_infos.get(position).getPhone());
        holder.email.setText("E-mail id: " + donor_infos.get(position).getEmail());
        holder.address.setText("Addresss: " + donor_infos.get(position).getAddress());
        holder.blood_grp.setText("Blood Group: " + donor_infos.get(position).getBlood_grp());
    }

    @Override
    public int getItemCount() {
        return donor_infos.size();
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
