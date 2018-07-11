package assess.talview.com.yalview_yasma.posts;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.db.AppDatabase;
import assess.talview.com.yalview_yasma.posts.retro.PostsController;
import assess.talview.com.yalview_yasma.posts.retro.PostsModel;
import assess.talview.com.yalview_yasma.posts.room.PostDao;
import assess.talview.com.yalview_yasma.posts.room.PostModel;
import assess.talview.com.yalview_yasma.user.UserRepository;
import assess.talview.com.yalview_yasma.user.retro.UserController;
import assess.talview.com.yalview_yasma.user.room.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsRepository implements PostsInterface {

    private LiveData<List<PostModel>> posts;
    private PostDao postDao;
    private UserRepository userRepository;

    public PostsRepository() {}

    public PostsRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInMemoryDatabase(context);
        postDao = appDatabase.postDao();
        userRepository = new UserRepository(context);
        posts = postDao.getPosts();
    }

    public LiveData<List<PostModel>> getPosts() {
        // TODO: 08-Jul-18 Check for last update time, if greater than the threshold then sync with server and write to database
        PostsController.GetPosts(this);
        if(posts == null) {
            if(postDao != null)
                posts = postDao.getPosts();
        }
        return posts;
    }

    public LiveData<PostModel> getPostById(int id) {
        if(postDao != null) {
            return postDao.getPostById(id);
        } else {
            return null;
        }
    }

    public void insertPost(PostModel postModel) {
        new InsertPostWithUserAsync(postDao, userRepository).execute(postModel);
    }

    public void fetchPost(int id) {
        PostsController.getPost(this, id);
    }

    @Override
    public void postGetPosts(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            // TODO: 05-Jul-18 Handle fetch failure
            Log.e("PostsRepository", baseResponse.getError());
            return;
        }
        new ConvertAndInsertPostsAsync(postDao, userRepository).execute((List<PostsModel>) baseResponse.getResult());
    }

    @Override
    public void postGetPost(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            // TODO: 05-Jul-18 Handle fetch failure
            Log.e("PostsRepository", baseResponse.getError());
            return;
        }
        new ConvertAndInsertPostAsync(postDao, userRepository).execute((PostsModel) baseResponse.getResult());
    }

    public static PostModel castRetroModelToRoomModel(PostsModel retro_post) {
        PostModel room_post = new PostModel();
        room_post.setId(retro_post.getId());
        room_post.setUserId(retro_post.getUserId());
        room_post.setBody(retro_post.getBody());
        room_post.setTitle(retro_post.getTitle());
        return room_post;
    }

    public static List<PostModel> castRetroModelListToRoomModelList(List<PostsModel> retro_posts) {
        ArrayList<PostModel> room_posts = new ArrayList<PostModel>();
        PostModel temp_post;
        for(PostsModel post : retro_posts) {
            temp_post = castRetroModelToRoomModel(post);
            room_posts.add(temp_post);
        }
        return room_posts;
    }

    public static void InsertPostsWithUser(final PostDao postDao, final UserRepository userRepository, List<PostModel> posts) {
        if(posts != null && posts.size() > 0) {
            ListIterator<PostModel> iterator = posts.listIterator(posts.size());
            while (iterator.hasPrevious()) {
                InsertPostWithUser(postDao, userRepository, iterator.previous());
            }
        }
//        for(PostModel post: posts) {
//            InsertPostWithUser(postDao, userRepository, post);
//        }
    }

    public static void InsertPostWithUser(final PostDao postDao, final UserRepository userRepository, final PostModel post) {
        if(postDao.getPostById(post.getId()).getValue() == null ) {
            if(userRepository.getUserById(post.getUserId()).getValue() != null) {
                postDao.insertPost(post);
            } else {
                final PostModel insert_post = post;
                UserController.getUser(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            UserModel room_user = UserRepository.castRetroModelToRoomModel((assess.talview.com.yalview_yasma.user.retro.UserModel) response.body());
                            userRepository.insertUserAsync(room_user);
                            // TODO: 09-Jul-18 Insert Post
                            new InsertPostAsync(postDao).execute(post);
//                                albumDao.insertAlbum(insert_album);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                }, post.getUserId());
            }
        }
    }

    public static class ConvertAndInsertPostsAsync extends AsyncTask<List<PostsModel>, Void, Void> {

        private PostDao asyncPostDao;
        private UserRepository asyncUserRepository;

        public ConvertAndInsertPostsAsync(PostDao asyncPostDao, UserRepository asyncUserRepository) {
            this.asyncPostDao = asyncPostDao;
            this.asyncUserRepository = asyncUserRepository;
        }

        @Override
        protected Void doInBackground(List<PostsModel>... lists) {
            List<PostModel> posts = castRetroModelListToRoomModelList(lists[0]);
            InsertPostsWithUser(asyncPostDao, asyncUserRepository, posts);
            return null;
        }
    }

    public static class ConvertAndInsertPostAsync extends AsyncTask<PostsModel, Void, Void> {

        private PostDao asyncPostDao;
        private UserRepository asyncUserRepository;

        public ConvertAndInsertPostAsync(PostDao asyncPostDao, UserRepository asyncUserRepository) {
            this.asyncPostDao = asyncPostDao;
            this.asyncUserRepository = asyncUserRepository;
        }

        @Override
        protected Void doInBackground(PostsModel... list) {
            PostModel post = castRetroModelToRoomModel(list[0]);
            InsertPostWithUser(asyncPostDao, asyncUserRepository, post);
            return null;
        }
    }

    public static class InsertPostsWithAsync extends AsyncTask<List<PostModel>, Void, Void> {

        private PostDao asyncPostDao;
        private UserRepository asyncUserRepository;

        public InsertPostsWithAsync(PostDao asyncPostDao, UserRepository asyncUserRepository) {
            this.asyncPostDao = asyncPostDao;
            this.asyncUserRepository = asyncUserRepository;
        }

        @Override
        protected Void doInBackground(List<PostModel>... lists) {
            InsertPostsWithUser(asyncPostDao, asyncUserRepository, lists[0]);
            return null;
        }
    }

    public static class InsertPostWithUserAsync extends AsyncTask<PostModel, Void, Void> {

        private PostDao asyncPostDao;
        private UserRepository asyncUserRepository;

        public InsertPostWithUserAsync(PostDao asyncPostDao, UserRepository asyncUserRepository) {
            this.asyncPostDao = asyncPostDao;
            this.asyncUserRepository = asyncUserRepository;
        }

        @Override
        protected Void doInBackground(PostModel... list) {
            InsertPostWithUser(asyncPostDao, asyncUserRepository, list[0]);
            return null;
        }
    }

    public static class InsertPostsAsync extends AsyncTask<List<PostModel>, Void, Void> {

        private PostDao asyncPostDao;

        public InsertPostsAsync(PostDao asyncPostDao) {
            this.asyncPostDao = asyncPostDao;
        }

        @Override
        protected Void doInBackground(List<PostModel>... lists) {
            asyncPostDao.insertAll(lists[0]);
            return null;
        }
    }

    public static class InsertPostAsync extends AsyncTask<PostModel, Void, Void> {

        private PostDao asyncPostDao;

        public InsertPostAsync(PostDao asyncPostDao) {
            this.asyncPostDao = asyncPostDao;
        }

        @Override
        protected Void doInBackground(PostModel... post) {
            asyncPostDao.insertPost(post[0]);
            return null;
        }
    }
}
