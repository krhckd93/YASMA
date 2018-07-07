package assess.talview.com.yalview_yasma.album.retro;

public class AlbumModel {
    private int userId;
    private int id;
    private String title;

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() {
        return "\n" + this.userId + " " + this.id + " " + this.title;
    }
}
