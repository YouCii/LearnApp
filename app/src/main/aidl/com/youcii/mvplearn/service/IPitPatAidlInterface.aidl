// IPitPatAidlInterface.aidl
package com.youcii.mvplearn.service;

import com.youcii.mvplearn.model.ServiceData;
import com.youcii.mvplearn.service.ISocketStateListener;

interface IPitPatAidlInterface {

    void sendMessage(in ServiceData serviceData);

    void onDestroy();

    void setSocketStateListener(in ISocketStateListener iSocketStateListener);
}
