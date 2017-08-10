package com.sh.ilovepooq.main.presenter;

import com.sh.ilovepooq.main.MainGridContract;
import com.sh.ilovepooq.model.ContentInfoModel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.TestSubscriber;

public class MainGridPresenterTest {

    private MainGridContract.View mockView;
    private MainGridContract.Repository mockRepository;
    private MainGridPresenter presenter;

    @Before
    public void setup() {
        mockView = Mockito.mock(MainGridContract.View.class);
        mockRepository = Mockito.mock(MainGridContract.Repository.class);

        presenter = new MainGridPresenter(mockRepository);
        presenter.setView(mockView);
    }

    @BeforeClass
    public static void setUpRxSchedulers() {
        final Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(new ScheduledThreadPoolExecutor(1) {
                    @Override
                    public void execute(Runnable runnable) {
                        runnable.run();
                    }
                });
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable)
                    throws Exception {
                return immediate;
            }
        });
        RxJavaPlugins
                .setInitComputationSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable)
                            throws Exception {
                        return immediate;
                    }
                });
        RxJavaPlugins
                .setInitNewThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable)
                            throws Exception {
                        return immediate;
                    }
                });
        RxJavaPlugins
                .setInitSingleSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable)
                            throws Exception {
                        return immediate;
                    }
                });
        RxAndroidPlugins
                .setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable)
                            throws Exception {
                        return immediate;
                    }
                });
    }

    @Test
    public void showList() throws Exception {
        // 1. 임의의 List<ContentInfoModel> list 객체 생성
        List<ContentInfoModel> list = Collections.singletonList(new ContentInfoModel("imageUrl",
                "alt", "title", "hyperlink"));

        // 2. Mock Repository - parsing() 반환 값을 미리 정의
        Mockito.when(mockRepository.parsing()).thenReturn(Single.just(list));

        // 3. Presenter - startParsing() 호출
        presenter.startParsing();

        // 4. TestSubscriber 인스턴스 생성
        TestSubscriber<List<ContentInfoModel>> testSubscriber = TestSubscriber.create();

        // 5. Single<ContentInfoModel> 객체를 Flowable로 변환 후, subscribe
        mockRepository.parsing().toFlowable().subscribe(testSubscriber);

        // 6. 미리 정의된 view-showList(list)를 한번 호출하는지 테스트
        Mockito.verify(mockView, Mockito.times(1)).showList(list);
    }

    @Test
    public void showEmpty() throws Exception {
        // 1. 임의의 값이 비어있는 List<ContentInfoModel> list 객체 생성
        List<ContentInfoModel> list = new ArrayList<>();

        // 2. Mock Repository - parsing() 반환 값을 미리 정의
        Mockito.when(mockRepository.parsing()).thenReturn(Single.just(list));

        // 3. Presenter - startParsing() 호출
        presenter.startParsing();

        // 4. TestSubscriber 인스턴스 생성
        TestSubscriber<List<ContentInfoModel>> testSubscriber = TestSubscriber.create();

        // 5. Single<ContentInfoModel> 객체를 Flowable로 변환 후, subscribe
        mockRepository.parsing().toFlowable().subscribe(testSubscriber);

        // 6. 미리 정의된 view-showEmpty()를 한번 호출하는지 테스트
        Mockito.verify(mockView, Mockito.times(1)).showEmpty();
    }

    @Test
    public void showError() throws Exception {
        // 1. Throwable throwable 객체 생성
        Throwable throwable = new Throwable("test");

        // 2. Mock Repository - parsing() 반환 값을 미리 정의
        Mockito.when(mockRepository.parsing())
                .thenReturn(Single.<List<ContentInfoModel>>error(throwable));

        // 3. Presenter - startParsing() 호출
        presenter.startParsing();

        // 4. TestSubscriber 인스턴스 생성
        TestSubscriber<List<ContentInfoModel>> testSubscriber = TestSubscriber.create();

        // 5. Single<ContentInfoModel> 객체를 Flowable로 변환 후, subscribe
        mockRepository.parsing().toFlowable().subscribe(testSubscriber);

        // 6. 미리 정의한 Throwable과 onError를 통해 받은 Throwable가 동일한지 테스트
        testSubscriber.assertErrorMessage(throwable.getMessage());

        // 7. 미리 정의된 view-showError()를 한번 호출하는지 테스트
        Mockito.verify(mockView, Mockito.times(1)).showError(throwable.getMessage());
    }

}
