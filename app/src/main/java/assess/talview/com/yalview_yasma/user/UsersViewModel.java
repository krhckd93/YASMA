package assess.talview.com.yalview_yasma.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.user.retro.UserController;
import assess.talview.com.yalview_yasma.user.retro.UserModel;

public class UsersViewModel extends ViewModel implements UserInterface {

    private MutableLiveData<List<UserModel>> users;
    private MutableLiveData<UserModel> user;


    public LiveData<List<UserModel>> getUsers() {
        if(users == null) {
            users = new MutableLiveData<List<UserModel>>();
        }
        UserController.getUsers(this);
        return users;
    }

    public LiveData<UserModel> getUser(int id) {
        if(user == null) {
            user = new MutableLiveData<UserModel>();
        }
        UserController.getUser(this, id);
        return user;
    }

    @Override
    public void postGetUsers(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            Log.e("UsersViewModel", baseResponse.getError());
            return;
        }
        if(users == null) {
            users = new MutableLiveData<List<UserModel>>();
        }
        // TODO: 05-Jul-18 Handle datatype check and mismatch
        users.setValue((List<UserModel>) baseResponse.getResult());
    }

    @Override
    public void postGetUser(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            Log.e("UsersViewModel", baseResponse.getError());
            return;
        }
        if(user == null) {
            user = new MutableLiveData<UserModel>();
        }

        // TODO: 05-Jul-18 Handle datatype check and mismatch
        user.setValue((UserModel) baseResponse.getResult());
    }
}
