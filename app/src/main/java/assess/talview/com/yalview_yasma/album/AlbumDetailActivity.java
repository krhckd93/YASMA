package assess.talview.com.yalview_yasma.album;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import assess.talview.com.yalview_yasma.R;
import assess.talview.com.yalview_yasma.album.photos.PhotoDetailActivity;
import assess.talview.com.yalview_yasma.album.photos.PhotoViewModel;
import assess.talview.com.yalview_yasma.album.photos.room.PhotoModel;
import assess.talview.com.yalview_yasma.helper.VerticalSpaceItemDecoration;

public class AlbumDetailActivity extends AppCompatActivity {

    private int album_id;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            album_id = getIntent().getExtras().getInt("album_id");
        } catch (Exception ex) {
            Toast.makeText(this, "album_id not found, kindly try again", Toast.LENGTH_SHORT).show();
            finish();
        }
        if(album_id < 1) {
            Toast.makeText(this, "album_id not found, kindly try again", Toast.LENGTH_SHORT).show();
            finish();
        }
        try {
            setContentView(R.layout.activity_album_detail);
            mRecyclerView = findViewById(R.id.recyclerview_photos);
            RecyclerView.LayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

            mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(25));
            CustomListAdapter customListAdapter = new CustomListAdapter(new DiffUtil.ItemCallback<PhotoModel>() {
                @Override
                public boolean areItemsTheSame(PhotoModel oldItem, PhotoModel newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(PhotoModel oldItem, PhotoModel newItem) {
                    return oldItem.getAlbumId() == newItem.getAlbumId() && oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getThumbnail_url().equals(newItem.getThumbnail_url());
                }
            }, this);

            mRecyclerView.setAdapter(customListAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Caught in on Create", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class CustomListAdapter extends ListAdapter<PhotoModel, CustomListAdapter.PhotoViewHolder> implements AlbumDetailActivity.RecyclerViewOnClickListener{
        private List<PhotoModel> photos;
        private Context mContext;

        public CustomListAdapter(@NonNull DiffUtil.ItemCallback<PhotoModel> diffCallback, Context context) {
            super(diffCallback);
            mContext = context;
            PhotoViewModel photoViewModel = ViewModelProviders.of(AlbumDetailActivity.this).get(PhotoViewModel.class);
            photoViewModel.getAlbumPhotos(album_id).observe(AlbumDetailActivity.this, new Observer<List<PhotoModel>>() {
                @Override
                public void onChanged(@Nullable List<PhotoModel> photoModels) {
                    submitList(photoModels);
                    photos = photoModels;
                }
            });
//            photos = photoViewModel.getAlbumPhotosObjects(album_id);
        }

        @NonNull
        @Override
        public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(mContext).inflate(R.layout.listitem_photo_list_nocard, null);
            final PhotoViewHolder viewHolder = new PhotoViewHolder(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(AlbumDetailActivity.this, itemView, "transition");
                    int revealX = (int) (itemView.getX() + itemView.getWidth() / 2);
                    int revealY = (int) (itemView.getY() + itemView.getHeight() / 2);
                    String image_url = photos.get(viewHolder.getAdapterPosition()).getUrl();

                    Intent intent = new Intent(AlbumDetailActivity.this, PhotoDetailActivity.class);
                    intent.putExtra(PhotoDetailActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
                    intent.putExtra(PhotoDetailActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
                    intent.putExtra("EXTRA_IMAGE_URL", image_url);

                    ActivityCompat.startActivity(AlbumDetailActivity.this, intent, options.toBundle());
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
            PhotoModel photo = photos.get(position);
            holder.tv_title.setText(photo.getTitle());
            Picasso.get().load(photo.getThumbnail_url()).into(holder.iv_photo_thumbnail);
        }

        public class PhotoViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title;
            ImageView iv_photo_thumbnail;

            public PhotoViewHolder(View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.tv_photo_title);
                iv_photo_thumbnail = itemView.findViewById(R.id.iv_photo_thumbnail);
            }

        }

//        @Override
//        public int getItemCount() {
//            return photos == null ? 0 : photos.size();
//        }

        @Override
        public void onItemClick(PhotoModel photo) {
            Toast.makeText(mContext, "Clicked on : " + photo.getId() + ":" + photo.getTitle() , Toast.LENGTH_SHORT).show();
        }
    }

    public interface RecyclerViewOnClickListener {
        public void onItemClick(PhotoModel photo);
    }
}
