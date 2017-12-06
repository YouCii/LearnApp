package com.youcii.mvplearn.presenter;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx2.adapter.ObservableBody;
import com.youcii.mvplearn.activity.interfaces.ILoginView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author YouCii
 * @date 2016/7/15
 */
public class LoginPresenter {

    private ILoginView iLoginView;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    public void login(String user, String password) {
        iLoginView.turnProgress(true);

        observableRequest(user, password);
    }

    public void saveUser(String user, String password) {
        iLoginView.showToast("getEvent");
    }

    private void observableRequest(String user, String password) {
        Observable<String> observable = OkGo.<String>post("http://www.baidu.com")
                .params("user", user)
                .params("password", password)
                .converter(new StringConvert())
                .adapt(new ObservableBody<String>());
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String string) throws Exception {
                        return !TextUtils.isEmpty(string);
                    }
                })
                .take(1)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        iLoginView.loginSuccess();
                        saveUser(s, password);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        iLoginView.loginFail();
                    }
                });
    }
}
