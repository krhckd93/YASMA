package assess.talview.com.yalview_yasma;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import assess.talview.com.yalview_yasma.album.AlbumsInterface;
import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.posts.comments.CommentsInterface;
import assess.talview.com.yalview_yasma.posts.PostsViewModel;
import assess.talview.com.yalview_yasma.posts.retro.PostsModel;
import assess.talview.com.yalview_yasma.user.UserInterface;

public class MainActivity extends AppCompatActivity implements CommentsInterface, UserInterface, AlbumsInterface {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.home_pager);
//        TabHost tabhost = (TabHost) findViewById(R.id.home_tabhost);

        tv = (TextView) findViewById(R.id.tv_post);

//        PostsController.GetPosts(this);
//        CommentsController.getPostComments(this, 1);

//        UserController.getUser(this, 1);
//        AlbumsController.getAlbumPhotos(this, 1);

        PostsViewModel postsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
        postsViewModel.getPosts().observe(this, new Observer<List<PostsModel>>() {
            @Override
            public void onChanged(@Nullable List<PostsModel> postsModels) {
                try {
                    tv.setText(postsModels.toString());
                } catch (Exception ex) {
                    tv.setText(ex.toString());
                    Log.e("MainActivity", ex.toString());
                }
            }
        });

    }

    @Override
    public void postGetUser(BaseResponse baseResponse) {
        if(tv == null) {
            tv = findViewById(R.id.tv_post);
        }

        if(baseResponse.getResult()!= null) {
            tv.setText(baseResponse.getResult().toString());
        } else {
            tv.setText(baseResponse.getError());
        }
    }

    @Override
    public void postGetUsers(BaseResponse baseResponse) {
        if(tv == null) {
            tv = findViewById(R.id.tv_post);
        }

        if(baseResponse.getResult()!= null) {
            tv.setText(baseResponse.getResult().toString());
        } else {
            tv.setText(baseResponse.getError());
        }
    }

    @Override
    public void postGetComments(BaseResponse baseResponse) {
        if(tv == null) {
            tv = findViewById(R.id.tv_post);
        }

        if(baseResponse.getResult()!= null) {
            tv.setText(baseResponse.getResult().toString());
        } else {
            tv.setText(baseResponse.getError());
        }
    }

    @Override
    public void postGetAlbums(BaseResponse baseResponse) {
        if(tv == null) {
            tv = findViewById(R.id.tv_post);
        }

        if(baseResponse.getResult()!= null) {
            tv.setText(baseResponse.getResult().toString());
        } else {
            tv.setText(baseResponse.getError());
        }
    }

    @Override
    public void postGetAlbum(BaseResponse baseResponse) {
        if(tv == null) {
            tv = findViewById(R.id.tv_post);
        }

        if(baseResponse.getResult()!= null) {
            tv.setText(baseResponse.getResult().toString());
        } else {
            tv.setText(baseResponse.getError());
        }
    }

    @Override
    public void postGetAlbumPhotos(BaseResponse baseResponse) {
        if(tv == null) {
            tv = findViewById(R.id.tv_post);
        }

        if(baseResponse.getResult()!= null) {
            tv.setText(baseResponse.getResult().toString());
        } else {
            tv.setText(baseResponse.getError());
        }
    }
}
