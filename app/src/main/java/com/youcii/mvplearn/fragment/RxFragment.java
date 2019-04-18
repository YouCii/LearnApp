package com.youcii.mvplearn.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.base.BasePresenterFragment;
import com.youcii.mvplearn.fragment.interfaces.IFragRxView;
import com.youcii.mvplearn.model.EasyEvent;
import com.youcii.mvplearn.presenter.FragRxPresenter;

import org.jetbrains.annotations.NotNull;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YouCii on 2016/12/17.
 */
public class RxFragment extends BasePresenterFragment<IFragRxView, FragRxPresenter> implements IFragRxView {

    // 优先加载父类
    private static int k = j;
    private int l = i;
    private int m = j;

    @Bind(R.id.rx_text)
    TextView rxText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rx, container, false);
        ButterKnife.bind(this, view);

        rxText.setMovementMethod(ScrollingMovementMethod.getInstance());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        EasyEvent[] events = new EasyEvent[]{new EasyEvent(), new EasyEvent()};
        presenter.rxTest(events);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @NotNull
    @Override
    public FragRxPresenter initPresenter() {
        return new FragRxPresenter(this);
    }

    @Override
    public void setText(String string) {
        rxText.setText(string);
    }

    @Override
    public String getText() {
        return rxText.getText().toString();
    }

    @Override
    public void addText(String string) {
        rxText.append(string);
    }
}
