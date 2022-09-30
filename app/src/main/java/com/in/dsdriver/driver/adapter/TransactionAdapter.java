package com.in.dsdriver.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.in.dsdriver.R;
import com.in.dsdriver.driver.modelclass.Transaction_Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    Context context;
    ArrayList<Transaction_Model> transction;

    public TransactionAdapter(ArrayList<Transaction_Model> tarnsaction, FragmentActivity activity) {

        this.context = activity;
        this.transction = tarnsaction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.walletpriceshow,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Transaction_Model trans = transction.get(position);

        String date = trans.getDate();

        /*SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date dateFromUser = fromUser.parse(date); // Parse it to the exisitng date pattern and return Date type
            String dateMyFormat = myFormat.format(dateFromUser); // format it to the date pattern you prefer
            holder.text_Date.setText(dateMyFormat);

        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        holder.text_Date.setText(trans.getDate());
       // holder.text_Action.setText(trans.getAction());
        holder.text_InvoiceNo.setText(trans.getInvoice_No());
        //holder.text_Date.setText(date);
        holder.text_Amount.setText("Rs "+trans.getAmount());
        holder.text_TRType.setText(trans.getTr_type());
        holder.text_Remarks.setText(trans.getRemark());

    }

    @Override
    public int getItemCount() {
        return transction.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_InvoiceNo,text_Date,text_Amount,text_TRType,text_Remarks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_InvoiceNo = itemView.findViewById(R.id.text_InvoiceNo);
            text_Date = itemView.findViewById(R.id.text_Date);
            text_Amount = itemView.findViewById(R.id.text_Amount);
            text_TRType = itemView.findViewById(R.id.text_TRType);
            text_Remarks = itemView.findViewById(R.id.text_Remarks);

        }

    }
}
