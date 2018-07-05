package assess.talview.com.yalview_yasma.album.photos.retro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhotosAPI {

    @GET("albums/{id}/photos")
    public Call<List<PhotosModel>> getAlbumPhotos(@Path("id") int album_id);

}
