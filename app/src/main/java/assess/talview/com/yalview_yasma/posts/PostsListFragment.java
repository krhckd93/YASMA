package assess.talview.com.yalview_yasma.posts;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import assess.talview.com.yalview_yasma.R;
import assess.talview.com.yalview_yasma.helper.VerticalSpaceItemDecoration;
import assess.talview.com.yalview_yasma.posts.room.PostModel;
import assess.talview.com.yalview_yasma.user.UserRepository;
import assess.talview.com.yalview_yasma.user.UsersViewModel;
import assess.talview.com.yalview_yasma.user.room.UserModel;

public class PostsListFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View posts_list_layout = inflater.inflate(R.layout.fragment_posts_list, null);
        RecyclerView recyclerView = (RecyclerView) posts_list_layout.findViewById(R.id.recyclerview_posts);
        CustomListAdapter customListAdapter = new CustomListAdapter(new DiffUtil.ItemCallback<PostModel>() {
            @Override
            public boolean areItemsTheSame(PostModel oldItem, PostModel newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(PostModel oldItem, PostModel newItem) {
                return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getBody().equals(newItem.getBody());
            }
        }, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(40));
        recyclerView.setAdapter(customListAdapter);

        return posts_list_layout;
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public class CustomListAdapter extends ListAdapter<PostModel, CustomListAdapter.PostViewHolder> implements CustomItemClickListener {
        private List<PostModel> posts;
        private Context mContext;

        public CustomListAdapter(@NonNull DiffUtil.ItemCallback<PostModel> diffCallback, Context context) {
            super(diffCallback);
            mContext = context;
            final PostsViewModel postsViewModel = ViewModelProviders.of(PostsListFragment.this).get(PostsViewModel.class);
            postsViewModel.getPosts().observe(PostsListFragment.this, new Observer<List<PostModel>>() {
                @Override
                public void onChanged(@Nullable List<PostModel> postModels) {
                    submitList(postModels);
                    posts = postModels;
                }
            });
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.listitem_posts_list, null);
            final PostViewHolder postViewHolder = new PostViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(posts.get(postViewHolder.getAdapterPosition()));
                }
            });
            return postViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
            try {
                PostModel post = posts.get(position);
                holder.tv_body.setText(post.getBody());
                holder.tv_name.setText(post.getId() + "");
                holder.tv_title.setText(post.getTitle());
                holder.tv_username.setText(post.getUserId() + "");
                UserModel userModel = new UserRepository(mContext).getUserObjectById(post.getUserId());
                if(userModel != null) {
                    if (holder.tv_name != null) {
                        holder.tv_name.setText(userModel.getName());
                    }
                    if (holder.tv_username != null) {
                        holder.tv_username.setText(userModel.getUsername());
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public class PostViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_title;
            private TextView tv_body;
            private TextView tv_username;
            private TextView tv_name;

            public PostViewHolder(View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.tv_post_title);
                tv_body = itemView.findViewById(R.id.tv_post_body);
                tv_username = itemView.findViewById(R.id.tv_user_username);
                tv_name = itemView.findViewById(R.id.tv_user_name);
            }
        }

        @Override
        public void onItemClick(PostModel post) {
            Intent intent = new Intent(PostsListFragment.this.getContext(), PostDetailActivity.class);
            intent.putExtra("post_id", post.getId());
            startActivity(intent);
            Toast.makeText(mContext, "Clicked on : " + post.getId(), Toast.LENGTH_SHORT).show();
        }
    }

    public interface CustomItemClickListener {
        public void onItemClick(PostModel post);
    }
}