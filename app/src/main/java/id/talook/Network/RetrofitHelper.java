package id.talook.Network;

import id.talook.Model.Item;
import id.talook.Model.PostList;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class RetrofitHelper {

    public static final String key = "AIzaSyDi4dkQ4h54JGXNDtKICOpTobo0CaKxSR0";
    public static final String url = "https://www.googleapis.com/blogger/v3/blogs/935537652039007175/posts/";
    public static PostService postService = null;

    public static PostService getService() {
        if (postService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postService = retrofit.create(PostService.class);
        }
        return postService;
    }

    public interface PostService {
        @GET
        Call<PostList> getPostList(@Url String url);

        @GET
        Call<Item> getId();
    }
}
