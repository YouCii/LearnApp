package com.youcii.mvplearn.presenter;

import com.youcii.mvplearn.base.BasePresenter;
import com.youcii.mvplearn.fragment.interfaces.IFragRxView;
import com.youcii.mvplearn.model.EasyEvent;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author YouCii
 * @date 2016/12/17
 */
public class FragRxPresenter extends BasePresenter<IFragRxView> {

    /**
     * 使用subscribe返回disposable的方式, 其他方式
     *
     * @see com.youcii.mvplearn.activity.IPMovieActivity
     */
    private Disposable disposable;

    public FragRxPresenter(@NotNull IFragRxView iFragRxView) {
        super(iFragRxView);
    }

    private void addText(String string) {
        if (getView() != null) {
            getView().addText(string);
        }
    }

    public void rxTest(EasyEvent[] events) {
        ObservableOnSubscribe<EasyEvent[]> onSubscribe = new ObservableOnSubscribe<EasyEvent[]>() {
            /**
             * 当 Observable 被订阅的时候，ObservableOnSubscribe 的 subscribe() 方法会自动被调用: 观察者
             */
            @Override
            public void subscribe(ObservableEmitter<EasyEvent[]> observableEmitter) throws Exception {
                addText("onSubscribe: " + Thread.currentThread().getName() + "\n");
                observableEmitter.onNext(events);
                observableEmitter.onComplete();
            }
        };

        // 三者等价 1. Observable.create(onSubscribe); 2. Observable.just("Hello", "Hi", "Aloha"); 3. Observable.from(new String{"Hello", "Hi", "Aloha"});
        disposable = Observable.create(onSubscribe)
                // ObservableOnSubscribe 被激活时所处的线程, 即ObservableOnSubscribe.subscribe执行的线程
                .subscribeOn(Schedulers.io())
                // 事件分发前的准备工作, 类似于subscriber的onSubscribe, 但是可以指定线程
                .doOnSubscribe(disposable -> addText("doOnSubscribe: " + Thread.currentThread().getName() + "\n"))
                // doOnSubscribe
                .subscribeOn(Schedulers.newThread())
                // 下方flatMap等
                .observeOn(Schedulers.newThread())
                .flatMap(easyEvents -> {
                    addText("flatMap: " + Thread.currentThread().getName() + "\n");
                    return Observable.fromArray(events);
                })
                .map(eEvent -> {
                    addText("map: " + Thread.currentThread().getName() + "\n");
                    return eEvent.pitPatGetCallBack;
                })
                .filter(s -> {
                    addText("filter: " + Thread.currentThread().getName() + "\n");
                    return s != null;
                })
                .take(2)
                //subscriber
                .observeOn(AndroidSchedulers.mainThread())
                // 链接, 订阅开始, onSubscribe.开始执行;
                // 这次使用 LambdaObserver, 这种方式可以在subscribe时返回dispose对象
                .subscribe(string -> addText("onNext: " + Thread.currentThread().getName() + "\n"),
                        throwable -> addText("onError: " + Thread.currentThread().getName() + "\n\n"),
                        () -> addText("onCompleted: " + Thread.currentThread().getName() + "\n\n"),
                        disposable -> addText("onStart: " + Thread.currentThread().getName() + "\n") // 事件分发前的准备工作, 仅能执行在 subscribe 所发生的线程, 不能指定线程
                );
    }

    @Override
    public void detach() {
        disposable.dispose();
    }
}
