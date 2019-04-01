1.数据库的操作类型有哪些，如何导入外部数据库？

>**使用数据库的方式有哪些？**
>
>（1）openOrCreateDatabase(String path);
>
>（2）继承SqliteOpenHelper类对数据库及其版本进行管理(onCreate,onUpgrade)
>
>​	当在程序当中调用这个类的方法getWritableDatabase()或者getReadableDatabase();的时候才会打开数据库。如果当时没有数据库文件的时候，系统就会自动生成一个数据库。
>
>**操作的类型：**增删改查CRUD
>
>直接操作SQL语句：SQliteDatabase.execSQL(sql);
>
>面向对象的操作方式：SQLiteDatabase.insert(table, nullColumnHack, ContentValues);
>
>**如何导入外部数据库？**
>
>​	一般外部数据库文件可能放在SD卡或者res/raw或者assets目录下面。
>
>​	写一个DBManager的类来管理，数据库文件搬家，先把数据库文件复制到”/data/data/包名/databases/”目录下面，然后通过db.openOrCreateDatabase(db文件),打开数据库使用。
>
>**操作方法：**
>
>​	把原数据库包括在项目源码的 res/raw 目录下，然后建立一个DBManager类，然后在程序的首个Activity中示例化一个DBManager对象，然后对其执行openDatabase方法就可以完成导入了，可以把一些要对数据库进行的操作写在DBManager类里，然后通过DBManager类的对象调用；也可以在完成导入之后通过一个SQliteDatabase类的对象打开数据库，并执行操作。

### 2.是否使用过 IntentService，作用是什么， AIDL 解决了什么问题？

>如果有一个任务，可以分成很多个子任务，需要按照顺序来完成，如果需要放到一个服务中完成，那么使用IntentService是最好的选择。
>
>一般我们所使用的Service是运行在主线程当中的，所以在service里面编写耗时的操作代码，则会卡主线程会ANR。为了解决这样的问题，谷歌引入了IntentService.
>
>IntentService的优点：
>
>- 它创建一个独立的工作线程来处理所有一个一个intent。
>- 创建了一个工作队列，来逐个发送intent给onHandleIntent()
>- 不需要主动调用stopSelf()来结束服务，因为源码里面自己实现了自动关闭。
>- 默认实现了onBind()返回的null。
>- 默认实现的onStartCommand()的目的是将intent插入到工作队列。
>
>总结：使用IntentService的好处有哪些。首先，省去了手动开线程的麻烦；第二，不用手动停止service；第三，由于设计了工作队列，可以启动多次---startService(),但是只有一个service实例和一个工作线程。一个一个顺序执行。
>
>**AIDL 解决了什么问题？**
>
>AIDL的全称：Android Interface Definition Language，安卓接口定义语言。
>
>由于Android系统中的进程之间不能共享内存，所以需要提供一些机制在不同的进程之间进行数据通信。
>
>远程过程调用：RPC—Remote Procedure Call。  安卓就是提供了一种IDL的解决方案来公开自己的服务接口。AIDL:可以理解为双方的一个协议合同。双方都要持有这份协议---文本协议 xxx.aidl文件（安卓内部编译的时候会将aidl协议翻译生成一个xxx.java文件---代理模式：Binder驱动有关的，Linux底层通讯有关的。）
>
>在系统源码里面有大量用到aidl，比如系统服务。
>
>电视机顶盒系统开发。你的服务要暴露给别的开发者来使用。

### 3.**Fragment的特点？**

>fragment的设计主要是把Activity界面包括其逻辑打碎成很多个独立的模块，这样便于模块的重用和更灵活地组装呈现多样的界面。
>
>1）  Fragment可以作为Activity界面的一个部分组成；
>
>2）  可以在一个Activity里面出现多个Fragment，并且一个fragment可以在多个Activity中使用；
>
>3）  在Activity运行中，可以动态地添加、删除、替换Fragment。
>
>4）  Fragment有自己的生命周期的，可以响应输入事件。
>
>踩过的坑：1.重叠；2. 注解newAPI（兼容包解决）；3. Setarguement()初始化数据;4. 不能在onsave...（）方法后，commit; 5. 入栈出栈问题; --事务。像Activity跳转一样的效果，同时返回的时候还能回到之前的页面(fragment)并且状态都还在。6.replace(f1,f2)严重影响生命周期:add()+show+hide

