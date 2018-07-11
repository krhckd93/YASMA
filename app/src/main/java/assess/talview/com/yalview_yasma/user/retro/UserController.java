package assess.talview.com.yalview_yasma.user.retro;

import java.util.List;

import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.helper.RetroHelper;
import assess.talview.com.yalview_yasma.user.UserInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserController {

    public static void getUsers(final UserInterface userInterface) {
        Retrofit retrofit = RetroHelper.getClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        userAPI.getUsers().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(response.body());
                userInterface.postGetUsers(baseResponse);
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setError(t.toString());
                userInterface.postGetUsers(baseResponse);
            }
        });
    }

    public static void getUser(final UserInterface userInterface, int id) {
        Retrofit retrofit = RetroHelper.getClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        userAPI.getUser(id).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(response.body());
                userInterface.postGetUsers(baseResponse);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setError(t.toString());
                userInterface.postGetUsers(baseResponse);
            }
        });
    }

    public static void getUser(Callback callback, int id) {
        Retrofit retrofit = RetroHelper.getClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        userAPI.getUser(id).enqueue(callback);
    }
}
