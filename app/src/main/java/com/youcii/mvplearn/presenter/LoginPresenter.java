package com.youcii.mvplearn.presenter;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.ObservableBody;
import com.youcii.mvplearn.activity.interfaces.ILoginView;
import com.youcii.mvplearn.base.BasePresenter;
import com.youcii.mvplearn.encap.OkGoCallBack.CallBackAdapter;
import com.youcii.mvplearn.encap.OkGoCallBack.HttpRequestBuilder;
import com.youcii.mvplearn.encap.OkGoCallBack.JsonConverter;
import com.youcii.mvplearn.response.IpQueryResponse;

import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author YouCii
 * @date 2016/7/15
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private Disposable disposable;
    private int requestTag = 0;

    public LoginPresenter(ILoginView iLoginView) {
        super(iLoginView);
    }

    public void login(String user, String password) {
        if (getView() != null) {
            getView().turnProgress(true);
        }

        rxRequest(user, password);
    }

    private void saveUser(String user, String password) {
        if (getView() != null) {
            getView().showToast("getEvent");
        }
    }

    /**
     * 使用CallBack方式请求
     * <p>
     * 注: 此处网络请求比较简单, 所以无需单独抽出
     */
    private void callBackRequest(String user, String password) {
        requestTag = new Random().nextInt();
        new HttpRequestBuilder<IpQueryResponse>()
                .getRequest("http://iploc.market.alicloudapi.com/v3/ip")
                .addHeader("Authorization", "APPCODE e791ada94bd74182aaab249e51128ad3")
                .addParams("ip", "114.247.50.2")
                .execute(new CallBackAdapter<IpQueryResponse>() {
                    @Override
                    public void onSuccess(IpQueryResponse ipQueryResponse) {
                        if (getView() != null) {
                            getView().loginSuccess();
                        }
                        saveUser(user, password);
                    }

                    @Override
                    public void onError(String errorInfo) {
                        if (getView() != null) {
                            getView().loginFail(errorInfo);
                        }
                    }
                }, requestTag);
    }

    /**
     * 使用RxJava方式请求
     */
    private void rxRequest(String user, String password) {
        Observable<IpQueryResponse> observable = OkGo.<IpQueryResponse>get("http://iploc.market.alicloudapi.com/v3/ip")
                .headers("Authorization", "APPCODE e791ada94bd74182aaab249e51128ad3")
                .params("ip", "114.247.50.2")
                .converter(new JsonConverter<>(IpQueryResponse.class))
                .adapt(new ObservableBody<>());
        disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<IpQueryResponse>() {
                    @Override
                    public boolean test(IpQueryResponse ipQueryResponse) throws Exception {
                        return ipQueryResponse != null;
                    }
                })
                .take(1)
                .subscribe(new Consumer<IpQueryResponse>() {
                    @Override
                    public void accept(IpQueryResponse ipQueryResponse) throws Exception {
                        if (getView() != null) {
                            getView().loginSuccess();
                        }
                        saveUser(user, password);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        if (getView() != null) {
                            getView().loginFail(throwable.getMessage());
                        }
                    }
                });
    }

    @Override
    public void detach() {
        if (disposable != null) {
            disposable.dispose();
        }
        if (requestTag != 0) {
            OkGo.getInstance().cancelTag(requestTag);
        }
    }
}