#### 4.Handler、 Thread 和 HandlerThread 的差别

>#### 1）三者的区别
>
>​	① Handler：在android中负责发送和处理消息，通过它可以实现其他支线线程与主线程之间的消息通信。
>
>​	②Thread：Java进程中执行运算的最小单位，亦即执行处理机调度的基本单位。某一进程中一路单独运行的程序。
>
>​	③HandlerThread：一个继承自Thread的类HandlerThread，Android中没有对Java中的Thread进行任何封装，而是提供了一个继承自Thread的类HandlerThread类，这个类对Java的Thread做了很多便利的封装。
>
>#### 2）**HandlerThread**
>
>①Thread. Handy class for starting a new thread that has a looper.②The looper can then be used to create handler classes.
>
>释义：HandlerThread对象start后可以获得其Looper对象，并且使用这个Looper对象实例Handler，之后Handler就可以运行在其他线程中了。
>
>#### 3）**HandlerThread机制**
>
>![](/Users/candice/Downloads/Worksoace/AndroidStudioProjects/Learn/InterviewQuestionofAndroid/app/Android/基础知识pics/Handler Thread.png)

>Andriod提供了 Handler  和  Looper  来满足线程间的通信。 Handler 先进先出原则。 Looper 类用来管理特定线程内对象之间的消息交换 (MessageExchange) 。 
>
>1)Looper:  一个线程可以产生一个 Looper 对象，由它来管理此线程里的 MessageQueue( 消息队列 ) 和对消息进行循环。 
>
>2)Handler:  你可以构造 Handler 对象来与 Looper 沟通，以便 push 新消息到 MessageQueue 里 ; 或者接收 Looper 从 Message Queue 取出 所送来的消息。 
>
>3) Message Queue( 消息队列 ): 用来存放线程放入的消息。 
>
>4) Message：是线程间通讯的消息载体。两个码头之间运输货物，Message充当集装箱的功能，里面可以存放任何你想传递的消息。
>
>看到这里就明白了为什么：如果一个线程要处理消息，那么它必须拥有自己的Looper，并不是Handler在哪里创建，就可以在哪里处理消息。
>
>注：对应关系Thread(1):Looper(1):MessageQueen(1):Handler(n).
>
>```java
>public class MainActivity extends AppCompatActivity {
> 
>    Handler mainHandler,workHandler;
>    HandlerThread mHandlerThread;
>    TextView text;
>    Button button1,button2;
> 
>    @Override
>    protected void onCreate(Bundle savedInstanceState) {
>        super.onCreate(savedInstanceState);
>        setContentView(R.layout.activity_main);
>        text = (TextView) findViewById(R.id.text1);
> 
>        // 创建与主线程关联的Handler
>        mainHandler = new Handler();
>        /**
>          * 步骤①：创建HandlerThread实例对象
>          * 传入参数 = 线程名字，作用 = 标记该线程
>          */
>        mHandlerThread = new HandlerThread("handlerThread");
> 
>        /**
>         * 步骤②：启动线程
>         */
>        mHandlerThread.start();
> 
>        /**
>         * 步骤③：创建工作线程Handler & 复写handleMessage（）
>         * 作用：关联HandlerThread的Looper对象、实现消息处理操作 & 与其他线程进行通信
>         * 注：消息处理操作（HandlerMessage（））的执行线程 = mHandlerThread所创建的工作线程中执行
>         */
> 
>        workHandler = new Handler(mHandlerThread.getLooper()){
>            @Override
>            public void handleMessage(Message msg)
>            {
>                //设置了两种消息处理操作,通过msg来进行识别
>                switch(msg.what){
>                    case 1:
>                        try {
>                            //延时操作
>                            Thread.sleep(1000);
>                        } catch (InterruptedException e) {
>                            e.printStackTrace();
>                        }
>                        // 通过主线程Handler.post方法进行在主线程的UI更新操作
>                        mainHandler.post(new Runnable() {
>                            @Override
>                            public void run () {
>                                text.setText("第一次执行");
>                            }
>                        });
>                        break;
>                    case 2:
>                        try {
>                            Thread.sleep(3000);
>                        } catch (InterruptedException e) {
>                            e.printStackTrace();
>                        }
>                        mainHandler.post(new Runnable() {
>                            @Override
>                            public void run () {
>                                text.setText("第二次执行");
>                            }
>                        });
>                        break;
>                    default:
>                        break;
>                }
>            }
>        };
> 
>        /**
>         * 步骤④：使用工作线程Handler向工作线程的消息队列发送消息
>         * 在工作线程中，当消息循环时取出对应消息 & 在工作线程执行相关操作
>         */
>        button1 = (Button) findViewById(R.id.button1);
>        button1.setOnClickListener(new View.OnClickListener() {
>            @Override
>            public void onClick(View v) {
>                Message msg = Message.obtain();
>                msg.what = 1; //消息的标识
>                msg.obj = "A"; // 消息的存放
>                // 通过Handler发送消息到其绑定的消息队列
>                workHandler.sendMessage(msg);
>            }
>        });
> 
>        button2 = (Button) findViewById(R.id.button2);
>        button2.setOnClickListener(new View.OnClickListener() {
>            @Override
>            public void onClick(View v) {
>                Message msg = Message.obtain();
>                msg.what = 2; 
>                msg.obj = "B"; 
>                workHandler.sendMessage(msg);
>            }
>        });
> 
>    }
>    
>    @Override
>    protected void onDestroy() {
>        super.onDestroy();
>        mHandlerThread.quit(); // 退出消息循环
>        workHandler.removeCallbacks(null); // 防止Handler内存泄露 清空消息队列
>    }
>}
>```
>
>​	从上面代码可以看出，HandlerThread继承于Thread，所以它本质就是个Thread。与普通Thread的差别就在于，然后在内部直接实现了Looper的实现，这是Handler消息机制必不可少的。有了自己的looper,可以让我们在自己的线程中分发和处理消息。如果不用HandlerThread的话，需要手动去调用Looper.prepare()和Looper.loop()这些方法。
>
>```java
>// 子线程中创建新的Handler 没有使用HandlerThread
>new Thread () {
>    @Override
>    public void run() {
>        Looper.prepare();
>        Hnadler handler = new Handler();
>        Looper.loop();
>    } 
>}
>
>```
>
>![](/Users/candice/Downloads/Worksoace/AndroidStudioProjects/Learn/InterviewQuestionofAndroid/app/Android/基础知识pics/消息机制.png)

