package assess.talview.com.yalview_yasma.posts.comments;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.posts.comments.retro.CommentsController;
import assess.talview.com.yalview_yasma.posts.comments.retro.CommentsModel;
import assess.talview.com.yalview_yasma.posts.comments.room.CommentModel;

public class CommentsViewModel extends AndroidViewModel {
    
    private LiveData<List<CommentModel>> comments;
    private CommentRepository commentRepository;

    public CommentsViewModel(@NonNull Application application) {
        super(application);
        commentRepository = new CommentRepository(application);
    }

    public LiveData<List<CommentModel>> getComments(int post_id) {
        if(comments == null) {
            comments = commentRepository.getComments(post_id);
        }
        return comments;
    }
}
