package com.ashwini.ecommapp.options;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwini.ecommapp.R;
import com.ashwini.ecommapp.model.Product;
import com.ashwini.ecommapp.product.ItemDetailsActivity;
import com.ashwini.ecommapp.startup.MainActivity;
import com.ashwini.ecommapp.utility.ImageUrlUtils;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import static com.ashwini.ecommapp.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.ashwini.ecommapp.fragments.ImageListFragment.STRING_IMAGE_URI;
import static com.ashwini.ecommapp.fragments.ImageListFragment.STRING_ITEM_DESC;
import static com.ashwini.ecommapp.fragments.ImageListFragment.STRING_ITEM_NAME;
import static com.ashwini.ecommapp.fragments.ImageListFragment.STRING_ITEM_PRICE;

public class CartListActivity extends AppCompatActivity {
    private static final String TAG = "CartListActivity";
    private static Context mContext;
    private TextView text_action_bottom1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        text_action_bottom1 = findViewById(R.id.text_action_bottom1);
        mContext = CartListActivity.this;
        ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
        ArrayList<Product> cartList = new ArrayList<Product>();
        cartList = (ArrayList<Product>) getIntent().getSerializableExtra("QuestionListExtra");
        if(cartList != null){
            Log.e(TAG, "onCreate:cartList.size " + cartList.size());
            if (cartList.size() != 0) {
                Product product = cartList.get(0);
                text_action_bottom1.setText(product.getPrice());
            }
        }

        //ArrayList<String> cartlistImageUri = imageUrlUtils.getCartListImageUri();
        //ArrayList<Product> cartList = imageUrlUtils.getCartProductList();


        setCartLayout();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new CartListActivity.SimpleStringRecyclerViewAdapter(recyclerView, cartList));
    }

    protected void setCartLayout() {
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);

        if (MainActivity.notificationCountCart > 0) {
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        } else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            Button bStartShopping = (Button) findViewById(R.id.bAddNew);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        //private ArrayList<String> mCartlistImageUri;
        private ArrayList<Product> mCartList;
        // private ArrayList<String> mItemPrices;


        private RecyclerView mRecyclerView;

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<Product> cartList) {
            //mCartlistImageUri = wishlistImageUri;
            mRecyclerView = recyclerView;
            mCartList = cartList;
        }

        @Override
        public CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartlist_item, parent, false);
            return new CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {


            final Product product = mCartList.get(position);
            //     Log.e(TAG, "CartList: "+product.getItemName() );
            //Log.e(TAG, ": file:///android_asset/"+product.getImageName());
            Glide.with(mContext).load("file:///android_asset/products/" + product.getImageName()).into(holder.mImageView);

            holder.mItemName.setText(product.getItemName());
            holder.mItemPrice.setText(mContext.getResources().getString(R.string.Rs) + product.getPrice());
            holder.mItemDesc.setText(product.getQuantity());
            // final Uri uri = Uri.parse(mCartlistImageUri.get(position));
            //holder.mImageView.setImageURI(uri);
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = mCartList.get(position);
                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra(STRING_ITEM_NAME, product.getItemName());
                    intent.putExtra(STRING_ITEM_PRICE, product.getPrice());
                    intent.putExtra(STRING_ITEM_DESC, "1");
                    intent.putExtra(STRING_IMAGE_URI, product.getImageName());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mContext.startActivity(intent);
                }
            });

            //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    imageUrlUtils.removeCartProduct(position);
                    notifyDataSetChanged();
                    //Decrease notification count
                    MainActivity.notificationCountCart--;

                }
            });

            //Set click action
            holder.mLayoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCartList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem, mLayoutRemove, mLayoutEdit;
            public final TextView mItemName;
            public final TextView mItemDesc;
            public final TextView mItemPrice;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
                mItemName = view.findViewById(R.id.item_name);
                mItemDesc = view.findViewById(R.id.item_desc);
                mItemPrice = view.findViewById(R.id.item_price);
            }
        }
    }
}
