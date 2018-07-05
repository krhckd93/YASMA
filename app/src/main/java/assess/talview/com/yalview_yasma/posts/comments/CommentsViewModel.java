package assess.talview.com.yalview_yasma.posts.comments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.posts.comments.retro.CommentsController;
import assess.talview.com.yalview_yasma.posts.comments.retro.CommentsModel;

public class CommentsViewModel extends ViewModel implements CommentsInterface {
    
    private MutableLiveData<List<CommentsModel>> comments;
    
    public LiveData<List<CommentsModel>> getComments(int post_id) {
        if(comments == null) {
            comments = new MutableLiveData<List<CommentsModel>>();
        }
        CommentsController.getPostComments(this, post_id);
        return comments;
    }

    @Override
    public void postGetComments(BaseResponse baseResponse) {
        if (baseResponse.getError() != null) {
            Log.e("CommentsViewModel", baseResponse.getError());
            return;
        }
        // TODO: 05-Jul-18 Handle datatype check and mismatch 
        comments.setValue((List<CommentsModel>) baseResponse.getResult());
    }
}
