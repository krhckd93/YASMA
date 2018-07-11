package assess.talview.com.yalview_yasma.album.room;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import assess.talview.com.yalview_yasma.user.room.UserModel;

@Entity(tableName = "album", foreignKeys = { @ForeignKey(entity = UserModel.class, parentColumns = "id", childColumns = "user_id")},
    indices = {@Index(name = "user_id", value = "user_id", unique = false)})
public class AlbumModel {
    @PrimaryKey
    public int id;

    public String title;

    @ColumnInfo(name = "user_id")
    public int userId;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "\n\n" + this.id + " " + this.userId + " " + this.title;
    }
}
