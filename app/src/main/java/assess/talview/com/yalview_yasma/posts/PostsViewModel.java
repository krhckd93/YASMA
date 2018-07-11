package assess.talview.com.yalview_yasma.posts;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


import assess.talview.com.yalview_yasma.posts.room.PostModel;

public class PostsViewModel extends AndroidViewModel {

    private LiveData<List<PostModel>> posts;
    private LiveData<PostModel> post;
    private PostsRepository postsRepository;

    public PostsViewModel(@NonNull Application application) {
        super(application);
        postsRepository = new PostsRepository(application);
    }

    public LiveData<List<PostModel>> getPosts() {
        if(posts == null) {
            posts = postsRepository.getPosts();
        }
        return posts;
    }

    public LiveData<PostModel> getPost(int id) {
        if(post == null) {
            post = postsRepository.getPostById(id);
        }
        return post;
    }


}
