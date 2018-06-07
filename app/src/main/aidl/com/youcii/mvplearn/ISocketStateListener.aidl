// ISocketStateListener.aidl
package com.youcii.mvplearn;

// 默认生成的模板类的对象只支持为 in 的定向 tag

interface ISocketStateListener {

    void onBreak();

    void onConnect();

    void onReceive(in String string);

    void onSend(in String string);

}
