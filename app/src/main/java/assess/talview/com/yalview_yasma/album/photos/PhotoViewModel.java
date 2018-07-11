package assess.talview.com.yalview_yasma.album.photos;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import assess.talview.com.yalview_yasma.album.photos.retro.PhotoController;
import assess.talview.com.yalview_yasma.album.photos.room.PhotoModel;

public class PhotoViewModel extends AndroidViewModel {
    private LiveData<List<PhotoModel>> photos;
    private PhotoRepository photoRepository;

    public PhotoViewModel(@NonNull Application application) {
        super(application);
        photoRepository = new PhotoRepository(application);
    }

    public LiveData<List<PhotoModel>> getAlbumPhotos(int album_id) {
        if(photos == null) {
            photos = photoRepository.getAlbumPhotos(album_id);
        }
        return photos;
    }

    public List<PhotoModel> getAlbumPhotosObjects(int album_id) {
        return photoRepository.getAlbumPhotosObjects(album_id);
    }

    public PhotoModel getPhotoObjectById(int photo_id) {
        return photoRepository.getPhotoObjectById(photo_id);
    }
}


