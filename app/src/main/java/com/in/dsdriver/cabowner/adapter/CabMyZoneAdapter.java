package com.in.dsdriver.cabowner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.dsdriver.R;
import com.in.dsdriver.cabowner.modelclass.CabMyZone_ModelClass;

import java.util.ArrayList;

public class CabMyZoneAdapter extends RecyclerView.Adapter<CabMyZoneAdapter.ViewHolder> {

    Context context;
    ArrayList<CabMyZone_ModelClass> cabMyZone;

    public CabMyZoneAdapter(Context context, ArrayList<CabMyZone_ModelClass> cabMyZone) {

        this.context = context;
        this.cabMyZone = cabMyZone;

    }

    @NonNull
    @Override
    public CabMyZoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cabowner_myzone,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CabMyZoneAdapter.ViewHolder holder, int position) {

        CabMyZone_ModelClass cabmyzone = cabMyZone.get(position);

        holder.text_Date.setText(cabmyzone.getJob_date());
        holder.text_Locality.setText("Locality : "+cabmyzone.getLocality());
        holder.text_Landmark.setText("Landmark : "+cabmyzone.getLandmark());
        holder.text_JobType.setText(cabmyzone.getJob_type());
        holder.text_ZipCode.setText(cabmyzone.getZip());
        holder.text_City.setText(cabmyzone.getCity());
        holder.text_Address.setText(cabmyzone.getAddress());

    }

    @Override
    public int getItemCount() {
        return cabMyZone.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_Locality,text_Landmark,text_Date,text_JobType,text_ZipCode,text_City,text_Address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_Address = itemView.findViewById(R.id.text_Address);
            text_Locality = itemView.findViewById(R.id.text_Locality);
            text_Landmark = itemView.findViewById(R.id.text_Landmark);
            text_Date = itemView.findViewById(R.id.text_Date);
            text_JobType = itemView.findViewById(R.id.text_JobType);
            text_ZipCode = itemView.findViewById(R.id.text_ZipCode);
            text_City = itemView.findViewById(R.id.text_City);

        }
    }
}
