

package com.adsoft.girls_tatoos_gallery;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.adsoft.girls_tatoos_gallery.R.id;
import static com.adsoft.girls_tatoos_gallery.R.layout;

public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {
    private static final int COUNT = 100;

    private Context mContext;
    private final RecyclerView mRecyclerView;

    List<Bitmap> image;
    List<String> assetList;


    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        //        public final TextView title;
        public final ImageView imageview;




        public SimpleViewHolder(View view) {
            super(view);
//            title = (TextView) view.findViewById(id.title);
            imageview = (ImageView) view.findViewById(id.title);



        }
    }

    public LayoutAdapter(Context context, RecyclerView recyclerView, List<Bitmap> image, List<String> assetList) {
        mContext = context;

        this.assetList = assetList;
        mRecyclerView = recyclerView;
        this.image = image;
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(layout.item, parent, false);

        return new SimpleViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
//        holder.title.setText(mItems.get(position).toString());
       /* LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 500);
        holder.imageview.setLayoutParams(layoutParams);*/
        holder.imageview.setImageBitmap(image.get(position));


        holder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "item " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ViewImageActivity.class);
                intent.putExtra("image", assetList.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return image.size();
    }


}
