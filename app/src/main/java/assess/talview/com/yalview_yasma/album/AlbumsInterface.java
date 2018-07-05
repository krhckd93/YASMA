package assess.talview.com.yalview_yasma.album;

import assess.talview.com.yalview_yasma.base.BaseResponse;

public interface AlbumsInterface {
    void postGetAlbums(BaseResponse baseResponse);

    void postGetAlbum(BaseResponse baseResponse);

    void postGetAlbumPhotos(BaseResponse baseResponse);
}