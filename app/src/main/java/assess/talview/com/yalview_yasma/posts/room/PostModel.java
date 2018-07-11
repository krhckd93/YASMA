package assess.talview.com.yalview_yasma.posts.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;

import assess.talview.com.yalview_yasma.user.room.UserModel;

@Entity(tableName = "posts",
        foreignKeys = {@ForeignKey(parentColumns = "id",
                childColumns = "user_id",
                entity = UserModel.class,
                onDelete = OnConflictStrategy.IGNORE )},
        indices = { @Index(name = "user+_index", value = "user_id")}
)
public class PostModel {
    @PrimaryKey
    int id;

    @ColumnInfo(name = "user_id")
    int userId;

    String title;

    String body;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    @Override
    public String toString() {
        return "PostModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
