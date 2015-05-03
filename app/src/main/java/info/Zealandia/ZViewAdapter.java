package info.Zealandia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Yar HTUT 21104216 on 26/03/2015.
 */
public class ZViewAdapter extends RecyclerView.Adapter<ZViewAdapter.MyZViewHolder> {

    public LayoutInflater inflater;
    public Context context;
    List<Information> data = Collections.emptyList();

    public ZViewAdapter(Context context, List<Information> data)    {
       this.context =context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position){
     //   data.remove(position);
    }

    @Override
    public MyZViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.left_nav_row, parent,false);
        MyZViewHolder holder = new MyZViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyZViewHolder holder,final int position) {
        Information current = data.get(position);

        holder.title.setText(current.title);

        holder.icon.setImageResource(current.iconId);




    }

    @Override
    public int getItemCount() {
       return data.size();
    }

    class MyZViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;
        public MyZViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);

           //icon.setOnClickListener(this);
           // title.setOnClickListener(this);


        }

            @Override
            public void onClick(View v) {

                if(getPosition() == 0 ){
                    context.startActivity(new Intent(context, SanctuaryViewActivity.class));
                }
                if(getPosition() == 1 ){
                    context.startActivity(new Intent(context, SchoolActivity.class));
                }
                if(getPosition() == 2 ){
                    context.startActivity(new Intent(context, SanctuaryActivity.class));
                }
                if(getPosition() == 3 ){
                  //  context.startActivity(new Intent(context, BirdView.class));
                }



            }

    }

}
