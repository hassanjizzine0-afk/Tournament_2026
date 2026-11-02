

# Библиотека studica/Titan.h (C++)

## 1. Класс `Titan` (Главный контроллер моторов)

Этот класс создается первым. Он представляет собой физический контроллер Titan на CAN-шине.

### Синтаксис создания:
```cpp
studica::Titan m_titan{42}; // 42 — это CAN ID по умолчанию
```

### Основные методы (Functions):
*   **`GetMotor(int port)`** — возвращает объект мотора (0, 1, 2 или 3).
    *   *Синтаксис:* `studica::Titan::Motor& m_leftMotor = m_titan.GetMotor(0);`
*   **`GetEncoder(int port, double distPerTick)`** — возвращает объект энкодера.
    *   *Синтаксис:* `studica::Titan::Encoder& m_leftEncoder = m_titan.GetEncoder(0, kDistPerTick);`

---

## 2. Класс `Titan::Motor` (Управление двигателем)

Этот класс создается через `GetMotor()`. Он нужен для подачи напряжения на моторы.

### Синтаксис создания (через Titan):
```cpp
studica::Titan::Motor& m_motor = m_titan.GetMotor(0);
```

### Главные методы (Functions):
*   **`Set(double speed)`** — задает скорость от `-1.0` до `1.0` (где `0.0` — остановка).
    *   *Синтаксис:* `m_motor.Set(0.5);` (50% мощности вперед).
*   **`SetTargetVelocity(int rpm)`** — плавно разгоняет мотор до заданных оборотов (RPM).
    *   *Синтаксис:* `m_motor.SetTargetVelocity(80);`
*   **`Stop()`** — останавливает мотор (эквивалентно `Set(0.0)`).
*   **`SetCurrentLimit(double amps)`** — ограничивает ток (например, чтобы не сжечь мотор).
*   **`GetRPM()`** — возвращает текущие обороты мотора.

---

## 3. Класс `Titan::Encoder` (Чтение показаний энкодера)

Этот класс создается через `GetEncoder()`. Он нужен для получения данных о том, сколько проехало колесо.

### Синтаксис создания (через Titan):
```cpp
studica::Titan::Encoder& m_encoder = m_titan.GetEncoder(0, kDistPerTick);
```

### Главные методы (Functions):
*   **`GetDistance()`** — возвращает пройденное расстояние (в метрах или тиках, в зависимости от `kDistPerTick`).
    *   *Синтаксис:* `double dist = m_encoder.GetDistance();`
*   **`GetRaw()`** — возвращает «сырое» количество импульсов с энкодера.
*   **`GetSpeed()`** — возвращает текущую скорость (аналог `GetRPM()`).
*   **`Reset()`** — сбрасывает счетчик энкодера обратно в `0`.

---

## 💡 Пример использования (Цепочка)

Вот как всё это выглядит в коде вместе:

```cpp
#include "studica/Titan.h"

class Robot {
public:
    // 1. Создаем контроллер
    studica::Titan m_titan{42}; 

    // 2. Создаем мотор (порт 0)
    studica::Titan::Motor& m_leftMotor = m_titan.GetMotor(0);

    // 3. Создаем энкодер (порт 0)
    studica::Titan::Encoder& m_leftEncoder = m_titan.GetEncoder(0, 1.0); 

    void TeleopPeriodic() {
        // Управляем мотором
        m_leftMotor.Set(0.5); 
        
        // Читаем расстояние
        double distance = m_leftEncoder.GetDistance(); 
    }
};
```
```
