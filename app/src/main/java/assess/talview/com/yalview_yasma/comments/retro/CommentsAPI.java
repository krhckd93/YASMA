package assess.talview.com.yalview_yasma.comments.retro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommentsAPI {

    @GET("posts/{id}/comments")
    Call<List<CommentsModel>> getPostComments(@Path("id") int Id);
}
