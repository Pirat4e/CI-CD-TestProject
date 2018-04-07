package com.example.inikolovski.mvpsampleproject.features.start;


import com.example.inikolovski.mvpsampleproject.common.util.schedulers.TestSchedulerProvider;
import com.example.inikolovski.mvpsampleproject.data.BaseRepository;
import com.example.inikolovski.mvpsampleproject.data.Book;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link MainPresenter}.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    @Mock
    MainContract.View view;

    @Mock
    BaseRepository repository;

    private MainPresenter presenter;

    private final String BOOK_TITLE = "title";
    private final Book BOOK = new Book(BOOK_TITLE, "", "");

    @Before
    public void setupPresenter() {
        final TestSchedulerProvider schedulerProvider = new TestSchedulerProvider();
        presenter = new MainPresenter(repository, schedulerProvider);
        presenter.registerView(view);
    }

    @Test
    public void insertBook() {
        when(repository.insertBook(BOOK)).thenReturn(Observable.just(BOOK));

        presenter.addBook(BOOK);

        verify(view).successfullyAddedBook(BOOK_TITLE);
    }
}
