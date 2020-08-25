package com.ashwini.ecommapp.options;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwini.ecommapp.R;
import com.ashwini.ecommapp.model.Product;
import com.ashwini.ecommapp.product.ItemDetailsActivity;
import com.ashwini.ecommapp.utility.ImageUrlUtils;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import static com.ashwini.ecommapp.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.ashwini.ecommapp.fragments.ImageListFragment.STRING_IMAGE_URI;
import static com.ashwini.ecommapp.fragments.ImageListFragment.STRING_ITEM_DESC;
import static com.ashwini.ecommapp.fragments.ImageListFragment.STRING_ITEM_NAME;
import static com.ashwini.ecommapp.fragments.ImageListFragment.STRING_ITEM_PRICE;

public class WishlistActivity extends AppCompatActivity {
    private static Context mContext;
    private static final String TAG = "WishlistActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recylerview_list);
        mContext = WishlistActivity.this;

        ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
        //ArrayList<String> wishlistImageUri = imageUrlUtils.getWishlistImageUri();

        ArrayList<Product> wishList = imageUrlUtils.getWishProductList();
        Log.i(TAG, "wishList: size "+wishList.size());
        RecyclerView recyclerView =  findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, wishList));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

       // private ArrayList<String> mWishlistImageUri;
        private RecyclerView mRecyclerView;
        private ArrayList<Product> mWishList;

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<Product> wishList) {
          //  mWishlistImageUri = wishlistImageUri;
            mRecyclerView = recyclerView;
            mWishList = wishList;
        }

        @Override
        public WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wishlist_item, parent, false);
            return new WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            final Product product = mWishList.get(position);

            holder.mItemName.setText(product.getItemName());
            holder.mItemPrice.setText(mContext.getResources().getString(R.string.Rs)  +product.getPrice());
            Glide.with(mContext).load("file:///android_asset/products/"+product.getImageName()).into(holder.mImageView);

            //final Uri uri = Uri.parse(mWishlistImageUri.get(position));
            //holder.mImageView.setImageURI(uri);
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra(STRING_ITEM_NAME, product.getItemName());
                    intent.putExtra(STRING_ITEM_PRICE, product.getPrice());
                    intent.putExtra(STRING_ITEM_DESC, "1");
                    intent.putExtra(STRING_IMAGE_URI, product.getImageName());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mContext.startActivity(intent);

                }
            });

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    imageUrlUtils.removeWishProduct(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mWishList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            private final TextView mItemName, mItemPrice;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView =  view.findViewById(R.id.image_wishlist);
                mLayoutItem =  view.findViewById(R.id.layout_item_desc);
                mImageViewWishlist =  view.findViewById(R.id.ic_wishlist);
                mItemName = view.findViewById(R.id.item_name);
                mItemPrice = view.findViewById(R.id.item_price);
            }
        }
    }
}
