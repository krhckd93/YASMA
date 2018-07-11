package assess.talview.com.yalview_yasma.album.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AlbumDao {

    @Query("SELECT * FROM album ORDER BY id DESC")
    public LiveData<List<AlbumModel>> getAllAlbums();

    @Query("SELECT * FROM album WHERE id=:id")
    public LiveData<AlbumModel> getAlbumById(int id);

    @Query("SELECT * FROM album WHERE id=:id")
    public AlbumModel getAlbumObjectById(int id);

    @Query("SELECT * FROM album WHERE user_id=:user_id")
    public LiveData<List<AlbumModel>> getUserAlbums(int user_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAll(List<AlbumModel> albums);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAlbum(AlbumModel album);

    @Query("DELETE FROM album")
    public void deleteAll();

    @Delete
    public void deleteAlbum(AlbumModel album);

    @Query("DELETE FROM album WHERE id=:id")
    public void deleteAlbumById(int id);

}
