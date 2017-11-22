package com.hackdevsummit.cynthia.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackdevsummit.cynthia.R;
import com.hackdevsummit.cynthia.databases.DbDataFashion;

/**
 * Created by Alhudaghifari on 11/22/2017.
 */

public class RecyclerHomeLeft extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
//    private OnArtikelClickListener mOnArtikelClickListener;
//    private OnButtonShareClickListener mOnButtonShareClickListener;

    public RecyclerHomeLeft(Context context) {
        mContext = context;
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

//        final DbDataFashion dbDataFashion =

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected class ViewHolderArticle extends RecyclerView.ViewHolder {
        public View mViewContainer;
        public CardView mCardViewContainer;

        public ImageView mImageViewGambarBerita;

        public ViewHolderArticle(View itemView) {
            super(itemView);

            mViewContainer = itemView;
            mCardViewContainer = (CardView) itemView.findViewById(R.id.cardview_container);

            mImageViewGambarBerita = (ImageView) itemView.findViewById(R.id.iv_gambar);
        }
    }
}
