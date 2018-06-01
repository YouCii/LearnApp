# LearnApp
小程序, 主要包括自己学习的新技术/测试用界面等.

应用内用到的东西:
01. 语言: java + kotlin
02. 架构: MVP
03. 用到的 MaterialDesign 控件: CardView, FloatingActionButton, ToolBar和CollapsingToolbarLayout, TextInputLayout, AutoCompleteTextView
04. Android原生和JS的互相调用:jsInterface方式和jsBridge方式
05. Socket长连接, 可与网络调试助手通信
06. Gesture手势实现屏幕外部左滑返回
07. aar导包
08. CSS/的学习注释JS
09. 线程池
10. Observer/Observable
11. 使用layout方式自定义控件, 实现带有指示器可循环滚动的菜单页
12. IntDef注解的基本使用, 用于替代Enum(但实际上不如Enum方便, DisposableHelper是Enum类)
13. 沉浸式效果
14. RxJava2
15. 两个主流网络请求: OkGo2/Retrofit2, 模拟生产环境封装, 隔离业务和第三方库
    1. OkGo + CallBack
    2. Retrofit + RxJava
16. Glide
17. GreenDao, 封装自定义数据类型的转换器(当前GreenDao版本暂未支持RxJava2)
18. MVP的内存泄漏问题, RxJava的取消订阅方式
19. Synchronized/wait/notifyAll, ReentrantLock, ReentrantReadWriteLock
    附 公平锁和非公平锁的实现:
        1. 简化版的步骤：（非公平锁的核心）
            基于CAS尝试将state（锁数量）从0设置为1
            A、如果设置成功，设置当前线程为独占锁的线程；
            B、如果设置失败，还会再
            获取一次锁数量，
            B1、如果锁数量为0，再
                基于CAS尝试将state（锁数量）从0设置为1一次，如果设置成功，设置当前线程为独占锁的线程；
            B2、如果锁数量不为0或者上边的尝试又失败了，
                查看当前线程是不是已经是独占锁的线程了，如果是，则将当前的锁数量+1；如果不是，则将该线程封装在一个Node内，并加入到等待队列中去。等待被其前一个线程节点唤醒。
        2. 简化版的步骤：（公平锁的核心）
            获取一次锁数量，
            B1、如果锁数量为0，如果当前线程是等待队列中的头节点，
                基于CAS尝试将state（锁数量）从0设置为1一次，如果设置成功，设置当前线程为独占锁的线程；
            B2、如果锁数量不为0或者当前线程不是等待队列中的头节点或者上边的尝试又失败了，
                查看当前线程是不是已经是独占锁的线程了，如果是，则将当前的锁数量+1；如果不是，则将该线程封装在一个Node内，并加入到等待队列中去。等待被其前一个线程节点唤醒。
