package assess.talview.com.yalview_yasma.user.room;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM res_user")
    public LiveData<List<UserModel>> getAllUsers();

    @Query("SELECT * FROM res_user WHERE id=:id")
    public LiveData<UserModel> getUserById(int id);

    @Query("SELECT * FROM res_user WHERE id=:id")
    public UserModel getUserObjectById(int id);

    @Query("DELETE FROM res_user")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(UserModel user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<UserModel> users);

}
