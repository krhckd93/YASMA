package assess.talview.com.yalview_yasma.album.photos;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import assess.talview.com.yalview_yasma.album.AlbumRepository;
import assess.talview.com.yalview_yasma.album.photos.retro.PhotoController;
import assess.talview.com.yalview_yasma.album.photos.retro.PhotosModel;
import assess.talview.com.yalview_yasma.album.photos.room.PhotoDao;
import assess.talview.com.yalview_yasma.album.photos.room.PhotoModel;
import assess.talview.com.yalview_yasma.album.room.AlbumModel;
import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.db.AppDatabase;

public class PhotoRepository implements PhotosInterface{
    private LiveData<List<PhotoModel>> photos;
    private PhotoDao photoDao;
    private AlbumRepository albumRepository;

    public PhotoRepository(Context context) {
        photoDao = AppDatabase.getInMemoryDatabase(context).photoDao();
        albumRepository = new AlbumRepository(context);
    }

    public LiveData<List<PhotoModel>> getAlbumPhotos(int album_id) {
        PhotoController.getAlbumPhotos(this, album_id);
        if(photos == null) {
            photos = photoDao.getAlbumPhotos(album_id);
        }
        return photos;
    }

    public List<PhotoModel> getAlbumPhotosObjects(int album_id) {
        return photoDao.getAlbumPhotosObjects(album_id);
    }

    public PhotoModel getPhotoObjectById(int photo_id) {
        return photoDao.getPhotoObjectById(photo_id);
    }

    @Override
    public void postGetAlbumPhotos(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            Log.e("PhotoRepository", baseResponse.getError());
            return;
        }
        new ConvertAndInsertPhotosWithAlbumsAsync(photoDao, albumRepository).execute((List<PhotosModel>) baseResponse.getResult());
    }

    private PhotoModel castRetroModelToRoomModel(PhotosModel retro_photo) {
        PhotoModel photoModel = new PhotoModel();
            photoModel.setId(retro_photo.getId());
            photoModel.setAlbumId(retro_photo.getAlbumId());
            photoModel.setTitle(retro_photo.getTitle());
            photoModel.setThumbnail_url(retro_photo.getThumbnailUrl());
            photoModel.setUrl(retro_photo.getUrl());
        return photoModel;
    }

    private List<PhotoModel> castRetroModelListToRoomModelList(List<PhotosModel> retro_list) {
        ArrayList<PhotoModel> photos = new ArrayList<>();
        PhotoModel temp;
        for(PhotosModel photo : retro_list) {
            temp = castRetroModelToRoomModel(photo);
            photos.add(temp);
        }
        return photos;
    }

    private  void InsertPhotoWithAlbum(PhotoDao photoDao, AlbumRepository albumRepository, PhotoModel photo) {
        if(photoDao.getPhotoObjectById(photo.getId()) == null ) {
            if(albumRepository.getAlbumObjectById(photo.getAlbumId()) != null) {
                photoDao.insertPhoto(photo);
            } else {
                albumRepository.fetchAlbum(photo.getAlbumId());
                photoDao.insertPhoto(photo);
            }
        }
    }

    private void InsertPhotosWithAlbums(PhotoDao photoDao, AlbumRepository albumRepository, List<PhotoModel> photos) {
        if(photos != null && photos.size() > 0) {
            ListIterator<PhotoModel> iterator = photos.listIterator(photos.size());
            while (iterator.hasPrevious()) {
                InsertPhotoWithAlbum(photoDao, albumRepository, iterator.previous());
            }
        }
    }

    public class InsertPhotoAsync extends AsyncTask<PhotoModel, Void, Void> {
        private PhotoDao photoDao;

        private InsertPhotoAsync(PhotoDao photoDao) {
            this.photoDao = photoDao;
        }

        @Override
        protected Void doInBackground(PhotoModel... photoModels) {
            photoDao.insertPhoto(photoModels[0]);
            return null;
        }
    }

    public class InsertPhotosAsync extends AsyncTask<List<PhotoModel>, Void, Void> {
        private PhotoDao photoDao;

        private InsertPhotosAsync(PhotoDao photoDao) {
            this.photoDao = photoDao;
        }

        @Override
        protected Void doInBackground(List<PhotoModel>... photoModels) {
            photoDao.insertAll(photoModels[0]);
            return null;
        }
    }

    public class InsertPhotoWithAlbumAsync extends AsyncTask<PhotoModel, Void, Void> {
        private PhotoDao photoDao;
        private AlbumRepository albumRepository;

        private InsertPhotoWithAlbumAsync(PhotoDao photoDao, AlbumRepository albumRepository) {
            this.photoDao = photoDao;
            this.albumRepository = albumRepository;
        }

        @Override
        protected Void doInBackground(PhotoModel... photoModels) {
            InsertPhotoWithAlbum(photoDao, albumRepository, photoModels[0]);
            return null;
        }
    }

    public class InsertPhotosWithAlbumsAsync extends AsyncTask<List<PhotoModel>, Void, Void> {
        private PhotoDao photoDao;
        private AlbumRepository albumRepository;

        private InsertPhotosWithAlbumsAsync(PhotoDao photoDao, AlbumRepository albumRepository) {
            this.photoDao = photoDao;
            this.albumRepository = albumRepository;
        }

        @Override
        protected Void doInBackground(List<PhotoModel>... photoModels) {
            InsertPhotosWithAlbums(photoDao, albumRepository, photoModels[0]);
            return null;
        }
    }

    public class ConvertAndInsertPhotosWithAlbumsAsync extends AsyncTask<List<PhotosModel>, Void, Void> {
        private PhotoDao photoDao;
        private AlbumRepository albumRepository;

        private ConvertAndInsertPhotosWithAlbumsAsync(PhotoDao photoDao, AlbumRepository albumRepository) {
            this.photoDao = photoDao;
            this.albumRepository = albumRepository;
        }

        @Override
        protected Void doInBackground(List<PhotosModel>... lists) {
            List<PhotoModel> photos = castRetroModelListToRoomModelList(lists[0]);
            InsertPhotosWithAlbums(photoDao, albumRepository, photos);
            return null;
        }
    }

    public class ConvertAndInsertPhotoWithAlbumAsync extends AsyncTask<PhotosModel, Void, Void> {
        private PhotoDao photoDao;
        private AlbumRepository albumRepository;

        private ConvertAndInsertPhotoWithAlbumAsync(PhotoDao photoDao, AlbumRepository albumRepository) {
            this.photoDao = photoDao;
            this.albumRepository = albumRepository;
        }

        @Override
        protected Void doInBackground(PhotosModel... lists) {
            PhotoModel photos = castRetroModelToRoomModel(lists[0]);
            InsertPhotoWithAlbum(photoDao, albumRepository, photos);
            return null;
        }
    }
}
