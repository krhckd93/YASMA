package assess.talview.com.yalview_yasma.album.photos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import assess.talview.com.yalview_yasma.album.photos.retro.PhotoController;
import assess.talview.com.yalview_yasma.album.photos.retro.PhotosModel;
import assess.talview.com.yalview_yasma.base.BaseResponse;

public class PhotoViewModel extends ViewModel implements PhotosInterface {
    private MutableLiveData<List<PhotosModel>> photos;

    public LiveData<List<PhotosModel>> getAlbumPhotos(int album_id) {
        if(photos == null) {
            photos = new MutableLiveData<List<PhotosModel>>();
        }
        PhotoController.getAlbumPhotos(this, album_id);
        return photos;
    }

    @Override
    public void postGetAlbumPhotos(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            Log.e("PhotosViewModel", baseResponse.getError());
            return;
        }
        // TODO: 05-Jul-18 Handle datatype check
        photos.setValue((List<PhotosModel>) baseResponse.getResult());
    }
}
