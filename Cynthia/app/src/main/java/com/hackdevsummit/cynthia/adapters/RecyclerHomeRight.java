package com.hackdevsummit.cynthia.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hackdevsummit.cynthia.R;
import com.hackdevsummit.cynthia.databases.DbDataFashion;

import java.util.List;

/**
 * Created by Alhudaghifari on 11/22/2017.
 */

public class RecyclerHomeRight extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static String TAG = RecyclerHomeRight.class.getSimpleName();

    private Context mContext;

    private List<DbDataFashion> mDbDataFashionList;

    public RecyclerHomeRight(Context context, List<DbDataFashion> dbDataFashions) {
        mContext = context;
        mDbDataFashionList = dbDataFashions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default :
                view = LayoutInflater.from(mContext).inflate(R.layout.cardview_image, parent, false);
                return new ViewHolderArticle(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderArticle viewHolderArticle = (ViewHolderArticle) holder;
        final int posisiAdapter = holder.getAdapterPosition();


        if (position % 2 == 0) {
            final DbDataFashion dbDataFashion = mDbDataFashionList.get(position);

            String judul = dbDataFashion.getJudul();
            String linkGambar = dbDataFashion.getLinkGambar();


            ImageView imageViewGambarBesar = viewHolderArticle.mImageViewGambarBerita;

            viewHolderArticle.mtextViewJudulGambar.setText(judul);

            imageViewGambarBesar.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(linkGambar)
                    .into(imageViewGambarBesar);

            Log.d(TAG,position + ". Recycler Success");
            Log.d(TAG,"link : " + linkGambar);
        }
    }

    @Override
    public int getItemCount() {
        return mDbDataFashionList.size();
    }

    protected class ViewHolderArticle extends RecyclerView.ViewHolder {
        public View mViewContainer;
        public CardView mCardViewContainer;

        public ImageView mImageViewGambarBerita;
        public TextView mtextViewJudulGambar;

        public ViewHolderArticle(View itemView) {
            super(itemView);

            mViewContainer = itemView;
            mCardViewContainer = (CardView) itemView.findViewById(R.id.cardview_container);

            mImageViewGambarBerita = (ImageView) itemView.findViewById(R.id.iv_gambar);
            mtextViewJudulGambar = (TextView) itemView.findViewById(R.id.tv_judulGambar);
        }
    }
}
