#include <frc/TimedRobot.h>
#include <frc/XboxController.h>
#include "studica/Titan.h"

class Robot : public frc::TimedRobot {
 public:
  // 1. СОЗДАЕМ ОБЪЕКТЫ (Это происходит один раз при включении)
  studica::Titan m_titan{42}; // Наш контроллер моторов
  
  // Моторы (левая и правая сторона)
  studica::Titan::Motor& m_fl = m_titan.GetMotor(0);
  studica::Titan::Motor& m_bl = m_titan.GetMotor(1);
  studica::Titan::Motor& m_fr = m_titan.GetMotor(2);
  studica::Titan::Motor& m_br = m_titan.GetMotor(3);

  // Пульт
  frc::XboxController m_controller{0};

  // 2. ГЛАВНЫЙ ЦИКЛ (Это повторяется 50 раз в секунду, пока робот включен)
  void TeleopPeriodic() override {
    // Читаем сигнал с пульта
    double forward = m_controller.GetLeftY();
    double turn = m_controller.GetRightX();

    // Считаем скорость для левых и правых колес
    double leftSpeed = forward + turn;
    double rightSpeed = forward - turn;

    // Отправляем команды моторам
    m_fl.Set(leftSpeed);
    m_bl.Set(leftSpeed);
    m_fr.Set(rightSpeed);
    m_br.Set(rightSpeed);
  }
};

#ifndef RUNNING_FRC_TESTS
int main() { return frc::StartRobot<Robot>(); }
#endif
