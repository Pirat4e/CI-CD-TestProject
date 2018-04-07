package com.example.inikolovski.mvpsampleproject.features.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.inikolovski.mvpsampleproject.R;
import com.example.inikolovski.mvpsampleproject.common.BasePresenterActivity;
import com.example.inikolovski.mvpsampleproject.common.PresenterFactory;
import com.example.inikolovski.mvpsampleproject.common.util.schedulers.SchedulerProvider;
import com.example.inikolovski.mvpsampleproject.data.AppDataRepository;
import com.example.inikolovski.mvpsampleproject.data.Book;
import com.example.inikolovski.mvpsampleproject.features.books.BookActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.inikolovski.mvpsampleproject.common.util.StringUtil.EMPTY_STRING;

public class MainActivity extends BasePresenterActivity<MainPresenter, MainContract.View> implements MainContract.View {

    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_author)
    EditText edtAuthor;
    @BindView(R.id.edt_genre)
    EditText edtGenre;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        final String title = edtTitle.getText().toString();
        final String author = edtAuthor.getText().toString();
        final String genre = edtGenre.getText().toString();

        if (title.equals(EMPTY_STRING) || author.equals(EMPTY_STRING) || genre.equals(EMPTY_STRING))
            showSnackBar(view, getString(R.string.error_message), Snackbar.LENGTH_SHORT);
        else
            presenter.addBook(new Book(title, author, genre));
    }

    @OnClick(R.id.txt_all_books)
    public void onSeeAllBooksClick() {
        startActivity(new Intent(this, BookActivity.class));
    }

    @NonNull
    @Override
    protected String tag() {
        return "MainActivity";
    }

    @NonNull
    @Override
    protected PresenterFactory<MainPresenter> getPresenterFactory() {
        return new MainPresenterFactory(new AppDataRepository(), SchedulerProvider.getInstance());
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onFailure(String errorMessage) {
        showSnackBar(floatingActionButton, errorMessage, Snackbar.LENGTH_LONG);
    }

    @Override
    public void successfullyAddedBook(String title) {
        showSnackBar(floatingActionButton, getString(R.string.successfully_added_book, title), Snackbar.LENGTH_LONG);
    }
}
