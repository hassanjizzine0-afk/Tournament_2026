
# Библиотека com.studica.frc.Titan (Java)

## 1. Класс `Titan` (Главный контроллер моторов)

Этот класс создается первым. Он представляет собой физический контроллер Titan на CAN-шине.

### Синтаксис создания:
```java
import com.studica.frc.Titan;

// 42 — это CAN ID по умолчанию
Titan m_titan = new Titan(42); 
```

### Основные методы (Functions):
*   **`getMotor(int port)`** — возвращает объект мотора (0, 1, 2 или 3) .
    *   *Синтаксис:* `Titan.Motor m_leftMotor = m_titan.getMotor(0);`
*   **`getEncoder(int port, double distPerTick)`** — возвращает объект энкодера .
    *   *Синтаксис:* `Titan.Encoder m_leftEncoder = m_titan.getEncoder(0, kDistPerTick);`

---

## 2. Класс `Titan.Motor` (Управление двигателем)

Этот класс создается через `getMotor()`. Он нужен для подачи напряжения на моторы.

### Синтаксис создания (через Titan):
```java
Titan.Motor m_motor = m_titan.getMotor(0);
```

### Главные методы (Functions):
*   **`set(double speed)`** — задает скорость от `-1.0` до `1.0` (где `0.0` — остановка).
    *   *Синтаксис:* `m_motor.set(0.5);` (50% мощности вперед).
*   **`stopMotor()`** — останавливает мотор.
*   **`get()`** — возвращает текущую скорость.

---

## 3. Класс `Titan.Encoder` (Чтение показаний энкодера)

Этот класс создается через `getEncoder()`. Он нужен для получения данных о том, сколько проехало колесо.

### Синтаксис создания (через Titan):
```java
Titan.Encoder m_encoder = m_titan.getEncoder(0, kDistPerTick);
```

### Главные методы (Functions):
*   **`getDistance()`** — возвращает пройденное расстояние (в метрах или тиках, в зависимости от `kDistPerTick`) .
    *   *Синтаксис:* `double dist = m_encoder.getDistance();`
*   **`getRaw()`** — возвращает «сырое» количество импульсов с энкодера.
*   **`getSpeed()`** — возвращает текущую скорость.
*   **`reset()`** — сбрасывает счетчик энкодера обратно в `0`.

---

## 💡 Пример использования (Цепочка)

Вот как всё это выглядит в коде вместе (в Java):

```java
import com.studica.frc.Titan;

public class Robot {
    // 1. Создаем контроллер
    Titan m_titan = new Titan(42); 

    // 2. Создаем мотор (порт 0)
    Titan.Motor m_leftMotor = m_titan.getMotor(0);

    // 3. Создаем энкодер (порт 0)
    Titan.Encoder m_leftEncoder = m_titan.getEncoder(0, 1.0); 

    public void teleopPeriodic() {
        // Управляем мотором
        m_leftMotor.set(0.5); 
        
        // Читаем расстояние
        double distance = m_leftEncoder.getDistance(); 
    }
}
```

### 💡 Главные отличия Java от C++:
1.  **Нет `::`**: Вместо `studica::Titan::Motor` пишем `Titan.Motor`.
2.  **Нет `&`**: В Java не нужно ставить амперсанд.
3.  **`new`**: Чтобы создать объект, обязательно пишем `new Titan(42);`.
4.  **Маленькие буквы**: Методы `getMotor`, `set`, `getEncoder` начинаются с маленькой буквы.
```
