package com.example.inikolovski.mvpsampleproject.common.util.schedulers;


import android.support.annotation.NonNull;

import io.reactivex.Scheduler;


public interface BaseSchedulerProvider {
    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
