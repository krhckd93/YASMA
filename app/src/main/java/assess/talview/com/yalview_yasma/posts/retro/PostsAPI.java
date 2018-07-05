package assess.talview.com.yalview_yasma.posts.retro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostsAPI {

    @GET("posts")
    public Call<List<PostsModel>> GetPosts();
}
