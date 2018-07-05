package assess.talview.com.yalview_yasma.album.photos.retro;

import java.util.List;

import assess.talview.com.yalview_yasma.album.photos.PhotosInterface;
import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.helper.RetroHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoController {
    public static void getAlbumPhotos(final PhotosInterface photosInterface, int album_id) {
        Retrofit retrofit = RetroHelper.getClient();
        PhotosAPI photosAPI = retrofit.create(PhotosAPI.class);
        photosAPI.getAlbumPhotos(album_id).enqueue(new Callback<List<PhotosModel>>() {
            @Override
            public void onResponse(Call<List<PhotosModel>> call, Response<List<PhotosModel>> response) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(response.body());
                photosInterface.postGetAlbumPhotos(baseResponse);
            }

            @Override
            public void onFailure(Call<List<PhotosModel>> call, Throwable t) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setError(t.toString());
                photosInterface.postGetAlbumPhotos(baseResponse);
            }
        });
    }
}
