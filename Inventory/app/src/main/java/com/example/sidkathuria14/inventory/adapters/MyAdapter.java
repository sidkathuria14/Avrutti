package com.example.sidkathuria14.inventory.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sidkathuria14.inventory.DataBaseHandler;
import com.example.sidkathuria14.inventory.R;
import com.example.sidkathuria14.inventory.interfaces.OnItemClickListener;
import com.example.sidkathuria14.inventory.interfaces.OnLongClickedListener;
import com.example.sidkathuria14.inventory.models.Item;

import java.util.ArrayList;

/**
 * Created by sidkathuria14 on 8/3/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> myArrayList;
    private OnItemClickListener onItemClickListener;
    private OnLongClickedListener onLongClickedListener;

    public void setOnLongClickedListener(OnLongClickedListener onLongClickedListener) {
        this.onLongClickedListener = onLongClickedListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyAdapter(Context context, ArrayList<Item> myArrayList) {
        this.context = context;
        this.myArrayList = myArrayList;
    }

    public void update(ArrayList<Item> myArrayList, Context context) {
        this.myArrayList = myArrayList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Item thisItem = myArrayList.get(position);

//        final DataBaseHandler db = new DataBaseHandler(context);

        holder.tvName.setText(thisItem.getName());
        holder.tvDescription.setText(thisItem.getDescription());
        holder.tvQuantity.setText(String.valueOf(thisItem.getQuantity()));
        holder.imgView.setImageURI(Uri.parse(thisItem.getImagePath()));
        holder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(thisItem.getId(), view);
                }
            }
        });

        holder.thisView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onLongClickedListener != null) {
                    onLongClickedListener.OnLongClick(thisItem.getId(), view);
                }
                return false;
            }
        });
    }
    public void setFilter(ArrayList<Item> myArrayList1) {
        myArrayList = new ArrayList<>();
        myArrayList.addAll(myArrayList1);
        notifyDataSetChanged();
    }




    @Override
    public int getItemCount() {
        return myArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvQuantity,tvName,tvDescription;
        View thisView;
        ImageView imgView;
        public MyViewHolder(View itemView) {
            super(itemView);
            thisView = itemView;
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvName = itemView.findViewById(R.id.tvName);
            imgView = itemView.findViewById(R.id.imgView);

        }
    }
}

