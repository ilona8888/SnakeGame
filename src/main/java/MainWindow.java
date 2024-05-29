import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() throws HeadlessException {
        setTitle("Змейка"); // Задает наименование окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // способность
        setSize(320,345); // Задает размеры окна
        setLocation(400,400);
        setResizable(false); // нельзя расширить окно
        add(new GameField()); //добавляем JPanel
        setVisible(true); //Делаем окно видимым
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow(); //создаем экземпляр класса

    }
}
