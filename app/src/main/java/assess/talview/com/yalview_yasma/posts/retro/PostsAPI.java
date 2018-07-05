package assess.talview.com.yalview_yasma.posts.retro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostsAPI {

    @GET("posts")
    public Call<List<PostsModel>> GetPosts();

    @GET("posts/{id}")
    public Call<PostsModel> getPost(@Path("id") int id);
}
