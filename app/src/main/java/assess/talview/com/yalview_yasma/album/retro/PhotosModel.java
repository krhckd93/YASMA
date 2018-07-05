package assess.talview.com.yalview_yasma.album.retro;

public class PhotosModel {
    int albumId;
    int id;
    String title;
    String url;
    String thumbnailUrl;

    public int getAlbumId() { return albumId; }

    public void setAlbumId(int albumId) { this.albumId = albumId; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getThumbnailUrl() { return thumbnailUrl; }

    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    @Override
    public String toString() {
        return "\n" + this.albumId + " " + this.id + " " + this.title + " " + this.url + " " + this.thumbnailUrl;
    }
}
