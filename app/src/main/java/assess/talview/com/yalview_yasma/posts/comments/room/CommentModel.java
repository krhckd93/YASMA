package assess.talview.com.yalview_yasma.posts.comments.room;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import assess.talview.com.yalview_yasma.posts.room.PostModel;
import assess.talview.com.yalview_yasma.user.room.UserModel;

@Entity(tableName = "comments",
        foreignKeys = {@ForeignKey(entity = PostModel.class, parentColumns = "id", childColumns = "post_id")},
        indices = {@Index(name = "post_index", value = "post_id")})
public class CommentModel {
    @PrimaryKey
    int id;

    @ColumnInfo(name = "post_id")
    int postId;

    String name;
    String email;
    String body;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getPostId() { return postId; }

    public void setPostId(int postId) { this.postId = postId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    @Override
    public String toString() {
        return "CommentModel{" +
                "id=" + id +
                ", postId=" + postId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
