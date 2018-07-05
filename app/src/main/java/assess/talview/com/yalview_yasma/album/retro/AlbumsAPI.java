package assess.talview.com.yalview_yasma.album.retro;

import java.util.List;

import assess.talview.com.yalview_yasma.album.photos.retro.PhotosModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlbumsAPI {
    @GET("albums")
    Call<List<AlbumModel>> getAlbums();

    @GET("albums/{id}")
    Call<AlbumModel> getAlbum(@Path("id") int id);

    @GET("albums/{id}/photos")
    Call<List<PhotosModel>> getAlbumPhotos(@Path("id") int id);
}
