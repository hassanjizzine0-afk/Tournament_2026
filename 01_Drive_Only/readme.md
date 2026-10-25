
# Как запустить робота с нуля (VMX + Titan + 4 мотора с энкодерами)

## 1. Что у нас есть (Железо)
- **VMX** — «мозг» (контроллер).
- **Titan** — «мышцы» (контроллер моторов).
- **4 мотора** с энкодерами (провода A, B, VCC, GND).
- **Пульт** (связь с VMX по Wi-Fi).

## 2. Как это соединить (Провода)
1. **Питание:** От аккумулятора → в Titan (Main Power Input).
2. **Питание VMX:** От Titan (Power Output) → в VMX (Power In). Так VMX включается вместе с Titan.
3. **CAN-шина (связь VMX ↔ Titan):**
   - Зеленый провод (L) → в порт **L** на Titan и в порт **L** на VMX.
   - Желтый провод (H) → в порт **H** на Titan и в порт **H** на VMX.
4. **Моторы:**
   - Моторы подключаются в порты **M0, M1, M2, M3** на Titan.
   - Энкодеры этих моторов подключаются в соответствующие порты **Encoder** на Titan.
   - *Пример из документации:* Левый мотор в M0, правый в M2 .

## 3. Что писать в программе (C++)
Мы используем **C++** и библиотеку **WPILib** (специальная версия для VMX).

### Шаг 1: Подключаем библиотеку
```cpp
#include "studica/Titan.h"
```

### Шаг 2: Создаем объекты (в конструкторе класса)
```cpp
// Создаем Titan. 42 — это его CAN ID по умолчанию.
studica::Titan m_titan{42};

// Получаем доступ к моторам (0-3)
studica::Titan::Motor& m_leftMotor  = m_titan.GetMotor(0);
studica::Titan::Motor& m_rightMotor = m_titan.GetMotor(2);

// Получаем доступ к энкодерам (0-3) и задаем расстояние на один «тик»
// (Значение kDistPerTick рассчитывается отдельно, но для старта можно взять из примеров)
studica::Titan::Encoder& m_leftEncoder  = m_titan.GetEncoder(0, kDistPerTick);
studica::Titan::Encoder& m_rightEncoder = m_titan.GetEncoder(2, kDistPerTick);
```

### Шаг 3: Управляем моторами (в методе TeleopPeriodic)
```cpp
// Читаем джойстик (пример)
double forward = controller.GetLeftY();
double turn = controller.GetRightX();

// Отправляем мощность на моторы
m_leftMotor.Set(forward + turn);
m_rightMotor.Set(forward - turn);
```

### Шаг 4: Читаем энкодеры (для одометрии или балансировки)
```cpp
// Получаем пройденное расстояние в метрах (если правильно настроен kDistPerTick)
double leftDist = m_leftEncoder.GetDistance();
double rightDist = m_rightEncoder.GetDistance();
```

## 4. Главный совет
В папке `Software/Project_Code` у тебя должен лежать **готовый Example Project от Studica**. Не пиши всё с нуля. Открой пример, посмотри, как там объявлены моторы и энкодеры для Titan, и просто адаптируй под свои номера портов.
```

### 💡 Почему именно C++ и как это упрощает жизнь
Если бы ты выбрал Python через ROS2, тебе пришлось бы дополнительно разбираться с нодами, топиками и сервисами, что для новичка на турнире — лишняя головная боль. В C++ с WPILib ты работаешь напрямую с объектами: создал `Titan`, получил `Motor`, вызвал `Set()`. Всё максимально прозрачно и близко к «железу».

