/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ashwini.ecommapp.fragments;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ashwini.ecommapp.R;
import com.ashwini.ecommapp.model.Product;
import com.ashwini.ecommapp.product.ItemDetailsActivity;
import com.ashwini.ecommapp.startup.MainActivity;
import com.ashwini.ecommapp.utility.ImageUrlUtils;
import com.ashwini.ecommapp.utility.UtilityAssets;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ImageListFragment extends Fragment {

    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    public static final String STRING_ITEM_NAME = "ItemName";
    public static final String STRING_ITEM_PRICE = "ItemPrice";
    public static final String STRING_ITEM_DESC = "ItemDesc";
    private static MainActivity mActivity;
    private ArrayList<Product> products;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        //products =  UtilityAssets.loadProductsFromAsset(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.layout_recylerview_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
      /*  if (ImageListFragment.this.getArguments().getInt("type") == 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        } else if (ImageListFragment.this.getArguments().getInt("type") == 2) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), 3);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        }*/
        /*String[] items = null;
        if (ImageListFragment.this.getArguments().getInt("type") == 1) {
            items = ImageUrlUtils.getGroceryUrls();
        } else if (ImageListFragment.this.getArguments().getInt("type") == 2) {
            items = ImageUrlUtils.getDairyBeveragesUrls();
        } else if (ImageListFragment.this.getArguments().getInt("type") == 3) {
            items = ImageUrlUtils.getPackagedFoodUrls();
        } else if (ImageListFragment.this.getArguments().getInt("type") == 4) {
            items = ImageUrlUtils.getHomeApplianceUrls();
        } else if (ImageListFragment.this.getArguments().getInt("type") == 5) {
            items = ImageUrlUtils.getBooksUrls();
        } else {
            items = ImageUrlUtils.getImageUrls();
        }*/
        if (ImageListFragment.this.getArguments().getInt("type") == 1) {
            products = UtilityAssets.loadCategoryProducts(getContext(),getString(R.string.item_1));
        } else if (ImageListFragment.this.getArguments().getInt("type") == 2) {
            products = UtilityAssets.loadCategoryProducts(getContext(),getString(R.string.item_2));
        } else if (ImageListFragment.this.getArguments().getInt("type") == 3) {
            products = UtilityAssets.loadCategoryProducts(getContext(),getString(R.string.item_3));
        } else if (ImageListFragment.this.getArguments().getInt("type") == 4) {
            products = UtilityAssets.loadCategoryProducts(getContext(),getString(R.string.item_4));
        } else if (ImageListFragment.this.getArguments().getInt("type") == 5) {
            products = UtilityAssets.loadCategoryProducts(getContext(),getString(R.string.item_5));
        } else {
            products = UtilityAssets.loadProductsFromAsset(getContext());
        }

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, products));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Product> mProducts;
        private RecyclerView mRecyclerView;

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView,  ArrayList<Product> products) {
            mProducts = products;
            mRecyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
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
        public void onBindViewHolder(final ViewHolder holder, final int position) {
           /* FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                layoutParams.height = 200;
            } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                layoutParams.height = 600;
            } else {
                layoutParams.height = 800;
            }*/

            final Product product = mProducts.get(position);
            //final Uri uri = Uri.parse(product.getImageName());
            Log.i("TAG", "onBindViewHolder: "+product.getImageName());
            //final Uri uri = Uri.parse("file:///android_asset/"+product.getImageName());
            Log.i("TAG", ": file:///android_asset/"+product.getImageName());
            Glide.with(mActivity).load("file:///android_asset/products/"+product.getImageName()).into(holder.mImageView);
//            Glide.with(mActivity)
//                    .load(Uri.parse("file:///android_asset/"+product.getImageName()))
//                    .into(holder.mImageView);
            //holder.mImageView.setImageURI(uri);
            /*AssetManager assetManager = mActivity.getAssets();
            InputStream is = null;
            try {
                is = assetManager.open(product.getImageName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            holder.mImageView.setImageBitmap(bitmap);*/
            holder.mItemName.setText(product.getItemName());
            holder.mItemPrice.setText(mActivity.getResources().getString(R.string.Rs)  +product.getPrice());
            holder.mItemDesc.setText(product.getQuantity());

            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ItemDetailsActivity.class);
                    intent.putExtra(STRING_ITEM_NAME, product.getItemName());
                    intent.putExtra(STRING_ITEM_PRICE, product.getPrice());
                    intent.putExtra(STRING_ITEM_DESC, product.getQuantity());
                    intent.putExtra(STRING_IMAGE_URI, product.getImageName());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mActivity.startActivity(intent);

                }
            });

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    imageUrlUtils.addWishlistImageUri(product.getImageName());
                    holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_black_18dp);
                    notifyDataSetChanged();
                    Toast.makeText(mActivity, "Item added to wish list.", Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            public final TextView mItemName;
            public final TextView mItemDesc;
            public final TextView mItemPrice;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image1);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
                mItemName = (TextView) view.findViewById(R.id.item_name);
                mItemDesc = (TextView) view.findViewById(R.id.item_desc);
                mItemPrice = (TextView) view.findViewById(R.id.item_price);
            }
        }
    }
}
