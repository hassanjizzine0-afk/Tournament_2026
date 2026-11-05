
### 🟢 БЛОК 1: ПОДГОТОВКА (Выполняется 1 раз при включении робота)

```text
[ СТАРТ ]
   │
   ▼
[ Подключить пульт (Порт 0) ]
   │   (Код: XboxController m_controller = new XboxController(0);)
   ▼
[ Создать контроллер Titan (ID 42) ]
   │   (Код: Titan m_titan = new Titan(42);)
   ▼
[ Назначить 4 мотора (порты 0, 1, 2, 3) ]
   │   (Код: Titan.Motor m_frontLeft = m_titan.getMotor(0);) и т.д.
   ▼
[ Задать размеры колес для математики (Translation2d) ]
   │   (Код: Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);) и т.д.
   ▼
[ Создать математический калькулятор (Kinematics) ]
   │   (Код: MecanumDriveKinematics m_kinematics = new MecanumDriveKinematics(...);)
   ▼
(Переход в бесконечный цикл)
```

---

### 🔵 БЛОК 2: БЕСКОНЕЧНЫЙ ЦИКЛ (Выполняется 50 раз в секунду)
*Программа выполняет это постоянно, пока робот включен и работает в режиме управления.*

```text
[ НАЧАЛО ЦИКЛА ]
   │
   ▼
[ 1. Прочитать левый стик ВВЕРХ/ВНИЗ -> Переменная "forward" ]
   │   (Код: double forward = -m_controller.getLeftY();)
   ▼
[ 2. Прочитать левый стик ВЛЕВО/ВПРАВО -> Переменная "strafe" ]
   │   (Код: double strafe = -m_controller.getLeftX();)
   ▼
[ 3. Прочитать правый стик ВЛЕВО/ВПРАВО -> Переменная "turn" ]
   │   (Код: double turn = m_controller.getRightX();)
   ▼
[ 4. Упаковать (forward, strafe, turn) в объект "ChassisSpeeds" ]
   │   (Код: ChassisSpeeds speeds = new ChassisSpeeds(forward, strafe, turn);)
   ▼
[ 5. МАТЕМАТИКА: Превратить "ChassisSpeeds" в скорости для каждого колеса ]
   │   (Код: MecanumDriveWheelSpeeds wheelSpeeds = m_kinematics.toWheelSpeeds(speeds);)
   ▼
[ 6. Отправить скорость 1 -> Левому переднему мотору ]
[ 7. Отправить скорость 2 -> Левому заднему мотору    ]
[ 8. Отправить скорость 3 -> Правому переднему мотору ]
[ 9. Отправить скорость 4 -> Правому заднему мотору   ]
   │   (Код: m_frontLeft.set(wheelSpeeds.frontLeftMetersPerSecond);) и т.д.
   ▼
[ ПОДОЖДАТЬ 0.02 СЕКУНДЫ ] (Это делает сама WPILib, писать ничего не нужно)
   │
   └───(Вернуться в НАЧАЛО ЦИКЛА)
```
```
