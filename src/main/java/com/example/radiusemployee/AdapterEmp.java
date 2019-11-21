package com.example.radiusemployee;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.Collections;
import java.util.List;

class AdapterEmp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataEmp> data = Collections.emptyList();

    public AdapterEmp(Context context, List<DataEmp> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.container_layout, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        DataEmp current = data.get(position);
        myHolder.textEmpName.setText(current.getEmpName());

        myHolder.textAge.setText(current.getEmpAge());

        Glide.with(context).load( current.empImage)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(myHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView textEmpName, textAge;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.profile_image);
            textEmpName=itemView.findViewById(R.id.text_EmpName);
            textAge=itemView.findViewById(R.id.text_Age);

        }

    }

}
