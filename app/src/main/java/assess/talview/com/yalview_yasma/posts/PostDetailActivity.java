package assess.talview.com.yalview_yasma.posts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

import assess.talview.com.yalview_yasma.R;
import assess.talview.com.yalview_yasma.helper.CustomDividerItemDecoration;
import assess.talview.com.yalview_yasma.helper.VerticalSpaceItemDecoration;
import assess.talview.com.yalview_yasma.posts.comments.CommentsViewModel;
import assess.talview.com.yalview_yasma.posts.comments.room.CommentModel;
import assess.talview.com.yalview_yasma.posts.room.PostModel;
import assess.talview.com.yalview_yasma.user.UserRepository;
import assess.talview.com.yalview_yasma.user.UsersViewModel;
import assess.talview.com.yalview_yasma.user.room.UserModel;

public class PostDetailActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView tv_post_title;
    private TextView tv_post_body;
    private TextView tv_post_name;
    private TextView tv_post_username;

    private int post_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            post_id = getIntent().getExtras().getInt("post_id");
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
            finish();
        }
        if( post_id < 1 ) {
            Toast.makeText(this, "Post ID not found, kindly try again.", Toast.LENGTH_SHORT).show();
            finish();
        }
        setContentView(R.layout.activity_post_detail_nestedsv);
        tv_post_body = findViewById(R.id.tv_post_body);
        tv_post_title = findViewById(R.id.tv_post_title);
        tv_post_name = findViewById(R.id.tv_user_name);
        tv_post_username = findViewById(R.id.tv_user_username);

        PostsViewModel postsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
        postsViewModel.getPost(post_id).observe(this, new Observer<PostModel>() {
            @Override
            public void onChanged(@Nullable PostModel postModel) {
                setPostDetails(postModel);
            }
        });

        mRecyclerView = findViewById(R.id.recyclerview_post_comment);
        mRecyclerView.setNestedScrollingEnabled(false);

        CustomListAdapter customListAdapter = new CustomListAdapter(new DiffUtil.ItemCallback<CommentModel>() {
            @Override
            public boolean areItemsTheSame(CommentModel oldItem, CommentModel newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(CommentModel oldItem, CommentModel newItem) {
                return oldItem.getName().equals(newItem.getName()) && oldItem.getBody().equals(newItem.getBody()) && oldItem.getEmail().equals(newItem.getEmail());
            }
        }, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(40));
        mRecyclerView.setAdapter(customListAdapter);
    }

    public void setPostDetails(PostModel post) {
        if(tv_post_body != null) {
            tv_post_body.setText(post.getBody());
        }
        if(tv_post_title != null) {
            tv_post_title.setText(post.getTitle());
        }
        UserModel userModel = new UserRepository(this).getUserObjectById(post.getUserId());

        if(userModel != null) {
            if (tv_post_name != null) {
                tv_post_name.setText(userModel.getName());
            }
            if (tv_post_username != null) {
                tv_post_username.setText(userModel.getUsername());
            }
        }

    }

    public class CustomListAdapter extends ListAdapter<CommentModel, CustomListAdapter.CommentViewHolder> {

        private List<CommentModel> comments;
        private Context mContext;

        public CustomListAdapter(@NonNull DiffUtil.ItemCallback<CommentModel> diffCallback, Context context) {
            super(diffCallback);
            mContext = context;
            CommentsViewModel commentsViewModel = ViewModelProviders.of(PostDetailActivity.this).get(CommentsViewModel.class);
            commentsViewModel.getComments(post_id).observe(PostDetailActivity.this, new Observer<List<CommentModel>>() {
                @Override
                public void onChanged(@Nullable List<CommentModel> commentModels) {
                    try {
                        submitList(commentModels);
                        comments = commentModels;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.listitem_comments_list, null);
            CommentViewHolder commentViewHolder = new CommentViewHolder(view);
            return commentViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
            try {
                holder.tv_email.setText(comments.get(position).getEmail());
                holder.tv_name.setText(comments.get(position).getName());
                holder.tv_body.setText(comments.get(position).getBody());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public class CommentViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name;
            TextView tv_body;
            TextView tv_email;

            public CommentViewHolder(View itemView) {
                super(itemView);
                tv_name = itemView.findViewById(R.id.tv_comment_name);
                tv_body = itemView.findViewById(R.id.tv_comment_body);
                tv_email = itemView.findViewById(R.id.tv_comment_email);
            }
        }
    }

}
