package com.example.inikolovski.mvpsampleproject.features.books;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.inikolovski.mvpsampleproject.R;
import com.example.inikolovski.mvpsampleproject.common.BasePresenterActivity;
import com.example.inikolovski.mvpsampleproject.common.PresenterFactory;
import com.example.inikolovski.mvpsampleproject.common.ui.RecyclerItemItemCustomClickListener;
import com.example.inikolovski.mvpsampleproject.common.util.schedulers.SchedulerProvider;
import com.example.inikolovski.mvpsampleproject.data.AppDataRepository;
import com.example.inikolovski.mvpsampleproject.data.Book;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BookActivity extends BasePresenterActivity<BookPresenter, BookContract.View> implements BookContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private BookAdapter adapter;
    private BookPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_activity_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        setupAdapter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadAllBooks();
        presenter.registerSwipeToRefresh(refreshLayout);
    }

    @NonNull
    @Override
    protected String tag() {
        return "BooksActivity";
    }

    @NonNull
    @Override
    protected PresenterFactory<BookPresenter> getPresenterFactory() {
        return new BooksPresenterFactory(new AppDataRepository(), SchedulerProvider.getInstance());
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull BookPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onFailure(String errorMessage) {
        Timber.d(errorMessage);
        showSnackBar(recyclerView, errorMessage, Snackbar.LENGTH_LONG);
    }

    @Override
    public void showAllBooks(List<Book> books) {
        adapter.updateList(books);
    }

    @Override
    public void successfullyDeletedBook(String bookTitle, int position) {
        adapter.notifyItemRemoved(position);
        showSnackBar(recyclerView, String.format("The Book %s is successfully deleted", bookTitle),
                Snackbar.LENGTH_SHORT);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        refreshLayout.setRefreshing(active);
    }

    private void setupAdapter(Activity activity) {
        adapter = new BookAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemItemCustomClickListener(activity, recyclerView, new RecyclerItemItemCustomClickListener.OnItemCustomClickListener() {
            @Override
            public void onSingleTapUp(int position) {
                final Book book = adapter.getList().get(position);
                presenter.deleteBook(book, position);
            }
        }));
    }
}
