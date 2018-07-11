package assess.talview.com.yalview_yasma.album.photos.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import assess.talview.com.yalview_yasma.album.room.AlbumModel;

@Entity(tableName = "photos",
        foreignKeys = {@ForeignKey(entity = AlbumModel.class, parentColumns = "id", childColumns = "album_id")},
        indices = {@Index(name = "index_album", value = "album_id")})
public class PhotoModel {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "album_id")
    private int albumId;

    private String title;
    private String url;
    private String thumbnail_url;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getAlbumId() { return albumId; }

    public void setAlbumId(int albumId) { this.albumId = albumId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getThumbnail_url() { return thumbnail_url; }

    public void setThumbnail_url(String thumbnail_url) { this.thumbnail_url = thumbnail_url; }
}
