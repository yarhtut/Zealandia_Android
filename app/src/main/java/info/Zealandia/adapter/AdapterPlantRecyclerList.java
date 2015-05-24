package info.Zealandia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import info.Zealandia.R;
import info.Zealandia.app.AppController;
import info.Zealandia.model.SanctuaryView;

/**
 * Created by 21104216 on 6/04/2015.
 */
public class AdapterPlantRecyclerList extends RecyclerView.Adapter<AdapterPlantRecyclerList.ViewHolderRecyclerBird> {
    private ArrayList<SanctuaryView> listPlant = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private AppController mVolleySingleton;
    private ImageLoader mImageLoader;



    public AdapterPlantRecyclerList(Context context){

        layoutInflater = LayoutInflater.from(context);
        mVolleySingleton = AppController.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();


    }

    public void setListBird(ArrayList<SanctuaryView> listPlant){
        this.listPlant = listPlant;
        notifyItemRangeChanged(0, listPlant.size());
    }

    @Override
    public ViewHolderRecyclerBird onCreateViewHolder(ViewGroup parent, int viewType){
         View view = layoutInflater.inflate(R.layout.recycler_list_bird,parent,false);
        ViewHolderRecyclerBird viewHolderBird = new ViewHolderRecyclerBird(view);

        return viewHolderBird;
    }

    @Override
    public void onBindViewHolder(final ViewHolderRecyclerBird holder, int position) {
        SanctuaryView currentPlants = listPlant.get(position);
        holder.list_name.setText(currentPlants.getList_name());
        //holder.birdThumbnail.setText(currentBirds.getList_name());
        holder.list_desc.setText(currentPlants.getList_desc());
        holder.list_points.setText(currentPlants.getList_points());


       String urlThumnail = currentPlants.getList_img();
      // mImageLoader = AppController.getInstance().getImageLoader();
      //  holder.birdThumbnail.setImageBitmap();

        if(urlThumnail !=  null){
            mImageLoader.get(urlThumnail, new ImageLoader.ImageListener(){


                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {

                    holder.list_img.setImageBitmap(imageContainer.getBitmap());
                }
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listPlant.size();
    }

    static class ViewHolderRecyclerBird extends RecyclerView.ViewHolder{
            private ImageView list_img;
            private TextView  list_name;
            private TextView   list_desc;
            private TextView  list_points;
        public ViewHolderRecyclerBird(View itemView){
            super (itemView);
            list_img = (ImageView) itemView.findViewById(R.id.thumbnailPlant);
            list_name = (TextView) itemView.findViewById(R.id.textViewNamePlant);
            list_desc = (TextView) itemView.findViewById(R.id.textViewDescPlant);
            list_points = (TextView) itemView.findViewById(R.id.textViewPointsPlant);



        }
    }
}
