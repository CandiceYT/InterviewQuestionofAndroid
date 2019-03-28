[TOC]



### 1.导致内存泄漏的原因

>**根本原因:** 长生命周期的对象持有短生命周期的对象。短周期对象就无法及时释放。
>
>**场景：**
>
>- 静态内部类非静态内部类的区别(Handler 引起的内存泄漏。)
>- 静态集合类引起内存泄露
>- 单例模式引起的内存泄漏。
>- 注册/反注册未成对使用引起的内存泄漏。
>- 资源未关闭，如cursor、I/O、bitmap等。
>
>**解决：**
>
>- Context是ApplicationContext，由于ApplicationContext的生命周期是和app一致的，不会导致内存泄漏。
>
>- 集合对象没有及时清理引起的内存泄漏。通常会把一些对象装入到集合中，当不使用的时候一定要记得及时清理集合，让相关对象不再被引用。
>
>- 注册与反注册成对出现
>
>- 减少内存对象的占用
>
>	I.ArrayMap/SparseArray代替hashmap
>
>	II.避免在android里面使用Enum
>
>	III.减少bitmap的内存占用。
>
>	IV.减少资源图片的大小，过大的图片可以考虑分段加载

### 2.Activity、View、Window三者关系

>Activity像一个工匠（控制单元），Window像窗户（承载模型），View像窗花（显示视图）LayoutInflater像剪刀，Xml配置像窗花图纸。
>
>- Activity构造的时候会初始化一个Window，准确的说是PhoneWindow。
>- 这个PhoneWindow有一个“ViewRoot”，这个“ViewRoot”是一个View或者说ViewGroup，是最初始的根视图。
>- “ViewRoot”通过addView方法来一个个的添加View。比如TextView，Button等
>- 这些View的事件监听，是由WindowManagerService来接受消息，并且回调Activity函数。比如onClickListener，onKeyDown等。

### 3.View，ViewGroup事件分发

- Touch事件分发中只有两个主角:ViewGroup和View。ViewGroup包含onInterceptTouchEvent、dispatchTouchEvent、onTouchEvent三个相关事件。View包含dispatchTouchEvent、onTouchEvent两个相关事件。其中ViewGroup又继承于View。
- ViewGroup和View组成了一个树状结构，根节点为Activity内部包含的一个ViwGroup。
- 触摸事件由Action_Down、Action_Move、Aciton_UP组成，其中一次完整的触摸事件中，Down和Up都只有一个，Move有若干个，可以为0个
- 当Acitivty接收到Touch事件时，将遍历子View进行Down事件的分发。ViewGroup的遍历可以看成是递归的。分发的目的是为了找到真正要处理本次完整触摸事件的View，这个View会在onTouchuEvent结果返回true。
- 当某个子View返回true时，会中止Down事件的分发，同时在ViewGroup中记录该子View。接下去的Move和Up事件将由该子View直接进行处理。由于子View是保存在ViewGroup中的，多层ViewGroup的节点结构时，上级ViewGroup保存的会是真实处理事件的View所在的ViewGroup对象:如ViewGroup0-ViewGroup1-TextView的结构中，TextView返回了true，它将被保存在ViewGroup1中，而ViewGroup1也会返回true，被保存在ViewGroup0中。当Move和UP事件来时，会先从ViewGroup0传递至ViewGroup1，再由ViewGroup1传递至TextView。
- 当ViewGroup中所有子View都不捕获Down事件时，将触发ViewGroup自身的onTouch事件。触发的方式是调用super.dispatchTouchEvent函数，即父类View的dispatchTouchEvent方法。在所有子View都不处理的情况下，触发Acitivity的onTouchEvent方法。
- onInterceptTouchEvent有两个作用：1.拦截Down事件的分发。2.中止Up和Move事件向目标View传递，使得目标View所在的ViewGroup捕获Up和Move事件。

### 4.onNewIntent()什么时候调用?(singleTask)

​	当Activity被设以singleTop启动，当需要再次响应此Activity启动需求时，会复用栈顶的已有Activity，还会调用onNewIntent方法。

​	并且，再接受新发送来的intent(onNewIntent方法)之前，一定会先执行onPause方法。 

### 5.自定义控件

​	View的绘制流程：OnMeasure()——>OnLayout()——>OnDraw()

