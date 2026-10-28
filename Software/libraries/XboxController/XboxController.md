# Библиотека frc/XboxController.h (C++)

## 1. Класс `XboxController` (Управление пультом)
Этот класс создается для получения сигналов с USB-пульта, подключенного к драйвер-станции. Он не связан с CAN-шиной и работает напрямую через WPILib.

### Синтаксис создания:
```cpp
frc::XboxController m_controller{0}; // 0 — это номер USB-порта на Driver Station
```

### Основные методы для осей (Функции стиков):
Эти методы возвращают значение типа `double` от `-1.0` до `1.0`.
*   **`GetLeftY()`** — возвращает положение левого стика по вертикали (Y).
    *   *Важно:* Вверх обычно дает отрицательное значение, поэтому для движения вперед часто пишут `-m_controller.GetLeftY()`.
*   **`GetRightX()`** — возвращает положение правого стика по горизонтали (X). Используется для поворота.
*   **`GetLeftX()`** — положение левого стика по горизонтали.
*   **`GetRightY()`** — положение правого стика по вертикали.

### Основные методы для кнопок (Возвращают `bool`):
Методы возвращают `true`, если кнопка нажата, и `false`, если отпущена.
*   **`GetAButton()`** — кнопка A.
*   **`GetBButton()`** — кнопка B.
*   **`GetXButton()`** — кнопка X.
*   **`GetYButton()`** — кнопка Y.
*   **`GetLeftBumper()`** — левый бампер (LB).
*   **`GetRightBumper()`** — правый бампер (RB).

### Триггеры (Курки):
*   **`GetLeftTriggerAxis()`** — возвращает значение нажатия левого курка (от 0.0 до 1.0).
*   **`GetRightTriggerAxis()`** — возвращает значение нажатия правого курка (от 0.0 до 1.0).

---

## 💡 Пример использования (Пульт + Моторы)

Вот как это выглядит в коде при управлении роботом:

```cpp
#include <frc/TimedRobot.h>
#include <frc/XboxController.h>
#include "studica/Titan.h"

class Robot : public frc::TimedRobot {
public:
    // 1. Создаем пульт
    frc::XboxController m_controller{0}; 
    
    // 2. Создаем контроллер моторов
    studica::Titan m_titan{42}; 
    studica::Titan::Motor& m_leftMotor  = m_titan.GetMotor(0);
    studica::Titan::Motor& m_rightMotor = m_titan.GetMotor(2);

    void TeleopPeriodic() {
        // Читаем пульт (инвертируем Y, чтобы вперед было положительное значение)
        double forward = -m_controller.GetLeftY();
        double turn    = m_controller.GetRightX();

        // Математика танкового привода
        double leftSpeed  = forward + turn;
        double rightSpeed = forward - turn;

        // Отправляем команды на моторы
        m_leftMotor.Set(leftSpeed);
        m_rightMotor.Set(rightSpeed);
        
        // Пример с кнопкой (если нажата А, то мотор на порту 1 крутится)
        if (m_controller.GetAButton()) {
            m_titan.GetMotor(1).Set(0.5);
        }
    }
};
```
```
