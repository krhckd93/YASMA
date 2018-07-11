package assess.talview.com.yalview_yasma.album;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import assess.talview.com.yalview_yasma.R;
import assess.talview.com.yalview_yasma.album.room.AlbumModel;
import assess.talview.com.yalview_yasma.helper.VerticalSpaceItemDecoration;
import assess.talview.com.yalview_yasma.posts.PostDetailActivity;
import assess.talview.com.yalview_yasma.user.UserRepository;
import assess.talview.com.yalview_yasma.user.room.UserModel;

public class AlbumsListFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View albums_list_layout = inflater.inflate(R.layout.fragment_albums_list, null);
        RecyclerView recyclerview_albums = (RecyclerView) albums_list_layout.findViewById(R.id.recyclerview_albums);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview_albums.setLayoutManager(layoutManager);
        recyclerview_albums.setItemAnimator(new DefaultItemAnimator());
        CustomListAdapter albumListAdapter = new CustomListAdapter(new DiffUtil.ItemCallback<AlbumModel>() {
            @Override
            public boolean areItemsTheSame(AlbumModel oldItem, AlbumModel newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(AlbumModel oldItem, AlbumModel newItem) {
                return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getUserId() == newItem.getUserId();
            }
        }, getContext());
        recyclerview_albums.addItemDecoration(new VerticalSpaceItemDecoration(40));
        recyclerview_albums.setAdapter(albumListAdapter);
        return albums_list_layout;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class CustomListAdapter extends ListAdapter<AlbumModel, CustomListAdapter.AlbumViewHolder> implements RecyclerViewItemClickListener{

        private Context mContext;
        private List<AlbumModel> albums;

        public CustomListAdapter(@NonNull DiffUtil.ItemCallback<AlbumModel> diffCallback, Context mContext) {
            super(diffCallback);
            this.mContext = mContext;
            AlbumsViewModel albumsViewModel = ViewModelProviders.of(AlbumsListFragment.this).get(AlbumsViewModel.class);
            albumsViewModel.getAlbums(mContext).observe(AlbumsListFragment.this, new Observer<List<AlbumModel>>() {
                @Override
                public void onChanged(@Nullable List<AlbumModel> albumModels) {
                    submitList(albumModels);
                    albums = albumModels;
                }
            });
        }

        @NonNull
        @Override
        public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.listitem_albums_list, null);
            final AlbumViewHolder albumViewHolder = new AlbumViewHolder(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(albums.get(albumViewHolder.getAdapterPosition()));
                }
            });
            return albumViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
            AlbumModel album = albums.get(position);
            holder.tv_album_title.setText(album.getTitle());
            holder.tv_user_name.setText(album.getId()+"");
            holder.tv_user_username.setText(album.getUserId()+"");
            UserModel userModel = new UserRepository(mContext).getUserObjectById(album.getUserId());
            if(userModel != null) {
                holder.tv_user_name.setText(userModel.getName());
                holder.tv_user_username.setText(userModel.getUsername());
            }
        }

        public class AlbumViewHolder extends RecyclerView.ViewHolder {
            TextView tv_album_title;
            TextView tv_user_name;
            TextView tv_user_username;

            public AlbumViewHolder(View itemView) {
                super(itemView);
                tv_album_title = itemView.findViewById(R.id.tv_album_title);
                tv_user_username = itemView.findViewById(R.id.tv_album_username);
                tv_user_name = itemView.findViewById(R.id.tv_album_user_name);
            }
        }

        @Override
        public void onItemClick(AlbumModel album) {
            if(album != null) {
                Intent album_detail_intent = new Intent(AlbumsListFragment.this.getContext(), AlbumDetailActivity.class);
                album_detail_intent.putExtra("album_id", album.getId());
                startActivity(album_detail_intent);
                Toast.makeText(mContext, "Clicked on : " + album.getId() + " " + album.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface RecyclerViewItemClickListener {
        public void onItemClick(AlbumModel album);
    }
}
