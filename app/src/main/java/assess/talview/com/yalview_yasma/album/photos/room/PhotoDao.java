package assess.talview.com.yalview_yasma.album.photos.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public abstract class PhotoDao {

    @Query("SELECT * FROM photos WHERE album_id=:album_id ORDER BY id DESC")
    public abstract LiveData<List<PhotoModel>> getAlbumPhotos(int album_id);

    @Query("SELECT * FROM photos WHERE album_id=:album_id ORDER BY id DESC")
    public abstract List<PhotoModel> getAlbumPhotosObjects(int album_id);

    @Query("SELECT * FROM photos WHERE id=:photo_id ORDER BY id DESC")
    public abstract LiveData<PhotoModel> getPhotoById(int photo_id);

    @Query("SELECT * FROM photos WHERE id=:photo_id ORDER BY id DESC")
    public abstract PhotoModel getPhotoObjectById(int photo_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertAll(List<PhotoModel> photos);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertPhoto(PhotoModel photo);

    @Query("DELETE FROM photos")
    public abstract void deleteAll();
}
