package com.ashwini.ecommapp.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.allandroidprojects.ecomsample.utility.ImageUrlUtils;
import com.ashwini.ecommapp.R;
import com.ashwini.ecommapp.fragments.ImageListFragment;
import com.ashwini.ecommapp.fragments.ViewPagerActivity;
import com.ashwini.ecommapp.notification.NotificationCountSetClass;
import com.ashwini.ecommapp.options.CartListActivity;
import com.ashwini.ecommapp.startup.MainActivity;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

public class ItemDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ItemDetailsActivity";
    int imagePosition;
    String stringImageUri, StringItemName, StringItemPrice, StringItemDesc;
    private TextView mItemName, mItemPrice, mItemDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        SimpleDraweeView mImageView =  findViewById(R.id.image1);
        TextView textViewAddToCart =  findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow =  findViewById(R.id.text_action_bottom2);
        mItemName =  findViewById(R.id.item_name);
        mItemPrice =  findViewById(R.id.item_price);
        mItemDesc =  findViewById(R.id.item_desc);

        //Getting image uri from previous screen
        if (getIntent() != null) {

            StringItemName = getIntent().getStringExtra(ImageListFragment.STRING_ITEM_NAME);
            StringItemPrice = getIntent().getStringExtra(ImageListFragment.STRING_ITEM_PRICE);
            StringItemDesc = getIntent().getStringExtra(ImageListFragment.STRING_ITEM_DESC);
            stringImageUri = getIntent().getStringExtra(ImageListFragment.STRING_IMAGE_URI);
            imagePosition = getIntent().getIntExtra(ImageListFragment.STRING_IMAGE_URI, 0);

        }
        //Log.i(TAG, "onCreate:StringItemName "+StringItemName);
        mItemName.setText(StringItemName);
        mItemPrice.setText(getResources().getString(R.string.Rs)  +StringItemPrice);
        mItemDesc.setText(StringItemDesc);
        //Log.i(TAG, "onCreate:stringImageUri "+stringImageUri);

        Glide.with(ItemDetailsActivity.this).load("file:///android_asset/products/"+stringImageUri).into(mImageView);
       // Uri uri = Uri.parse(stringImageUri);
       // mImageView.setImageURI(uri);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailsActivity.this, ViewPagerActivity.class);
                intent.putExtra("position", imagePosition);
                startActivity(intent);

            }
        });

        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                imageUrlUtils.addCartListImageUri(stringImageUri);
                Toast.makeText(ItemDetailsActivity.this, "Item added to cart.", Toast.LENGTH_SHORT).show();
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
            }
        });

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                imageUrlUtils.addCartListImageUri(stringImageUri);
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));

            }
        });

    }
}