>①Handler是Android消息机制的上层接口，通过它可以轻松地将一个任务切换到Handler所在的线程中去执行，该线程既可以是主线程，也可以是子线程，要看构造Handler时使用的构造方法中传入的Looper位于哪里；
>
>②Handler的运行需要底层的MessageQueue和Looper的支撑，Handler创建的时候会采用当前线程的Looper来构造消息循环系统，而线程默认是没有Looper的，如果需要使用Handler就必须为线程创建Looper；
>
>③上述代码中的第一个Handler-mainHandler，实例化的时候，直接在onCreate()方法中new出了实例，其实是其已经在主线程中了，主线程-ActivityThread，ActivityThread被创建时就会初始化Looper，这就是主线程中默认可以直接使用Handler的原因；
>
>④上述代码中的第二个Handler-workHandler，它在实例化的时候，参数传入了 mHandlerThread.getLooper() ，注意，这个Handler使用的就不是主线程的Looper了，而是子线程的Looper，HandlerThread在调用start()方法之后，就可以获取到子线程的Looper，然后将其传入workHandler的构造方法中，那么此时的workHandler就会运行在子线程中，用于处理耗时操作。
>
>⑤Handler的工作原理：Handler创建时会采用当前线程的Looper来构建内部消息循环系统，如果当前线程没有Looper，那么就会报错“Can`t create handler inside thread that has not called Looper.prepare()”解决方法有两个：为当前线程创建Looper即可，像上述代码中workHandler，或者在一个有Looper的线程中创建Handler也行，就像上述代码中的mainHandler一样；
>
>⑥调用Handler的post方法会将一个Runnable投递到Handler内部的Looper中去处理，也可以通过Handler的send方法来发送一个消息，这个消息同样会在Looper中去处理。其实post方法最终也是通过send方法来完成的。每当Looper发现有新消息到来时，就会处理这个消息，最终消息中的Runnable的run方法或者Handler的handleMessage方法就会被调用。注意Looper是运行在创建Handler所在的线程中的，这样一来Handler中的业务逻辑就被切换到创建Handler所在的线程中去执行了；
>
>⑦Looper的工作原理：Looper在Android的消息机制中扮演着消息循环的角色，具体来说就是它会不停地从MessageQueue中查看是否有新消息，如果有新消息就会立刻处理，否则就一直阻塞在那里。注意关注一些重要的Looper的方法：
>
>Looper.prepare()-为当前线程创建一个Looper；
>Looper.loop()-开启消息循环，只有调用该方法，消息循环系统才会开始循环；
>Looper.prepareMainLooper()-为主线程也就是ActivityThread创建Looper使用；
>Looper.getMainLooper()-通过该方法可以在任意地方获取到主线程的Looper；
>Looper.quit() Looper.quitSafely()-退出Looper，自主创建的Looper建议在不使用的时候退出
>
>⑧ActivityThread主线程通过ApplicationThread和AMS进行进程间通信

### 4.低版本 SDK 实现高版本 api

>1）  一般很多高版本的新的API都会在兼容包里面找到替代的实现。比如fragment。
>
>Notification，在v4兼容包里面有NotificationCompat类。5.0+出现的backgroundTint，minSdk小于5.0的话会包检测错误，v4兼容包DrawableCompat类。
>
>2）  没有替代实现就自己手动实现。比如：控件的水波纹效果—第三方实现。或者直接在低版本去除这个效果。
>
>3）  补充:如果设置了minSDK但是代码里面使用了高版本的API，会出现检测错误。需要在代码里面使用声明编译检测策略，比如：@SuppressLint和@TargetApi注解提示编译器编译的规则。@SuppressLint是忽略检测；@TargetApi=23，会根据你函数里面使用的API，严格地匹配SDK版本，给出相应的编译错误提示。
>
>4）  为了避免位置的错误，最好不要使用废弃api。（一般情况下不会有兼容性问题，后面可能会随时删除这个API方法；性能方面的问题。）

### 5. ANR 定位和修正

>可以通过查看/data/anr/traces.txt查看ANR信息。
>
>根本原因是：主线程被卡了，导致应用在5秒时间未响应用户的输入事件。
>
>很多种ANR错误出现的场景：
>
>1）  主线程当中执行IO/网络操作，容易阻塞。
>
>2）  主线程当中执行了耗时的计算。----自定义控件的时候onDraw方法里面经常这么做。
>
>（同时聊一聊自定义控件的性能优化：在onDraw里面创建对象容易导致内存抖动---绘制动作会大量不断调用，产生大量垃圾对象导致GC很频繁就造成了内存抖动。）内存抖动就容易造成UI出现掉帧卡顿的问题
>
>3）  BroadCastReceiver没有在10秒内完成处理。
>
>4）  BroadCastReceiver的onReceived代码中也要尽量减少耗时的操作，建议使用IntentService处理。
>
>5）  Service执行了耗时的操作，因为service也是在主线程当中执行的，所以耗时操作应该在service里面开启子线程来做。
>
>6）  使用AsyncTask处理耗时的IO等操作。
>
>7）  使用Thread或者HandlerThread时，使用Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)或者java.lang.Thread.setPriority （int priority）设置优先级为后台优先级，这样可以让其他的多线程并发消耗CPU的时间会减少，有利于主线程的处理。
>
>8）  Activity的onCreate和onResume回调中尽量耗时的操作。

### 6.什么情况导致 oom

>​	OOM产生的原因：内存不足，android系统为每一个应用程序都设置了一个硬性的条件:DalvikHeapSize最大阀值64M/48M/24M.如果你的应用程序内存占用接近这个阀值，此时如果再尝试内存分配的时候就会造成OOM。
>
>1)内存泄露多了就容易导致OOM
>
>2)大图的处理。压缩图片。平时开发就要注意对象的频繁创建和回收。
>
>3）可以适当的检测：ActivityManager.getMemoryClass()可以用来查询当前应用的HeapSize阀值。可以通过命名adb shellgetProp | grep dalvik.vm.heapxxxlimit查看。
>
>如何避免内存泄露：
>
>1）  减小对象的内存占用：
>
>- 使用更加轻量级的数据结构：
>
>	考虑适当的情况下替代HashMap等传统数据结构而使用安卓专门为手机研发的数据结构类ArrayMap/SparseArray。SparseLongMap/SparseIntMap/SparseBoolMap更加高效。
>
>	HashMap.put(string,Object);Object o = map.get(string);会导致一些没必要的自动装箱和拆箱。
>
>- 适当的避免在android中使用Enum枚举，替代使用普通的static常量。（一般还是提倡多用枚举---软件的架构设计方面；如果碰到这个枚举需要大量使用的时候就应该更加倾向于解决性能问题。）
>
>- 较少Bitmap对象的内存占用。
>
>	使用inSampleSize:计算图片压缩比例进行图片压缩，可以避免大图加载造成OOM; decodeformat：图片的解码格式选择，ARGB_8888/RGB_565/ARGB_4444/ALPHA_8,还可以使用WebP。
>
>- 使用更小的图片
>
>	资源图片里面，是否存在还可以继续压缩的空间。
>
>2）  内存对象的重复利用：
>
>​	使用对象池技术，两种：1.自己写；2.利用系统既有的对象池机制。比如LRU(Last Recently Use)算法。
>
>- ListView/GridView源码可以看到重用的情况ConvertView的复用。RecyclerView中Recycler源码。
>
>- Bitmap的复用
>
>	Listview等要显示大量图片。需要使用LRU缓存机制来复用图片。
>
>- 避免在onDraw方法里面执行对象的创建，要复用。避免内存抖动。
>
>- 常见的java基础问题---StringBuilder等
>
>3）  避免对象的内存泄露：
>
>4）  使用一些内存的优化策略：

### 7.Android 各个版本 API 的区别

>- **5.0**
>
>	全新Android l系统从图片上就能看到一些全新的功能。从图片上看，这套概念设计图对Android系统的桌面图标及部件的透明度进行的稍稍的调整，并且各种桌面小部件也可以重叠摆放。虽然调整桌面部件透明度对Android系统来说并不算什么新鲜的功能，但是加入了透明度的改进，除了整体的色调更加清新之外。 
>	1、谷歌将为Android的语音服务Google Now加入一个名为OK Google Everywhere的全新功能。 
>	2、Android 5.0可能还会加入更多的健身功能，考虑到谷歌在发布了Android Wear，后者与智能手表及谷歌眼镜等可穿戴设备的协作应该会成为下个版本的重点功能。 
>	3、整合碎片化 
>	4、传言Google将在Android5.0中，禁止厂商进行深度定制。 
>	5、数据迁移 
>	6、独立平板 
>	7、功能按键 
>
>	8、接口风格
>
>- **6.0**
>
>	Android 6.0正式版终于随着Nexus 5X/6P一起登场了，并在北京时间10月6日面向Nexus系列产品推送升级 
>	1.App Permissions（软件权限管理）。 
>	2.Chrome Custom Tabs（网页体验提升）。 
>	3.App Links（APP关联）。 
>	4.Android Pay（安卓支付）。 
>	5.Fingerprint Support（指纹支持）。 
>
>	6.Power & Change（电量管理 ）。
>
>- **7.0**
>
>	(1)多窗口支持,可以指定应用允许的最小尺寸.同时打开两个应用,并且在多窗口模式中,增加了拖拽功能,对于开发者,可以设置
>	Activity允许的最小尺寸,分屏模式(屏幕一分为二)、画中画模式(TV上应用,视频播放窗口一直在最顶层显示)、Freeform模式(应用界面可以自由拖动或者修改大小)
>	(2)增加了JIT编译器,并对ART进行代码分析,使得安装提速并且所占空间减少.
>	(3)对通知进行了许多的增强,消息传递可以自定义,开发者只需要用到MessagingStyle进行配置即可.
>	(4)低耗电模式
>	(5)Android N 引入一项新的应用签名方案 APK Signature Scheme v2,它能提供更快的应用安装时间和更多针对未授权 APK 文件更改的保护.
>
>	注意:多窗口不影响和改变原先Activity的生命周期,因为在多窗口模式,多个Activity可以同时可见,但只有一个Activity是最顶层的,即:获取焦点的Activity.
>	所有其他Activity都会处于Paused状态(尽管它们是可见的)
>
>	1. http://blog.csdn.net/a279822581/article/details/52445154 Android 7.0分屏原理及生命周期
>	2. http://blog.csdn.net/qidabing/article/details/73548911 Android 7.0 分屏原理分析
>
>- **8.0**
>
>(1)Android O中大部分的界面改变都在设置菜单中,整体更加简洁
>(2)自适应图标,即:桌面图标都是相同的形状
>(3)后台进程,严格限制了后台进程对手机资源的调用.
>(4)取消了大部分静态广播注册
>
>- **9.0**
>
>(1)加入了全新的"自适应"电池功能,可以让手机智能判断用户对App的使用情况,并且还可以智能调节CPU的使用,最大限度地降低耗电量(加入人工智能)
>(2)重新设计系统界面,重绘系统图标,在屏幕底部增加了一个短横符号,其作用相当于原来的Home键.
>(3)提供了人工智能的API,整合形成"MLKit".
>
>(4)手机支持翻转手机进入免打扰模式.

### 8.如何保证一个后台服务不被杀死,比较省电的方式是什么？

>​	由于各种原因，在开发Android应用时会提出保证自己有一个后台一直运行的需求，如何保证后台始终运行，不被系统因为内存低杀死，不被任务管理器杀死，不被软件管家等软件杀死等等还是一个比较困难的问题。网上也有各种方案，笔者经过自己试验学习尝试总结了3中还可以的方式，记录如下。并不是绝对保证，不过确实提高了存活率不少。
>
>**方式一：service绑定通知栏成为前台服务**
>
>android中实现后台一般通过service方式，但系统本身会在内存低等情况下杀死service。通过将service绑定到notification，就成为了一个用户可见的前台服务，这样可以大大提高存活率。
>
>具体实现方式为在service中创建一个notification，再调用void android.app.[Service](eclipse-javadoc:%E2%98%82=VideoChat_phone_new/E:%5C/adt-bundle-windows-x86_64-20140702%5C/sdk%5C/platforms%5C/android-21%5C/android.jar%3Candroid.app(Service.class%E2%98%83Service).startForeground(int id, [Notification](eclipse-javadoc:%E2%98%82=VideoChat_phone_new/E:%5C/adt-bundle-windows-x86_64-20140702%5C/sdk%5C/platforms%5C/android-21%5C/android.jar%3Candroid.app(Service.class%E2%98%83Service~startForeground~I~Landroid.app.Notification;%E2%98%82Notification) notification)方法运行在前台即可。
>
> 该方式基本可以保证在正常运行情况下，以及任务栏移除历史任务后（小米、魅族手机除外），service不被杀死。但是360等软件管家依然可以杀死。
>
>**方式二：AlarmManager不断启动service**
>
>该方式原理是通过定时警报来不断启动service，这样就算service被杀死，也能再启动。同时也可以监听网络切换、开锁屏等广播来启动service。
>
>参考实现方式如下：
>
>Intent intent =new Intent(mContext, MyService.class);
>PendingIntent sender=PendingIntent
>.getService(mContext, 0, intent, 0);
>AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
>alarm.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),5*1000,sender);
>
>该方式基本可以保证在正常运行情况下，以及任务栏移除历史任务后（小米、魅族手机除外），service不被杀死。但是360等软件管家依然可以杀死。另外还有不断启动的逻辑处理麻烦。
>
>**方式三：通过jni调用，在c层启动多进程**
>
>该方式主要通过底层启动另外一个进程来实现。笔者猜测系统和三方软件管家杀死应用进程是通过包名相关线程等来遍历关闭。因此在c语言里启动另一个进程可以躲过杀死的命运。
>
>该方式思路是应用通过jni调用c，再c语言中启动一个进程fork()。
>
>该方式基本可以保证在正常运行情况下，以及任务栏移除历史任务后（小米、魅族手机除外），service不被杀死。360等软件管家也不会清理。但是带来了jni交互，稍微有点麻烦。