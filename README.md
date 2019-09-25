# WorldClock
作为一位酒店大堂服务人员，我想在大堂的城市时钟不准时，用设置自己手机时间的方法，自动统一调整这些城市时钟时间，来避免逐一根据时差调整这些时钟的繁琐工作，系统特性具体包括：

REQ1: ”码农酒店”大堂里有5个时钟，分别显示北京、伦敦、莫斯科、悉尼和纽约的时间

REQ2: 伦敦与UTC时间一致，北京比UTC时间早8小时，莫斯科比UTC时间早4小时，悉尼比UTC时间早10小时，纽约比UTC时间晚5小时

REQ3: 将酒店大堂服务员的智能手机时间设置为北京时间

REQ4: 若大堂墙壁上所有城市的时钟都或多或少有些走时不准，需要调整时间时，只需调准服务员手机的时间，那么墙上5个城市的时钟时间都能够相应地自动调整准确


### 代码实现效果：

可以调用`WorldClockSystem`类中的`addClock(Clock)`方法向一个`WorldClockSystem`实例中加入`Clock/PhoneClock`实例，注意其中`PhoneClock`实例必须第一个加入。然后调用`PhoneClock`实例中的`setWorldClockSystem(WorldClockSystem)`方法就可以开始使用系统了。

可以设置不同时钟的时间和获取时钟时间，当设置`PhoneClock`时钟时，系统中的其余时钟都会根据时区关系自动校准。

### 测试用例设置：

共计设置了三个测试用例，分别是对时钟时间的设置和读取：

```java
static Clock[] clocks = new Clock[5];

    @Test
    @BeforeAll
    static void init() {
        clocks[0] = new PhoneClock("Beijing", -8);
        clocks[1] = new Clock("London", 0);
        clocks[2] = new Clock("Moscow", -4);
        clocks[3] = new Clock("Sydney", -10);
        clocks[4] = new Clock("New York", 5);
    }

    @Test
    @DisplayName("Set and get the Clock time")
    void testClockSetGet() {
        clocks[0].setLocalTime(LocalTime.of(1, 10));
        clocks[1].setLocalTime(LocalTime.of(3, 20));
        clocks[2].setLocalTime(LocalTime.of(4, 10));
        assertAll(
                ()->assertEquals("Beijing(offset:-8): 01:10", clocks[0].getLocation() + "(offset:" + clocks[0].getUTCOffSet() + "): " + clocks[0].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("London(offset:0): 03:20", clocks[1].getLocation() + "(offset:" + clocks[1].getUTCOffSet() + "): " + clocks[1].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("Moscow(offset:-4): 04:10", clocks[2].getLocation() + "(offset:" + clocks[2].getUTCOffSet() + "): " + clocks[2].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                );
    }
```

对`WorldClockSystem`实例中添加时钟，读取时钟信息的测试：

```java
static Clock[] clocks = new Clock[5];
    static WorldClockSystem worldClockSystem;


    @Test
    @BeforeAll
    static void init() {
        clocks[0] = new PhoneClock("Beijing", -8);
        clocks[1] = new Clock("London", 0);
        clocks[2] = new Clock("Moscow", -4);
        clocks[3] = new Clock("Sydney", -10);
        clocks[4] = new Clock("New York", 5);
        worldClockSystem = new WorldClockSystem();
        for(int i = 0; i < 5; i++) {
            worldClockSystem.addClock(clocks[i]);
        }
    }

    @Test
    @DisplayName("Test ClockList in Class WorldClockSystem")
    void testClockListUse() {
        clocks[0].setLocalTime(LocalTime.of(1, 10));
        clocks[1].setLocalTime(LocalTime.of(2, 20));
        clocks[2].setLocalTime(LocalTime.of(3, 30));
        Clock[] testClocks = new Clock[3];
        testClocks[0] = worldClockSystem.getClock("Beijing");
        testClocks[1] = worldClockSystem.getClock("London");
        testClocks[2] = worldClockSystem.getClock("Moscow");
        assertAll(
                ()->assertEquals("Beijing: 01:10", testClocks[0].getLocation() + ": " + testClocks[0].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("London: 02:20", testClocks[1].getLocation() + ": " + testClocks[1].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("Moscow: 03:30", testClocks[2].getLocation() + ": " + testClocks[2].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
        );
    }
```

对核心功能——`PhoneClock`校准功能的测试：

```java
@Test
    @DisplayName("Test the PhoneClock's calibration function")
    void testPhoneClockCalibration() {
        ((PhoneClock)clocks[0]).setWorldClockSystem(worldClockSystem);
        clocks[1].setLocalTime(LocalTime.of(2, 30));
        clocks[2].setLocalTime(LocalTime.of(3, 30));
        clocks[3].setLocalTime(LocalTime.of(4, 30));
        clocks[4].setLocalTime(LocalTime.of(5, 30));
        clocks[0].setLocalTime(LocalTime.of(12, 30));
        assertAll(
                ()->assertEquals("London: 04:30", clocks[1].getLocation() + ": " + clocks[1].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("Moscow: 08:30", clocks[2].getLocation() + ": " + clocks[2].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("Sydney: 14:30", clocks[3].getLocation() + ": " + clocks[3].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("New York: 23:30", clocks[4].getLocation() + ": " + clocks[4].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
        );
    }
```

