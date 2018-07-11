package assess.talview.com.yalview_yasma.posts.comments.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CommentDao {

    @Query("SELECT * FROM comments ORDER BY post_id, id DESC")
    public LiveData<List<CommentModel>> getComments();

    @Query("SELECT * FROM comments WHERE id=:comment_id")
    public LiveData<CommentModel> getCommentById(int comment_id);

    @Query("SELECT * FROM comments WHERE post_id=:post_id ORDER BY id DESC")
    public LiveData<List<CommentModel>> getPostComments(int post_id);

    @Query("DELETE FROM comments")
    public void deleteAll();

    @Query("DELETE FROM comments WHERE id=:comment_id")
    public void deleteCommentById(int comment_id);

    @Delete
    public void deleteComment(CommentModel comment);

    @Delete
    public void deleteComments(List<CommentModel> comments);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertComment(CommentModel comment);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAll(List<CommentModel> comments);

}
