package com.youcii.mvplearn.fragment.interfaces;

import com.youcii.mvplearn.base.BaseView;

/**
 * Created by YouCii on 2016/12/17.
 */

public interface IFragRxView extends BaseView {

    void setText(String string);

    String getText();

    void addText(String string);
}
