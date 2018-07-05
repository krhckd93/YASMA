package assess.talview.com.yalview_yasma.album;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import assess.talview.com.yalview_yasma.album.retro.AlbumModel;
import assess.talview.com.yalview_yasma.album.retro.AlbumsController;
import assess.talview.com.yalview_yasma.base.BaseResponse;

public class AlbumsViewModel extends ViewModel implements AlbumsInterface {
    private MutableLiveData<List<AlbumModel>> albums;
    private MutableLiveData<AlbumModel> album;

    public LiveData<List<AlbumModel>> getAlbums() {
        if(albums == null) {
            albums = new MutableLiveData<List<AlbumModel>>();
        }
        AlbumsController.getAlbums(this);
        return albums;
    }

    public LiveData<AlbumModel> getAlbum(int id) {
        if(album == null) {
            album = new MutableLiveData<AlbumModel>();
        }
        AlbumsController.getAlbum(this, id);
        return album;
    }

    @Override
    public void postGetAlbums(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            Log.e("AlbumsViewModel", baseResponse.getError());
            return;
        }
        if(albums == null) {
            albums = new MutableLiveData<List<AlbumModel>>();
        }
        // TODO: 05-Jul-18 Handle datatype check
        albums.setValue((List<AlbumModel>)baseResponse.getResult());
    }

    @Override
    public void postGetAlbum(BaseResponse baseResponse) {
        if(baseResponse.getError() != null) {
            Log.e("AlbumsViewModel", baseResponse.getError());
            return;
        }
        if(album == null) {
            album = new MutableLiveData<AlbumModel>();
        }
        // TODO: 05-Jul-18 Handle datatype check
        album.setValue((AlbumModel)baseResponse.getResult());
    }

    @Override
    public void postGetAlbumPhotos(BaseResponse baseResponse) {

    }
}
