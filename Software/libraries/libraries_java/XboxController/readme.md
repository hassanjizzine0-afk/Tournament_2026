
# Библиотека XboxController (Java)

## 1. Класс `XboxController` (Управление пультом)
Этот класс создается для получения сигналов с USB-пульта, подключенного к драйвер-станции. Он не связан с CAN-шиной и работает напрямую через WPILib.

### Синтаксис создания:
```java
import edu.wpi.first.wpilibj.XboxController;

XboxController m_controller = new XboxController(0); // 0 — это номер USB-порта на Driver Station
```

### Основные методы для осей (Функции стиков):
Эти методы возвращают значение типа `double` от `-1.0` до `1.0`.
*   **`getLeftY()`** — возвращает положение левого стика по вертикали (Y).
    *   *Важно:* Вверх обычно дает отрицательное значение, поэтому для движения вперед часто пишут `-m_controller.getLeftY()`.
*   **`getRightX()`** — возвращает положение правого стика по горизонтали (X). Используется для поворота.
*   **`getLeftX()`** — положение левого стика по горизонтали.
*   **`getRightY()`** — положение правого стика по вертикали.

### Основные методы для кнопок (Возвращают `boolean`):
Методы возвращают `true`, если кнопка нажата, и `false`, если отпущена.
*   **`getAButton()`** — кнопка A.
*   **`getBButton()`** — кнопка B.
*   **`getXButton()`** — кнопка X.
*   **`getYButton()`** — кнопка Y.
*   **`getLeftBumper()`** — левый бампер (LB).
*   **`getRightBumper()`** — правый бампер (RB).

### Триггеры (Курки):
*   **`getLeftTriggerAxis()`** — возвращает значение нажатия левого курка (от 0.0 до 1.0).
*   **`getRightTriggerAxis()`** — возвращает значение нажатия правого курка (от 0.0 до 1.0).

---

## 💡 Пример использования (Пульт + Моторы)

Вот как это выглядит в коде при управлении роботом:

```java
import com.studica.frc.Titan;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
    // 1. Создаем пульт
    XboxController m_controller = new XboxController(0); 
    
    // 2. Создаем контроллер моторов
    Titan m_titan = new Titan(42); 
    Titan.Motor m_leftMotor  = m_titan.getMotor(0);
    Titan.Motor m_rightMotor = m_titan.getMotor(2);

    @Override
    public void teleopPeriodic() {
        // Читаем пульт (инвертируем Y, чтобы вперед было положительное значение)
        double forward = -m_controller.getLeftY();
        double turn    = m_controller.getRightX();

        // Математика танкового привода
        double leftSpeed  = forward + turn;
        double rightSpeed = forward - turn;

        // Отправляем команды на моторы
        m_leftMotor.set(leftSpeed);
        m_rightMotor.set(rightSpeed);
        
        // Пример с кнопкой (если нажата А, то мотор на порту 1 крутится)
        if (m_controller.getAButton()) {
            m_titan.getMotor(1).set(0.5);
        }
    }
}
```
```

### 💡 Главные отличия от C++:
1.  **Импорты:** Вместо `#include <frc/XboxController.h>` пишем `import edu.wpi.first.wpilibj.XboxController;`.
2.  **Создание:** Обязательно используем `new XboxController(0)`.
3.  **Маленькие буквы:** Методы `getLeftY`, `getRightX`, `getAButton`, `set` начинаются со строчной буквы.
4.  **`@Override`:** В Java перед методом `teleopPeriodic()` принято писать `@Override`, чтобы программа точно знала, что ты переопределяешь стандартный метод WPILib.
