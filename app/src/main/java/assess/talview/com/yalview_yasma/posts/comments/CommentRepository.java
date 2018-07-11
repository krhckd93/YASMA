package assess.talview.com.yalview_yasma.posts.comments;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import assess.talview.com.yalview_yasma.base.BaseResponse;
import assess.talview.com.yalview_yasma.db.AppDatabase;
import assess.talview.com.yalview_yasma.posts.PostsRepository;
import assess.talview.com.yalview_yasma.posts.comments.retro.CommentsController;
import assess.talview.com.yalview_yasma.posts.comments.retro.CommentsModel;
import assess.talview.com.yalview_yasma.posts.comments.room.CommentDao;
import assess.talview.com.yalview_yasma.posts.comments.room.CommentModel;

public class CommentRepository implements CommentsInterface {

    private CommentDao commentDao;
    private PostsRepository postsRepository;
    private LiveData<List<CommentModel>> comments;

    public CommentRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInMemoryDatabase(context);
        commentDao = appDatabase.commentDao();
        postsRepository = new PostsRepository(context);
    }

    public LiveData<List<CommentModel>> getComments(int post_id) {
        CommentsController.getPostComments(this, post_id);
        if (comments == null) {
            if (commentDao != null)
                comments = commentDao.getPostComments(post_id);
        }
        return comments;
    }

    @Override
    public void postGetComments(BaseResponse baseResponse) {
        if (baseResponse.getError() != null) {
            Log.e("CommentsRepository", baseResponse.getError());
            return;
        }

        new ConvertAndInsertCommentsWithPostsAsync(commentDao, postsRepository).execute((List<CommentsModel>) baseResponse.getResult());
    }

    public void insertComment(CommentModel comment) {
        new InsertCommentAsync(commentDao).execute(comment);
    }

    public void insertComments(List<CommentModel> comments) {
        new InsertCommentsAsync(commentDao).execute(comments);
    }

    private static CommentModel castRetroModelToRoomModel(CommentsModel retro_comment) {
        CommentModel room_comment = new CommentModel();
        room_comment.setId(retro_comment.getId());
        room_comment.setPostId(retro_comment.getPostId());
        room_comment.setName(retro_comment.getName());
        room_comment.setEmail(retro_comment.getEmail());
        room_comment.setBody(retro_comment.getBody());
        return room_comment;
    }

    private static List<CommentModel> castRetroModelListToRoomModelList(List<CommentsModel> retro_comments) {
        ArrayList<CommentModel> room_comments = new ArrayList<CommentModel>();
        CommentModel temp_comment;
        for (CommentsModel comment : retro_comments) {
            temp_comment = castRetroModelToRoomModel(comment);
            room_comments.add(temp_comment);
        }
        return room_comments;
    }

    private class InsertCommentAsync extends AsyncTask<CommentModel, Void, Void> {
        private CommentDao commentDao;

        private InsertCommentAsync(CommentDao commentDao) {
            this.commentDao = commentDao;
        }

        @Override
        protected Void doInBackground(CommentModel... commentModels) {
            commentDao.insertComment(commentModels[0]);
            return null;
        }
    }

    private class InsertCommentsAsync extends AsyncTask<List<CommentModel>, Void, Void> {
        private CommentDao commentDao;

        private InsertCommentsAsync(CommentDao commentDao) {
            this.commentDao = commentDao;
        }

        @Override
        protected Void doInBackground(List<CommentModel>... lists) {
            commentDao.insertAll(lists[0]);
            return null;
        }
    }

    private class InsertCommentWithPostAsync extends AsyncTask<CommentModel, Void, Void> {

        private CommentDao asyncCommentDao;
        private PostsRepository asyncPostRepository;

        private InsertCommentWithPostAsync(CommentDao asyncCommentDao, PostsRepository asyncPostRepository) {
            this.asyncCommentDao = asyncCommentDao;
            this.asyncPostRepository = asyncPostRepository;
        }

        @Override
        protected Void doInBackground(CommentModel... commentModels) {
            InsertCommentWithPost(commentDao, postsRepository, commentModels[0]);
            return null;
        }
    }

    private class InsertCommentsWithPostsAsync extends AsyncTask<List<CommentModel>, Void, Void> {

        private CommentDao asyncCommentDao;
        private PostsRepository asyncPostRepository;

        private InsertCommentsWithPostsAsync(CommentDao asyncCommentDao, PostsRepository asyncPostRepository) {
            this.asyncCommentDao = asyncCommentDao;
            this.asyncPostRepository = asyncPostRepository;
        }

        @Override
        protected Void doInBackground(List<CommentModel>... commentModels) {
            InsertCommentsWithPosts(commentDao, postsRepository, commentModels[0]);
            return null;
        }
    }

    private class ConvertAndInsertCommentWithPostAsync extends AsyncTask<CommentsModel, Void, Void> {

        private CommentDao asyncCommentDao;
        private PostsRepository asyncPostRepository;

        private ConvertAndInsertCommentWithPostAsync(CommentDao asyncCommentDao, PostsRepository asyncPostRepository) {
            this.asyncCommentDao = asyncCommentDao;
            this.asyncPostRepository = asyncPostRepository;
        }

        @Override
        protected Void doInBackground(CommentsModel... commentModels) {
            CommentModel comment = castRetroModelToRoomModel(commentModels[0]);
            InsertCommentWithPost(commentDao, postsRepository, comment);
            return null;
        }
    }

    private class ConvertAndInsertCommentsWithPostsAsync extends AsyncTask<List<CommentsModel>, Void, Void> {

        private CommentDao asyncCommentDao;
        private PostsRepository asyncPostRepository;

        private ConvertAndInsertCommentsWithPostsAsync(CommentDao asyncCommentDao, PostsRepository asyncPostRepository) {
            this.asyncCommentDao = asyncCommentDao;
            this.asyncPostRepository = asyncPostRepository;
        }

        @Override
        protected Void doInBackground(List<CommentsModel>... commentModels) {
            InsertCommentsWithPosts(commentDao, postsRepository, castRetroModelListToRoomModelList(commentModels[0]));
            return null;
        }
    }


    private void InsertCommentWithPost(CommentDao commentDao, final PostsRepository postsRepository, CommentModel comment) {
        if (commentDao.getCommentById(comment.getId()).getValue() == null) {
            if (postsRepository.getPostById(comment.getPostId()).getValue() != null) {
                insertComment(comment);
            } else {
                try {
                    postsRepository.fetchPost(comment.getPostId());
                    new InsertCommentAsync(commentDao).execute(comment);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void InsertCommentsWithPosts(CommentDao commentDao, final PostsRepository postsRepository, List<CommentModel> comments) {
        if(comments != null && comments.size() > 0) {
            ListIterator<CommentModel> iterator = comments.listIterator(comments.size());
            while (iterator.hasPrevious()) {
                InsertCommentWithPost(commentDao, postsRepository, iterator.previous());
            }
        }
    }

}