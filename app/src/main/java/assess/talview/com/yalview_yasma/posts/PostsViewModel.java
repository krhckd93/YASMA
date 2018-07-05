package assess.talview.com.yalview_yasma.posts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.posts.retro.PostsController;
import assess.talview.com.yalview_yasma.posts.retro.PostsModel;

public class PostsViewModel extends ViewModel implements PostsInterface {

    private MutableLiveData<List<PostsModel>> posts;
    private MutableLiveData<PostsModel> post;

    public LiveData<List<PostsModel>> getPosts() {
        if(posts == null) {
            posts = new MutableLiveData<List<PostsModel>>();
        }
        PostsController.GetPosts(this);
        return posts;
    }

    public LiveData<PostsModel> getPost(int id) {
        if(post == null) {
            post = new MutableLiveData<PostsModel>();
        }
        PostsController.getPost(this, id);
        return post;
    }

    @Override
    public void postGetPosts(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            // TODO: 05-Jul-18 Handle fetch failure
            Log.e("PostsViewModel", baseResponse.getError());
            return;
        }
        if(posts == null) {
            posts = new MutableLiveData<List<PostsModel>>();
        }
        posts.setValue((List<PostsModel>)baseResponse.getResult());
    }

    @Override
    public void postGetPost(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            // TODO: 05-Jul-18 Handle fetch failure
            Log.e("PostsViewModel", baseResponse.getError());
            return;
        }
        if(post == null) {
            post = new MutableLiveData<PostsModel>();
        }
        post.setValue((PostsModel)baseResponse.getResult());
    }
}
