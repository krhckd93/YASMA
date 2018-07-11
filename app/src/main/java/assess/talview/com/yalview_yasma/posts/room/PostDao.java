package assess.talview.com.yalview_yasma.posts.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id DESC")
    LiveData<List<PostModel>> getPosts();

    @Query("SELECT * FROM posts WHERE id=:post_id")
    LiveData<PostModel> getPostById(int post_id);

    @Query("SELECT * FROM posts WHERE user_id=:user_id")
    LiveData<List<PostModel>> getUserPosts(int  user_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPost(PostModel post);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<PostModel> posts);

    @Query("DELETE FROM posts")
    void deleteAll();

    @Delete
    void deletePost(PostModel post);

    @Query("DELETE FROM posts WHERE id=:post_id")
    void deleteById(int post_id);

}
