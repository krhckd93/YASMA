package assess.talview.com.yalview_yasma.comments.retro;

import java.util.List;

import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.comments.CommentsInterface;
import assess.talview.com.yalview_yasma.helper.RetroHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentsController {

    public static void getPostComments(final CommentsInterface commentsInterface, int post_id) {
         Retrofit retrofit = RetroHelper.getClient();
         CommentsAPI commentsAPI = retrofit.create(CommentsAPI.class);
         commentsAPI.getPostComments(post_id).enqueue(new Callback<List<CommentsModel>>() {
             @Override
             public void onResponse(Call<List<CommentsModel>> call, Response<List<CommentsModel>> response) {
                 BaseResponse baseResponse = new BaseResponse();
                 baseResponse.setResult(response.body());
                 commentsInterface.postGetComments(baseResponse);
             }

             @Override
             public void onFailure(Call<List<CommentsModel>> call, Throwable t) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setError(t.toString());
                commentsInterface.postGetComments(baseResponse);
             }
         });
    }
}
