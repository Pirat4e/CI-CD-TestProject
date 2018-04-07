package com.example.inikolovski.mvpsampleproject.features.start;


import com.example.inikolovski.mvpsampleproject.common.PresenterFactory;
import com.example.inikolovski.mvpsampleproject.common.util.schedulers.BaseSchedulerProvider;
import com.example.inikolovski.mvpsampleproject.data.BaseRepository;

class MainPresenterFactory implements PresenterFactory<MainPresenter> {
    private final BaseRepository repository;
    private final BaseSchedulerProvider schedulerProvider;

    MainPresenterFactory(BaseRepository repository, BaseSchedulerProvider schedulerProvider) {
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public MainPresenter create() {
        return new MainPresenter(repository, schedulerProvider);
    }
}
