package assess.talview.com.yalview_yasma.album;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import java.util.List;

import assess.talview.com.yalview_yasma.album.room.AlbumModel;
import assess.talview.com.yalview_yasma.album.retro.AlbumsController;
import assess.talview.com.yalview_yasma.base.BaseResponse;

public class AlbumsViewModel extends AndroidViewModel {

    private AlbumRepository albumRepository;
    private LiveData<List<AlbumModel>> albums;
    private LiveData<AlbumModel> album;

    public AlbumsViewModel(Application application) {
        super(application);
        albumRepository = new AlbumRepository(application);
        albums = albumRepository.getAlbums();
    }

    // TODO: 08-Jul-18 Context is being passed, find a workaround without the need of context
    public LiveData<List<AlbumModel>> getAlbums(Context context) {
        if(albums == null) {
            if(albumRepository == null)
                albumRepository = new AlbumRepository(context);
            albums = albumRepository.getAlbums();
        }
        return albums;
    }

    public LiveData<AlbumModel> getAlbum(Context context, int id) {
        if(album == null) {
            if(albumRepository == null)
                albumRepository = new AlbumRepository(context);
            album = albumRepository.getAlbumById(id);
        }
        return album;
    }
}
