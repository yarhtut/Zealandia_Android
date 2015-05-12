/**
 * Author: Yar HTUT
 * URL: www.yar.cloudns.org
 * Created by 21104216 on 2/04/2015.
 * */
package info.Zealandia.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import info.Zealandia.LoginActivity;
import info.Zealandia.app.AppController;
import info.Zealandia.model.SanctuaryView;
import info.Zealandia.R;



public class BirdAdapter extends BaseAdapter  {
    private Activity activity;
    private LayoutInflater inflater;
    private List<SanctuaryView> birdLists;


    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public BirdAdapter(Activity activity, List<SanctuaryView> birdLists){
       this.activity = activity;
        this.birdLists = birdLists;

    }
    // counting the size of my list array size
    @Override
    public int getCount(){
        return this.birdLists.size();
    }

    // getting the bird list array  index
    @Override
    public Object getItem(int index){
        return this.birdLists.get(index);
    }
    // getting the bird list array  index
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);



        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);

        TextView textViewId = (TextView) convertView.findViewById(R.id.textViewID);
        TextView textViewLogId = (TextView) convertView.findViewById(R.id.textViewLoginID);
        TextView textViewName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView textViewDesc = (TextView) convertView.findViewById(R.id.textViewDesc);
        TextView textViewPoints = (TextView) convertView.findViewById(R.id.textViewPoints);

        //class definition for SanctuaryView
        SanctuaryView bird = birdLists.get(position);

        // thumbnail image
        thumbNail.setImageUrl(bird.getList_img(), imageLoader);

        //bird Id
        textViewId.setText(bird.getList_id());

        //Login Id
        textViewLogId.setText(bird.getUser_id());

        //bird Name
        textViewName.setText(bird.getList_name());

        //bird Desc
        textViewDesc.setText(bird.getList_desc());

        //bird Points
        textViewPoints.setText(bird.getList_points());


        return convertView;
    }
}
