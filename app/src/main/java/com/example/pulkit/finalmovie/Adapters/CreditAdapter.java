package com.example.pulkit.finalmovie.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pulkit.finalmovie.Model.Credit;
import com.example.pulkit.finalmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pulkit on 8/3/2017.
 */

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditViewHolder> {

    private List<Credit> mCredit;
    private Context mContext;

    public CreditAdapter(List<Credit> mCredit, Context mContext) {
        this.mCredit = mCredit;
        this.mContext = mContext;
    }


    @Override
    public CreditAdapter.CreditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_list_item_credit, parent
                , false);
        return new CreditAdapter.CreditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CreditViewHolder holder, int position) {
        Credit credit = mCredit.get(position);
        holder.charactername.setText(credit.getCharacter());
        holder.name.setText(credit.getName());
        Picasso.with(mContext)
                .load(credit.getProfilepath())
                .placeholder(R.color.colorAccent)
                .into(holder.characterImage);
    }


    @Override
    public int getItemCount() {
        return (mCredit == null) ? 0 : 4;
    }

    public static class CreditViewHolder extends RecyclerView.ViewHolder {

        TextView charactername;
        TextView name;
        ImageView characterImage;

        public CreditViewHolder(View itemView) {
            super(itemView);
            characterImage = itemView.findViewById(R.id.characterimage);
            name = itemView.findViewById(R.id.name);
            charactername = itemView.findViewById(R.id.character);
        }
    }
}
