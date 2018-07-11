package assess.talview.com.yalview_yasma.album;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import assess.talview.com.yalview_yasma.album.retro.AlbumsController;
import assess.talview.com.yalview_yasma.album.room.AlbumDao;
import assess.talview.com.yalview_yasma.album.room.AlbumModel;
import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.db.AppDatabase;
import assess.talview.com.yalview_yasma.user.UserRepository;
import assess.talview.com.yalview_yasma.user.retro.UserController;
import assess.talview.com.yalview_yasma.user.room.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository implements AlbumsInterface {

    private LiveData<List<AlbumModel>> albums;
    private AlbumDao albumDao;
    private UserRepository userRepository;

    public AlbumRepository() {}

    public AlbumRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInMemoryDatabase(context);
        albumDao = appDatabase.albumDao();
        userRepository = new UserRepository(context);
        albums = albumDao.getAllAlbums();
    }

    public LiveData<List<AlbumModel>> getAlbums() {
        // TODO: 08-Jul-18 Check for last update time, if greater than the threshold then sync with server and write to database
        AlbumsController.getAlbums(this);
        if(albumDao != null)
            return albumDao.getAllAlbums();
        else
            return null;
    }

    public LiveData<AlbumModel> getAlbumById(int id) {
        if(albumDao != null) {
            return albumDao.getAlbumById(id);
        } else {
            return null;
        }
    }

    public AlbumModel getAlbumObjectById(int id) {
        if(albumDao != null) {
            return albumDao.getAlbumObjectById(id);
        } else {
            return null;
        }
    }

    public void fetchAlbum(int album_id) {
        AlbumsController.getAlbum(this, album_id);
    }

    public void insertAlbums(List<AlbumModel> albums) {
        // TODO: 08-Jul-18 change to async
        if(albumDao != null)
            albumDao.insertAll(albums);
    }

    public void insertAlbum(AlbumModel album) {
        // TODO: 08-Jul-18 change to async
        if(albumDao != null)
            albumDao.insertAlbum(album);
    }



    @Override
    public void postGetAlbums(BaseResponse baseResponse) {
        // TODO: 08-Jul-18 Handle Errors
        // TODO: 08-Jul-18 Handle datatype check and mismatch
//        java.lang.ClassCastException: java.net.UnknownHostException cannot be cast to java.util.List
        if (baseResponse.getError() != null) {
            Log.e("AlbumRepository", baseResponse.getError());
            return;
        }
        if (baseResponse.getResult() != null) {
            if(baseResponse.getResult().getClass().toString().equals("class java.net.UnknownHostException")) {
                return;
            }
            new ConvertAndInsertAlbumsAsync(albumDao, userRepository).execute((List<assess.talview.com.yalview_yasma.album.retro.AlbumModel>) baseResponse.getResult());

        }
    }
    @Override
    public void postGetAlbum(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            // TODO: 05-Jul-18 Handle fetch failure
            Log.e("PostsViewModel", baseResponse.getError());
            return;
        }
        // TODO: 08-Jul-18 Handle datatype check and mismatch
        new ConvertAndInsertAlbumAsync(albumDao, userRepository).execute((assess.talview.com.yalview_yasma.album.retro.AlbumModel)baseResponse.getResult());
    }

    @Override
    public void postGetAlbumPhotos(BaseResponse baseResponse) {

    }



    // TODO: 08-Jul-18 Make Async tasks static
    public class ConvertAndInsertAlbumsAsync extends AsyncTask<List<assess.talview.com.yalview_yasma.album.retro.AlbumModel>, Void, Void> {

        private AlbumDao asyncAlbumDao;
        private UserRepository asyncUserRepository;

        public ConvertAndInsertAlbumsAsync(AlbumDao albumDao, UserRepository userRepository) {
            asyncAlbumDao = albumDao;
            asyncUserRepository = userRepository;
        }

        @Override
        protected Void doInBackground(List<assess.talview.com.yalview_yasma.album.retro.AlbumModel>... lists) {
            try {
                List<AlbumModel> room_albums = castRetroModelListToRoomModelList(lists[0]);
                InsertAlbumsWithUser(asyncAlbumDao, asyncUserRepository, room_albums);
//                asyncAlbumDao.insertAll(room_albums);

            } catch (Exception ex) {
                Log.e("AlbumRepository", ex.toString());
            }
            return null;
        }


    }

    public class InsertAlbumsWithUserAsync extends AsyncTask<List<AlbumModel>, Void, Void> {

        private AlbumDao asyncAlbumDao;
        private UserRepository asyncUserRepository;

        public InsertAlbumsWithUserAsync(AlbumDao albumDao, UserRepository userRepository) {
            asyncAlbumDao = albumDao;
            asyncUserRepository = userRepository;
        }

        @Override
        protected Void doInBackground(List<AlbumModel>... lists) {
            try {
                InsertAlbumsWithUser(albumDao, userRepository, lists[0]);
//                asyncAlbumDao.insertAll(room_albums);

            } catch (Exception ex) {
                Log.e("AlbumRepository", ex.toString());
            }
            return null;
        }


    }

    public class InsertAlbumWithAsync extends AsyncTask<AlbumModel, Void, Void> {
        private AlbumDao asyncAlbumDao;
        private UserRepository asyncUserRepository;

        public InsertAlbumWithAsync(AlbumDao asyncAlbumDao, UserRepository userRepository) {
            this.asyncAlbumDao = asyncAlbumDao;
            this.asyncUserRepository = userRepository;
        }

        @Override
        protected Void doInBackground(AlbumModel... albumModels) {
            InsertAlbumWithUser(asyncAlbumDao, asyncUserRepository, albumModels[0]);
            return null;
        }
    }
    public class InsertAlbumsAsync extends AsyncTask<List<AlbumModel>, Void, Void> {

        private AlbumDao asyncAlbumDao;

        public InsertAlbumsAsync(AlbumDao albumDao) {
            asyncAlbumDao = albumDao;
        }

        @Override
        protected Void doInBackground(List<AlbumModel>... lists) {
            try {
                asyncAlbumDao.insertAll(lists[0]);

            } catch (Exception ex) {
                Log.e("AlbumRepository", ex.toString());
            }
            return null;
        }


    }

    public class InsertAlbumAsync extends AsyncTask<AlbumModel, Void, Void> {
        private AlbumDao asyncAlbumDao;

        public InsertAlbumAsync(AlbumDao asyncAlbumDao) {
            this.asyncAlbumDao = asyncAlbumDao;
        }

        @Override
        protected Void doInBackground(AlbumModel... albumModels) {
            asyncAlbumDao.insertAlbum(albumModels[0]);
            return null;
        }
    }

    private class ConvertAndInsertAlbumAsync extends AsyncTask<assess.talview.com.yalview_yasma.album.retro.AlbumModel, Void, Void> {
        private AlbumDao asyncAlbumDao;
        private UserRepository asyncUserRepository;

        public ConvertAndInsertAlbumAsync(AlbumDao asyncAlbumDao, UserRepository userRepository) {
            this.asyncAlbumDao = asyncAlbumDao;
            this.asyncUserRepository = userRepository;
        }

        @Override
        protected Void doInBackground(assess.talview.com.yalview_yasma.album.retro.AlbumModel... albumModels) {
            try {
                InsertAlbumWithUser(asyncAlbumDao, asyncUserRepository, castRetroModelToRoomModel(albumModels[0]));
                return null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    private static List<AlbumModel> castRetroModelListToRoomModelList(List<assess.talview.com.yalview_yasma.album.retro.AlbumModel> retro_albums) {
        ArrayList<AlbumModel> room_albums = new ArrayList<>();
        AlbumModel temp_album;
        for(assess.talview.com.yalview_yasma.album.retro.AlbumModel album: retro_albums) {
            temp_album = castRetroModelToRoomModel(album);
            room_albums.add(temp_album);
        }
        return room_albums;
    }

    private static AlbumModel castRetroModelToRoomModel(assess.talview.com.yalview_yasma.album.retro.AlbumModel album) {
        AlbumModel temp_album;
        temp_album = new AlbumModel();
        temp_album.setId(album.getId());
        temp_album.setTitle(album.getTitle());
        temp_album.setUserId(album.getUserId());
        return temp_album;
    }

    private void InsertAlbumsWithUser(final AlbumDao albumDao, final UserRepository userRepository, List<AlbumModel> albums) {

        // TODO: 08-Jul-18 For each album :
        // TODO: 08-Jul-18  0. Check if the album exists
        // TODO: 08-Jul-18  1. Check if the user exists
        // TODO: 08-Jul-18  2. If it does not exist, then download the user from server and insert into the database
        // TODO: 08-Jul-18  3. Insert the album
//        for(AlbumModel album: albums) {
//            InsertAlbumWithUser(albumDao,userRepository, album);
//        }
        if(albums != null && albums.size() > 0) {
            ListIterator<AlbumModel> iterator = albums.listIterator(albums.size());
            while (iterator.hasPrevious()) {
                InsertAlbumWithUser(albumDao, userRepository, iterator.previous());
            }
        }
    }

    private void InsertAlbumWithUser(final AlbumDao albumDao, final UserRepository userRepository, AlbumModel album) {
        if(albumDao.getAlbumById(album.getId()).getValue() == null) {
            if(userRepository.getUserById(album.getUserId()).getValue() != null) {
                albumDao.insertAlbum(album);
            } else {
                final AlbumModel insert_album = album;
                UserController.getUser(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            UserModel room_user = UserRepository.castRetroModelToRoomModel((assess.talview.com.yalview_yasma.user.retro.UserModel) response.body());
                            userRepository.insertUserAsync(room_user);
                            new InsertAlbumAsync(albumDao).execute(insert_album);
//                                albumDao.insertAlbum(insert_album);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        // TODO: 08-Jul-18 Handle failure
                    }
                }, album.getUserId());
            }
        }
    }
}