- OnMeasure()：测量视图大小。从顶层父View到子View递归调用measure方法，measure方法又回调OnMeasure。
- OnLayout()：确定View位置，进行页面布局。从顶层父View向子View的递归调用view.layout方法的过程，即父View根据上一步measure子View所得到的布局大小和布局参数，将子View放在合适的位置上。 
- OnDraw()：绘制视图。ViewRoot创建一个Canvas对象，然后调用OnDraw()。六个步骤：①、绘制视图的背景；②、保存画布的图层（Layer）；③、绘制View的内容；④、绘制View子视图，如果没有就不用；⑤、还原图层（Layer）；⑥、绘制滚动条。

### 6.Serializable和Parcelable的区别

- P 消耗内存小
- 网络传输用S， 程序内使用P
- S将数据持久化方便
- S使用了反射 容易触发垃圾回收比较慢，频繁GC;
- S代码量少，P序列化复杂，代码量大；

### 7.Android为什么要序列化？什么是序列化，怎么进行序列化

>**why**
>
>​	行Android开发的时候，无法将对象的引用传给Activities或者Fragments，我们需要将这些对象放到一个Intent或者Bundle里面，然后再传递。

>**what**
>
>​	序列化，表示将一个对象转换成可存储或可传输的状态。序列化后的对象可以在网络上进行传输，也可以存储到本地。

>**how**
>
>Android中Intent如果要传递类对象，可以通过两种方式实现。
>
>- 方式一：Serializable，要传递的类实现Serializable接口传递对象，
>- 方式二：Parcelable，要传递的类实现Parcelable接口传递对象。

>**Serializable（Java自带）：**
>
>Serializable是序列化的意思，表示将一个对象转换成可存储或可传输的状态。序列化后的对象可以在网络上进行传输，也可以存储到本地。

>**Parcelable（android 专用）：**
>
>Parcelable方式的实现原理是将一个完整的对象进行分解，而分解后的每一部分都是Intent所支持的数据类型，这样也就实现传递对象的功能了。

>**实现Parcelable的作用**
>
>- 永久性保存对象，保存对象的字节序列到本地文件中；
>- 通过序列化对象在网络中传递对象；
>- 通过序列化在进程间传递对象。

>**选择序列化方法的原则**
>
>- 在使用内存的时候，Parcelable比Serializable性能高，所以推荐使用Parcelable。
>- Parcelable不能使用在要将数据存储在磁盘上的情况，因为Parcelable不能很好的保证数据的持续性在外界有变化的情况下。尽管Serializable效率低点，但此时还是建议使用Serializable 。

>**应用场景**
>
>需要在多个部件(Activity或Service)之间通过Intent传递一些数据，简单类型（如：数字、字符串）的可以直接放入Intent。复杂类型必须实现Parcelable接口。

### 8.四大组件简介

>**Activity**
>
>​	用户可操作的可视化界面，为用户提供一个完成操作指令的窗口。一个Activity通常是一个单独的屏幕，Activity通过Intent来进行通信。Android中会维持一个Activity Stack，当一个新Activity创建时，它就会放到栈顶，这个Activity就处于运行状态。

>**Service**
>
>​	运行在手机后台，适合执行不需和用户交互且还需长期运行的任务。

>**BroadcastReceiver**
>
>​	运用在应用程序间传输信息，可以使用广播接收器来让应用对一个外部事件做出响应。

>**ContentProvider**
>
>​	使一个应用程序的指定数据集提供给其他应用程序，其他应用可通过ContentResolver类从该内容提供者中获取或存入数据。它提供了一种跨进程数据共享的方式，当数据被修改后，ContentResolver接口的notifyChange函数通知那些注册监控特定URI的ContentObserver对象。

### 9.四大组件的生命周期

>**Activity**
>
>​	onCreate()->onStart()->onResume()->onPause()->onStop()->onDestory()
>
>onCreate(): 为Activity设置布局，此时界面还不可见；
>
>onStart(): Activity可见但还不能与用户交互，不能获得焦点;
>
>onRestart(): 重新启动Activity时被回调;
>
>onResume(): Activity可见且可与用户进行交互;
>
>onPause(): 当前Activity暂停，不可与用户交互，但还可见。在新Activity启动前被系统调用保存现有的Activity中的持久数据、停止动画等;
>
>onStop(): 当Activity被新的Activity覆盖不可见时被系统调用
>
>onDestory(): 当Activity被系统销毁杀掉或是由于内存不足时调用

>**Service**
>
>
>
>















 

