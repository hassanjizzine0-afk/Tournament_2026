import com.studica.frc.Titan;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.math.geometry.Translation2d;

public class Robot extends TimedRobot {
    // 1. Создаем пульт
    XboxController m_controller = new XboxController(0); 
    
    // 2. Создаем моторы (4 штуки)
    Titan m_titan = new Titan(42); 
    Titan.Motor m_frontLeft  = m_titan.getMotor(0);
    Titan.Motor m_backLeft   = m_titan.getMotor(1);
    Titan.Motor m_frontRight = m_titan.getMotor(2);
    Titan.Motor m_backRight  = m_titan.getMotor(3);

    // 3. Создаем кинематику (указываем размеры робота в метрах)
    // (Замени эти цифры на реальные размеры твоего робота!)
    Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
    Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
    Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
    Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);
    
    MecanumDriveKinematics m_kinematics = new MecanumDriveKinematics(
        m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation
    );

    @Override
    public void teleopPeriodic() {
        // Читаем ПУЛЬТ (теперь 3 оси)
        double forward = -m_controller.getLeftY(); // Вперед/назад
        double strafe  = -m_controller.getLeftX(); // Вбок (влево/вправо)
        double turn    = m_controller.getRightX(); // Поворот

        // Создаем желаемую скорость робота
        ChassisSpeeds speeds = new ChassisSpeeds(forward, strafe, turn);

        // МАГИЯ: WPILib сам считает скорости для каждого колеса
        MecanumDriveWheelSpeeds wheelSpeeds = m_kinematics.toWheelSpeeds(speeds);

        // Отправляем команды на моторы (Titan)
        // (Обрати внимание: тут мы напрямую передаем скорость, которую посчитал WPILib)
        m_frontLeft.set(wheelSpeeds.frontLeftMetersPerSecond);
        m_frontRight.set(wheelSpeeds.frontRightMetersPerSecond);
        m_backLeft.set(wheelSpeeds.rearLeftMetersPerSecond);
        m_backRight.set(wheelSpeeds.rearRightMetersPerSecond);
    }
}
