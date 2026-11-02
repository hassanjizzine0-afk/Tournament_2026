import com.studica.frc.Titan;

public class Robot {
    // 1. Создаем контроллер Titan
    Titan m_titan = new Titan(42); 

    // 2. Создаем 4 мотора (0, 1 — левая сторона; 2, 3 — правая)
    Titan.Motor m_frontLeft  = m_titan.getMotor(0);
    Titan.Motor m_backLeft   = m_titan.getMotor(1);
    Titan.Motor m_frontRight = m_titan.getMotor(2);
    Titan.Motor m_backRight  = m_titan.getMotor(3);

    // 3. Создаем 4 энкодера (для каждого мотора)
    Titan.Encoder m_frontLeftEncoder  = m_titan.getEncoder(0, 1.0);
    Titan.Encoder m_backLeftEncoder   = m_titan.getEncoder(1, 1.0);
    Titan.Encoder m_frontRightEncoder = m_titan.getEncoder(2, 1.0);
    Titan.Encoder m_backRightEncoder  = m_titan.getEncoder(3, 1.0);

    // Метод для управления моторами (вызывать его будешь из основного цикла)
    public void drive(double leftSpeed, double rightSpeed) {
        // Отправляем скорость на ВСЕ 4 мотора
        m_frontLeft.set(leftSpeed);
        m_backLeft.set(leftSpeed);
        m_frontRight.set(rightSpeed);
        m_backRight.set(rightSpeed);
    }

    // Метод для чтения энкодеров
    public void readSensors() {
        double leftDist  = m_frontLeftEncoder.getDistance();
        double rightDist = m_frontRightEncoder.getDistance();
        // Тут можно вывести их в консоль или использовать для балансировки
    }
}
