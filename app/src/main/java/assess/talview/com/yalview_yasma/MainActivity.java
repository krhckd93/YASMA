package assess.talview.com.yalview_yasma;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.widget.TextView;

import assess.talview.com.yalview_yasma.album.AlbumsListFragment;
import assess.talview.com.yalview_yasma.posts.PostsListFragment;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Toolbar mToolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = (TabLayout) findViewById(R.id.home_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.home_viewpager);
        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        mTabLayout.setupWithViewPager(mViewPager, true);
        setSupportActionBar(mToolbar);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout.getTabAt(0).setIcon(R.drawable.post_icon_32);
        mTabLayout.getTabAt(1).setIcon(R.drawable.album_icon_32);
//        tv = (TextView) findViewById(R.id.tv_post);

//        PostsController.GetPosts(this);
//        CommentsController.getPostComments(this, 1);

//        UserController.getUser(this, 1);
//        AlbumsController.getAlbumPhotos(this, 1);

//        PostsViewModel postsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
//        postsViewModel.getPosts().observe(this, new Observer<List<PostsModel>>() {
//            @Override
//            public void onChanged(@Nullable List<PostsModel> postsModels) {
//                try {
//                    tv.setText(postsModels.toString());
//                } catch (Exception ex) {
//                    tv.setText(ex.toString());
//                    Log.e("MainActivity", ex.toString());
//                }
//            }
//        });


//        UsersViewModel usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
//        usersViewModel.getUsers(getApplicationContext()).observe(this, new Observer<List<UserModel>>() {
//            @Override
//            public void onChanged(@Nullable List<UserModel> userModels) {
//                try {
//                    tv.setText(userModels.toString());
//                } catch (Exception ex) {
//                    tv.setText(ex.toString());
//                    Log.e("MainActivity", ex.toString());
//                }
//            }
//        });

//        AlbumsViewModel albumsViewModel = ViewModelProviders.of(this).get(AlbumsViewModel.class);
//        albumsViewModel.getAlbums(getApplicationContext()).observe(this, new Observer<List<AlbumModel>>() {
//            @Override
//            public void onChanged(@Nullable List<AlbumModel> albumModels) {
//                try {
//                    tv.setText(albumModels.toString());
//                } catch (Exception ex) {
//                    tv.setText(ex.toString());
//                    Log.e("MainActivity", ex.toString()  );
//                }
//            }
//        });

//        PostsViewModel postsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
//        postsViewModel.getPosts().observe(this, new Observer<List<PostModel>>() {
//            @Override
//            public void onChanged(@Nullable List<PostModel> postModels) {
//                try {
//                    tv.setText(postModels.toString());
//                } catch (Exception ex) {
//                    tv.setText(ex.toString());
//                    Log.e("MainActivity", ex.toString());
//                }
//            }
//        });
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new PostsListFragment();
            } else if(position == 1 ) {
                return new AlbumsListFragment();
            }
            return new PostsListFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
//            switch(position) {
//                case 0 :
//                    return "Posts";
//                case 1 :
//                    return "Albums";
//            }
            return super.getPageTitle(position);
        }
    }
}
