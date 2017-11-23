package com.youcii.mvplearn.presenter.fragment;

import com.youcii.mvplearn.model.EasyEvent;
import com.youcii.mvplearn.view.fragment.interfaces.IFragRxView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
		Observable.OnSubscribe<EasyEvent[]> onSubscribe = new Observable.OnSubscribe<EasyEvent[]>() {
			/**
			 * 当 Observable 被订阅的时候，onSubscribe 的 call() 方法会自动被调用: 观察者
			 */
			@Override
			public void call(Subscriber<? super EasyEvent[]> subscriber) {
				iFragRxView.addText("onSubscribe: " + Thread.currentThread().getName() + "\n");
				subscriber.onNext(events);
				subscriber.onCompleted();
			}
		};

		// 可理解为Observer的扩展类
		Subscriber<String> subscriber = new Subscriber<String>() {
			/**
			 * 事件分发前的准备工作, 仅能执行在 subscribe 所发生的线程, 不能指定线程
			 */
			@Override
			public void onStart() {
				super.onStart();
				iFragRxView.addText("onStart: " + Thread.currentThread().getName() + "\n");
			}

			@Override
			public void onNext(String string) {
				iFragRxView.addText("onNext: " + Thread.currentThread().getName() + "\n");
			}

			@Override
			public void onCompleted() {
				iFragRxView.addText("onCompleted: " + Thread.currentThread().getName() + "\n\n");
				// Subscriber 内的方法, 用于反注册, 防止出现内存泄漏的问题,一般在onPause/onStop中调用
				unsubscribe();
			}

			@Override
			public void onError(Throwable e) {
				iFragRxView.addText("onError: " + Thread.currentThread().getName() + "\n\n");
				unsubscribe();
			}
		};

		// 三者等价 1. Observable.create(onSubscribe); 2. Observable.just("Hello", "Hi", "Aloha"); 3. Observable.from(new String{"Hello", "Hi", "Aloha"});
		Observable.create(onSubscribe)
				// Observable.OnSubscribe 被激活时所处的线程, 即Observable.OnSubscribe.call执行的线程
				.subscribeOn(Schedulers.io())
				// 事件分发前的准备工作, 类似于subscriber的onStart, 但是可以指定线程
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						iFragRxView.addText("doOnSubscribe: " + Thread.currentThread().getName() + "\n");
					}
				})
				// doOnSubscribe
				.subscribeOn(Schedulers.newThread())
				.observeOn(Schedulers.newThread())
				.flatMap(new Func1<EasyEvent[], Observable<EasyEvent>>() {
					@Override
					public Observable<EasyEvent> call(EasyEvent[] events) {
						iFragRxView.addText("flatMap: " + Thread.currentThread().getName() + "\n");
						return Observable.from(events);
					}
				})
				.map(new Func1<EasyEvent, String>() {
					@Override // 把EasyEvent转化为String
					public String call(EasyEvent eEvent) {
						iFragRxView.addText("map: " + Thread.currentThread().getName() + "\n");
						return eEvent.pitPatGetCallBack;
					}
				})
				.filter(new Func1<String, Boolean>() {
					@Override
					public Boolean call(String s) {
						iFragRxView.addText("filter: " + Thread.currentThread().getName() + "\n");
						return s != null;
					}
				})
				.limit(2)
				//subscriber
				.observeOn(AndroidSchedulers.mainThread())
				// 链接, 订阅开始, onSubscribe.开始执行;  ps: 返回Subscriber, 用于unSubscribe()
				.subscribe(subscriber);
	}

}
