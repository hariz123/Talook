package id.talook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.talook.Adapter.PostAdapter;
import id.talook.Model.Item;
import id.talook.Model.PostList;
import id.talook.Network.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArtikelFragment extends Fragment {
    Unbinder unbinder;
    RecyclerView recyclerView;
    LinearLayoutManager manager;

    PostAdapter adapter;
    List<Item> items = new ArrayList<>();

    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    String token = "";

    public ArtikelFragment() {
        // Required empty public constructor
    }

    public static ArtikelFragment newInstance() {
        ArtikelFragment fragment = new ArtikelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artikel, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView = (RecyclerView) view.findViewById(R.id.postlist);
        manager = new LinearLayoutManager(getContext());

        adapter = new PostAdapter(getContext(), items);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    getData();
                }
            }
        });
        getData();
        return view;

    }

    private void getData() {
        String url = RetrofitHelper.url + "?key=" + RetrofitHelper.key;
        if (token != "") {
            url = url + "&pageToken=" + token;
        }
        if (token == null) {
            return;
        }
//        progress.setVisibility(View.VISIBLE);
//
        final Call<PostList> postList = RetrofitHelper.getService().getPostList(url);
        postList.enqueue(new Callback<PostList>(){
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList list = response.body();
//                token = list.getNextPageToken();
                items.addAll(list.getItems());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(new PostAdapter(getContext(),list.getItems()));
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

//                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
