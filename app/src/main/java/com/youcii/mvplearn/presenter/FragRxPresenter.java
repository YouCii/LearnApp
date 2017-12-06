package com.youcii.mvplearn.presenter;

import com.youcii.mvplearn.model.EasyEvent;
import com.youcii.mvplearn.fragment.interfaces.IFragRxView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * @author YouCii
 * @date 2016/12/17
 */
public class FragRxPresenter {

	private IFragRxView iFragRxView;

	public FragRxPresenter(IFragRxView iFragRxView) {
		this.iFragRxView = iFragRxView;
	}

	public void rxTest(EasyEvent[] events) {
		ObservableOnSubscribe<EasyEvent[]> onSubscribe = new ObservableOnSubscribe<EasyEvent[]>() {
			/**
			 * 当 Observable 被订阅的时候，ObservableOnSubscribe 的 subscribe() 方法会自动被调用: 观察者
			 */
			@Override
			public void subscribe(ObservableEmitter<EasyEvent[]> observableEmitter) throws Exception {
				iFragRxView.addText("onSubscribe: " + Thread.currentThread().getName() + "\n");
				observableEmitter.onNext(events);
				observableEmitter.onComplete();
			}
		};

		// 可理解为Observer的扩展类
		Observer<String> observer = new Observer<String>() {
			/**
			 * 事件分发前的准备工作, 仅能执行在 subscribe 所发生的线程, 不能指定线程
			 */
			@Override
			public void onSubscribe(Disposable disposable) {
				iFragRxView.addText("onStart: " + Thread.currentThread().getName() + "\n");
			}

			@Override
			public void onNext(String string) {
				iFragRxView.addText("onNext: " + Thread.currentThread().getName() + "\n");
			}

			@Override
			public void onComplete() {
				iFragRxView.addText("onCompleted: " + Thread.currentThread().getName() + "\n\n");
			}

			@Override
			public void onError(Throwable e) {
				iFragRxView.addText("onError: " + Thread.currentThread().getName() + "\n\n");
			}
		};

		// 三者等价 1. Observable.create(onSubscribe); 2. Observable.just("Hello", "Hi", "Aloha"); 3. Observable.from(new String{"Hello", "Hi", "Aloha"});
		Observable.create(onSubscribe)
				// ObservableOnSubscribe 被激活时所处的线程, 即ObservableOnSubscribe.subscribe执行的线程
				.subscribeOn(Schedulers.io())
				// 事件分发前的准备工作, 类似于subscriber的onSubscribe, 但是可以指定线程
				.doOnSubscribe(new Consumer<Disposable>() {
					@Override
					public void accept(Disposable disposable) throws Exception {
						iFragRxView.addText("doOnSubscribe: " + Thread.currentThread().getName() + "\n");
					}
				})
				// doOnSubscribe
				.subscribeOn(Schedulers.newThread())
				.observeOn(Schedulers.newThread())
				.flatMap(new Function<EasyEvent[], Observable<EasyEvent>>() {
					@Override
					public Observable<EasyEvent> apply(EasyEvent[] easyEvents) throws Exception {
						iFragRxView.addText("flatMap: " + Thread.currentThread().getName() + "\n");
						return Observable.fromArray(events);
					}
				})
				.map(new Function<EasyEvent, String>() {
					@Override // 把EasyEvent转化为String
					public String apply(EasyEvent eEvent) {
						iFragRxView.addText("map: " + Thread.currentThread().getName() + "\n");
						return eEvent.pitPatGetCallBack;
					}
				})
				.filter(new Predicate<String>() {
					@Override
					public boolean test(String s) throws Exception {
						iFragRxView.addText("filter: " + Thread.currentThread().getName() + "\n");
						return s != null;
					}
				})
				.take(2)
				//subscriber
				.observeOn(AndroidSchedulers.mainThread())
				// 链接, 订阅开始, onSubscribe.开始执行;  ps: 返回Subscriber, 用于unSubscribe()
				.subscribe(observer);
	}

}
