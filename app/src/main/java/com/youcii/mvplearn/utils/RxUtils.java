package com.youcii.mvplearn.utils;

import com.youcii.mvplearn.model.EasyEvent;
import com.youcii.mvplearn.view.fragment.interfaces.IFragRxView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by YouCii on 2016/12/17.
 */
public class RxUtils {

	public static void rxTest(EasyEvent[] events, IFragRxView iFragRxView) {

		Observable.OnSubscribe<EasyEvent[]> onSubscribe = new Observable.OnSubscribe<EasyEvent[]>() {
			@Override
			public void call(Subscriber<? super EasyEvent[]> subscriber) {
				iFragRxView.addText("onSubscribe: " + Thread.currentThread().getName() + "\n");
				subscriber.onNext(events);
				subscriber.onCompleted();
			}
		};

		Subscriber<String> subscriber = new Subscriber<String>() {
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
				unsubscribe(); // 反注册, 防止出现内存泄漏的问题
			}

			@Override
			public void onError(Throwable e) {
				iFragRxView.addText("onError: " + Thread.currentThread().getName() + "\n\n");
				unsubscribe();
			}
		};

		Observable.create(onSubscribe)
				.subscribeOn(Schedulers.io())  // onSubscribe
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						iFragRxView.addText("doOnSubscribe: " + Thread.currentThread().getName() + "\n");
					}
				})
				.subscribeOn(Schedulers.newThread()) // doOnSubscribe
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
						return eEvent.PitPatGetCallBack;
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
				.observeOn(AndroidSchedulers.mainThread()) //subscriber
				.subscribe(subscriber);
	}

}
