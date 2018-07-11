package assess.talview.com.yalview_yasma.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import assess.talview.com.yalview_yasma.album.photos.room.PhotoDao;
import assess.talview.com.yalview_yasma.album.photos.room.PhotoModel;
import assess.talview.com.yalview_yasma.album.room.AlbumDao;
import assess.talview.com.yalview_yasma.album.room.AlbumModel;
import assess.talview.com.yalview_yasma.posts.comments.room.CommentDao;
import assess.talview.com.yalview_yasma.posts.comments.room.CommentModel;
import assess.talview.com.yalview_yasma.posts.room.PostDao;
import assess.talview.com.yalview_yasma.posts.room.PostModel;
import assess.talview.com.yalview_yasma.user.room.UserDao;
import assess.talview.com.yalview_yasma.user.room.UserModel;

@Database(entities = { UserModel.class, AlbumModel.class, PostModel.class, CommentModel.class, PhotoModel.class}, version = 1 )
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "talview-yasma-db";

    private static AppDatabase instance;

    public abstract UserDao userDao();

    public abstract AlbumDao albumDao();

    public abstract PostDao postDao();

    public abstract PhotoDao photoDao();

    public abstract CommentDao commentDao();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

}
