package com.example.inikolovski.mvpsampleproject.features.books;

import com.example.inikolovski.mvpsampleproject.common.util.schedulers.TestSchedulerProvider;
import com.example.inikolovski.mvpsampleproject.data.BaseRepository;
import com.example.inikolovski.mvpsampleproject.data.Book;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link BookPresenter}.
 */

@RunWith(MockitoJUnitRunner.class)
public class BookPresenterTest {
    @Mock
    BookContract.View view;

    @Mock
    BaseRepository repository;

    private BookPresenter presenter;

    private final List<Book> BOOK_LIST = new ArrayList<>();
    private final Book BOOK = new Book("", "", "");

    @Before
    public void setupPresenter() {
        final TestSchedulerProvider schedulerProvider = new TestSchedulerProvider();
        presenter = new BookPresenter(repository, schedulerProvider);
        presenter.registerView(view);
    }


    @Test
    public void getAllBooks() {
        when(repository.getAllBooks()).thenReturn(Flowable.just(BOOK_LIST));

        presenter.loadAllBooks();

        verify(view).showAllBooks(BOOK_LIST);
    }


    @Test
    public void getAllBooksFailure() {
        when(repository.getAllBooks()).thenReturn(Flowable.<List<Book>>error(new Exception("reason")));

        presenter.loadAllBooks();

        verify(view, times(1)).onFailure("reason");
    }

    @Test
    public void getAllBooksFailure_() {
        when(repository.getAllBooks()).thenReturn(Flowable.<List<Book>>error(new Exception("reason")));

        presenter.loadAllBooks();

        verify(view, never())
                .showAllBooks(Collections.EMPTY_LIST);
    }


    @Test
    public void deleteBook() {
        when(repository.deleteBook(BOOK)).thenReturn(Observable.just(BOOK));

        int ADAPTER_POSITION = 0;
        presenter.deleteBook(BOOK, ADAPTER_POSITION);

        verify(view).successfullyDeletedBook(BOOK.getTitle(), ADAPTER_POSITION);
    }

    @Test
    public void verifyGetAllBooksInteractionOrder() {
        when(repository.getAllBooks()).thenReturn(Flowable.just(BOOK_LIST));

        presenter.loadAllBooks();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).setLoadingIndicator(true);
        inOrder.verify(view).setLoadingIndicator(false);
        inOrder.verify(view).showAllBooks(BOOK_LIST);
    }


}
