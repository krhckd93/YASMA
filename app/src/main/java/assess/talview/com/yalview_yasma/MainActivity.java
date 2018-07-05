package assess.talview.com.yalview_yasma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import assess.talview.com.yalview_yasma.album.AlbumsInterface;
import assess.talview.com.yalview_yasma.album.retro.AlbumsController;
import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.comments.CommentsInterface;
import assess.talview.com.yalview_yasma.comments.retro.CommentsController;
import assess.talview.com.yalview_yasma.posts.PostsInterface;
import assess.talview.com.yalview_yasma.posts.retro.PostsController;
import assess.talview.com.yalview_yasma.user.UserInterface;
import assess.talview.com.yalview_yasma.user.retro.UserController;

public class MainActivity extends AppCompatActivity implements PostsInterface, CommentsInterface, UserInterface, AlbumsInterface {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.home_pager);
//        TabHost tabhost = (TabHost) findViewById(R.id.home_tabhost);

        tv = (TextView) findViewById(R.id.tv_post);

        PostsController.GetPosts(this);
        CommentsController.getPostComments(this, 1);
//
        UserController.getUser(this, 1);
        AlbumsController.getAlbumPhotos(this, 1);
    }

    @Override
    public void postGetPosts(BaseResponse baseResponse) {
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
