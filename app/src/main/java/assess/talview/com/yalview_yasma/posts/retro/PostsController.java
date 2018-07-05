package assess.talview.com.yalview_yasma.posts.retro;

import java.util.List;

import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.posts.PostsInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static assess.talview.com.yalview_yasma.helper.RetroHelper.getClient;

public class PostsController {

    public static void GetPosts(final PostsInterface postsInterface) {
        Retrofit retrofit = getClient();
        PostsAPI api_interface = retrofit.create(PostsAPI.class);
        api_interface.GetPosts().enqueue(new Callback<List<PostsModel>>() {
            @Override
            public void onResponse(Call<List<PostsModel>> call, Response<List<PostsModel>> response) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(response.body());
                postsInterface.postGetPosts(baseResponse);
            }

            @Override
            public void onFailure(Call<List<PostsModel>> call, Throwable t) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setError(t.toString());
                postsInterface.postGetPosts(baseResponse);
            }
        });
    }

    public static void getPost(final PostsInterface postsInterface, int id) {
        Retrofit retrofit = getClient();
        PostsAPI api_interface = retrofit.create(PostsAPI.class);
        api_interface.getPost(id).enqueue(new Callback<PostsModel>() {
            @Override
            public void onResponse(Call<PostsModel> call, Response<PostsModel> response) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(response.body());
                postsInterface.postGetPost(baseResponse);

            }

            @Override
            public void onFailure(Call<PostsModel> call, Throwable t) {

                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setError(t.toString());
                postsInterface.postGetPost(baseResponse);
            }
        });
    }
}
