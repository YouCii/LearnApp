# LearnApp
小程序, 主要包括自己学习的新技术/测试用界面等.

应用内用到的东西:
- 语言: java + kotlin
- 架构: MVP
- 用到的 MaterialDesign 控件: CardView, FloatingActionButton, ToolBar和CollapsingToolbarLayout, TextInputLayout, AutoCompleteTextView
- Android原生和JS的互相调用:jsInterface方式和jsBridge方式
- Socket长连接, 可与网络调试助手通信
- 百分比布局和ConstraintLayout;
- aar导包
- CSS/的学习注释JS
- 线程池
- Observer/Observable
- 使用layout方式自定义控件, 实现带有指示器可循环滚动的菜单页
- IntDef注解的基本使用, 用于替代Enum(但实际上不如Enum方便, DisposableHelper是Enum类)
- 沉浸式效果
- RxJava2
- 两个主流网络请求: OkGo2/Retrofit2, 模拟生产环境封装, 隔离业务和第三方库
    - OkGo + CallBack
    - Retrofit + RxJava
- Glide
- GreenDao, 封装自定义数据类型的转换器(当前GreenDao版本暂未支持RxJava2)
- MVP的内存泄漏问题, RxJava的取消订阅方式
- Synchronized/wait/notifyAll, ReentrantLock, ReentrantReadWriteLock
- 梳理了普通Service, 使用AIDL创建跨进程Service的情景
- 几个特殊UI样式:
    - 多样式多spanSize的RecyclerView
    - 带有下载动画按钮
    - 露边动画ViewPager
