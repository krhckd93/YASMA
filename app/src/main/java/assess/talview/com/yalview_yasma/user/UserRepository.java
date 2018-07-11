package assess.talview.com.yalview_yasma.user;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.db.AppDatabase;
import assess.talview.com.yalview_yasma.user.retro.UserController;
import assess.talview.com.yalview_yasma.user.room.UserDao;
import assess.talview.com.yalview_yasma.user.room.UserModel;
import assess.talview.com.yalview_yasma.user.room.submodels.Address;
import assess.talview.com.yalview_yasma.user.room.submodels.Company;
import assess.talview.com.yalview_yasma.user.room.submodels.Geo;

public class UserRepository implements UserInterface {


    private UserDao userDao;
    private LiveData<List<UserModel>> users;

    public UserRepository(Context application) {
        AppDatabase appDatabase = AppDatabase.getInMemoryDatabase(application);
        userDao = appDatabase.userDao();
        users = userDao.getAllUsers();
    }

    public LiveData<List<UserModel>> getUsers() {
        UserController.getUsers(this);
        if(userDao != null)
            return userDao.getAllUsers();
        else
            return null;
    };

    public LiveData<UserModel> getUserById(int user_id) {
        if(userDao != null)
            return userDao.getUserById(user_id);
        else
            return null;
    };

    public UserModel getUserObjectById(int user_id) {
        if(userDao != null)
            return userDao.getUserObjectById(user_id);
        else
            return null;
    };

    public void insertUsers(List<UserModel> users) {
        userDao.insertAll(users);
    }

    public void insertUser(UserModel user) {
        userDao.insertUser(user);
    }


    public void insertUsersAsync(List<UserModel> users) {

        new InsertUsersAsync(userDao).execute(users);
    }

    public void insertUserAsync(UserModel user) {
        new InsertUserAsync(userDao).execute(user);
    }

    @Override
    public void postGetUsers(BaseResponse baseResponse) {
        // TODO: 07-Jul-18  Convert Retro User model to Room User model and insert records
        // TODO: 07-Jul-18 Handle datatype check and mismatch
        if(baseResponse.getError() != null) {
            Log.e("UserRepository", baseResponse.getError());
            return;
        }
        new ConvertAndInsertUsersAsync(userDao).execute((List<assess.talview.com.yalview_yasma.user.retro.UserModel>)baseResponse.getResult());
    }

    @Override
    public void postGetUser(BaseResponse baseResponse) {
        // TODO: 07-Jul-18  Convert Retro User model to Room User model and insert records
        // TODO: 07-Jul-18 Handle datatype check and mismatch
        if(baseResponse.getError() != null) {
            Log.e("UserRepository", baseResponse.getError());
            return;
        }
        new ConvertAndInsertUsersAsync(userDao).execute((List<assess.talview.com.yalview_yasma.user.retro.UserModel>)baseResponse.getResult());

    }

    public static class ConvertAndInsertUsersAsync extends AsyncTask<List<assess.talview.com.yalview_yasma.user.retro.UserModel>, Void, Void > {
        private UserDao asyncUserDao;

        public ConvertAndInsertUsersAsync(UserDao userDao) {
            asyncUserDao = userDao;
        }

        @Override
        protected Void doInBackground(List<assess.talview.com.yalview_yasma.user.retro.UserModel>... users) {
            List<UserModel> room_users = castRetroModelListToRoomModelList(users[0]);
            asyncUserDao.insertAll(room_users);
            return null;
        }
    }

    public static class ConvertAndInsertUserAsync extends AsyncTask<assess.talview.com.yalview_yasma.user.retro.UserModel, Void, Void > {
        private UserDao asyncUserDao;

        public ConvertAndInsertUserAsync(UserDao userDao) {
            asyncUserDao = userDao;
        }

        @Override
        protected Void doInBackground(assess.talview.com.yalview_yasma.user.retro.UserModel... users) {
            UserModel room_user = castRetroModelToRoomModel(users[0]);
            asyncUserDao.insertUser(room_user);
            return null;
        }
    }

    public static class InsertUsersAsync extends AsyncTask<List<UserModel>, Void, Void > {
        private UserDao asyncUserDao;

        public InsertUsersAsync(UserDao userDao) {
            asyncUserDao = userDao;
        }

        @Override
        protected Void doInBackground(List<UserModel>... users) {
            asyncUserDao.insertAll(users[0]);
            return null;
        }
    }

    public static class InsertUserAsync extends AsyncTask<UserModel, Void, Void > {
        private UserDao asyncUserDao;

        public InsertUserAsync(UserDao userDao) {
            asyncUserDao = userDao;
        }

        @Override
        protected Void doInBackground(UserModel... user) {
            asyncUserDao.insertUser(user[0]);
            return null;
        }
    }

    private static List<UserModel> castRetroModelListToRoomModelList(List<assess.talview.com.yalview_yasma.user.retro.UserModel> users) {
        ArrayList<UserModel> users_room = new ArrayList<>();
        UserModel temp_user;
        for(assess.talview.com.yalview_yasma.user.retro.UserModel user : users) {
            temp_user = castRetroModelToRoomModel(user);
            users_room.add(temp_user);
        }
        return users_room;
    }

    public static UserModel castRetroModelToRoomModel(assess.talview.com.yalview_yasma.user.retro.UserModel user) {
        UserModel temp_user;
        Address temp_address;
        Company temp_company;
        Geo temp_geo;

        temp_user = new UserModel();
        temp_user.setId(user.getId());
        temp_user.setEmail(user.getEmail());
        temp_user.setName(user.getName());
        temp_user.setPhone(user.getPhone());
        temp_user.setUsername(user.getUsername());
        temp_user.setWebsite(user.getWebsite());

        temp_geo = new Geo(user.getAddress().getGeo().getLat(), user.getAddress().getGeo().getLng());
        temp_address = new Address();
        temp_address.setSuite(user.getAddress().getSuite());
        temp_address.setStreet(user.getAddress().getStreet());
        temp_address.setCity(user.getAddress().getCity());
        temp_address.setZipcode(user.getAddress().getZipcode());
        temp_address.setGeo(temp_geo);
        temp_user.setAddress(temp_address);

        temp_company = new Company();
        temp_company.setBs(user.getCompany().getBs());
        temp_company.setCatchPhrase(user.getCompany().getCatchPhrase());
        temp_company.setName(user.getCompany().getName());

        temp_user.setCompany(temp_company);

        return temp_user;
    }
}
