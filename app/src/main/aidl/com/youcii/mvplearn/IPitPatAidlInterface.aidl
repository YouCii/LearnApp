// IPitPatAidlInterface.aidl
package com.youcii.mvplearn;

import com.youcii.mvplearn.model.ServiceData;
import com.youcii.mvplearn.ISocketStateListener;

interface IPitPatAidlInterface {

    void sendMessage(in ServiceData serviceData);

    void onDestroy();

    void setSocketStateListener(in ISocketStateListener iSocketStateListener);
}
