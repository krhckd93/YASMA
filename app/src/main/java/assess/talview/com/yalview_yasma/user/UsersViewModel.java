package assess.talview.com.yalview_yasma.user;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;
import assess.talview.com.yalview_yasma.user.room.UserModel;
public class UsersViewModel extends ViewModel {

    private LiveData<List<UserModel>> users;
    private UserRepository userRepository;

    // TODO: 08-Jul-18 Default contructor called, argument constructor needs to be called for initialization
    public UsersViewModel() {}

    public UsersViewModel(Context applicationContext) {
        userRepository = new UserRepository(applicationContext);
        users = userRepository.getUsers();
    }

    // TODO: 08-Jul-18 Context is being passed, find a workaround without the need of context
    public LiveData<List<UserModel>> getUsers(Context applicationContext) {
        if(users == null) {
            if(userRepository == null) { userRepository = new UserRepository(applicationContext); }
            users = userRepository.getUsers();
        }
        return users;
    }
}
