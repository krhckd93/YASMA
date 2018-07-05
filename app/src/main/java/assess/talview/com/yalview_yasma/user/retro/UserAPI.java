package assess.talview.com.yalview_yasma.user.retro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserAPI {

    @GET("users")
    Call<List<UserModel>> getUsers();

    @GET("users/{id}")
    Call<UserModel> getUser(@Path("id") int id);
}
