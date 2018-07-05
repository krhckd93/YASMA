package assess.talview.com.yalview_yasma.album.retro;

import java.util.List;

import assess.talview.com.yalview_yasma.album.AlbumsInterface;
import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.helper.RetroHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AlbumsController {

    public static void getAlbums(final AlbumsInterface albumsInterface) {
        Retrofit retrofit = RetroHelper.getClient();
        AlbumsAPI albumsAPI = retrofit.create(AlbumsAPI.class);
        albumsAPI.getAlbums().enqueue(new Callback<List<AlbumModel>>() {
            @Override
            public void onResponse(Call<List<AlbumModel>> call, Response<List<AlbumModel>> response) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(response.body());
                albumsInterface.postGetAlbums(baseResponse);
            }

            @Override
            public void onFailure(Call<List<AlbumModel>> call, Throwable t) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(t);
                albumsInterface.postGetAlbums(baseResponse);
            }
        });
    }

    public static void getAlbum(final AlbumsInterface albumsInterface, int id) {
        Retrofit retrofit = RetroHelper.getClient();
        AlbumsAPI albumsAPI = retrofit.create(AlbumsAPI.class);
        albumsAPI.getAlbum(id).enqueue(new Callback<AlbumModel>() {
            @Override
            public void onResponse(Call<AlbumModel> call, Response<AlbumModel> response) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(response.body());
                albumsInterface.postGetAlbum(baseResponse);
            }

            @Override
            public void onFailure(Call<AlbumModel> call, Throwable t) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(t);
                albumsInterface.postGetAlbum(baseResponse);
            }
        });
    }

    public static void getAlbumPhotos(final AlbumsInterface albumsInterface, int id) {
        Retrofit retrofit = RetroHelper.getClient();
        AlbumsAPI albumsAPI = retrofit.create(AlbumsAPI.class);
        albumsAPI.getAlbumPhotos(id).enqueue(new Callback<List<PhotosModel>>() {
            @Override
            public void onResponse(Call<List<PhotosModel>> call, Response<List<PhotosModel>> response) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(response.body());
                albumsInterface.postGetAlbumPhotos(baseResponse);
            }

            @Override
            public void onFailure(Call<List<PhotosModel>> call, Throwable t) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResult(t);
                albumsInterface.postGetAlbumPhotos(baseResponse);
            }
        });
    }

}
