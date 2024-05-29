import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private final int SIZE = 320; //размер поле
    private final int DOT_SIZE = 16; //разхмер ячейки

    private final int All_DOTS = 400;

    private Image dot;

    private Image apple;

    private int appleX;

    private int appleY;

    private int[] x = new int[All_DOTS];
    private int[] y = new int[All_DOTS];

    private int dots;

    private Timer timer;
    private boolean left = false;
    private boolean right = true;

    private boolean up = false;
    private boolean down = false;

    private boolean inGame = true;


    public GameField() {
        setBackground(Color.BLACK); //задет фон черным
        loadImages();  //загружаем картинки с ресурсов
        initGame(); //запускаем игру
        addKeyListener(new FieldKeyListener()); // добавляем управление с клавиатуры
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;  //размер змеи в точках
        for(int i = 0; i < dots; i++){
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }

        timer = new Timer(250,this);
        timer.start();
        createApple();

    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            createApple();
        }
    }

    public void createApple(){
        //создаем координаты яблока в пределах от 0-19 * на DOT_SIZE
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;

    }

    public void loadImages(){
        ImageIcon  imageIconApple = new ImageIcon(getClass().getResource("/icons/apple.png"));
        apple = imageIconApple.getImage();

        ImageIcon imageIconDot = new ImageIcon(getClass().getResource("/icons/dot.png"));

        dot = imageIconDot.getImage();
    }

    public void move(){
        for(int i = dots; i > 0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] +=DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }

        if (down){
            y[0] += DOT_SIZE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){

            //Проверка, находится ли яблоко в пределах поля
            if (appleX < 0 || appleX >= SIZE || appleY < 0 || appleY >= SIZE){
                createApple();
            }
            g.drawImage(apple,appleX,appleY,this);
            for(int i = 0; i < dots; i++){
                g.drawImage(dot,x[i],y[i],this);
            }
        }
        else {

            //В случае если у игры выход за пределы поля – игра заканивается
            String str = "Game Over";
            g.setColor(Color.white);
            g.drawString(str,125, SIZE/2);
        }

    }

    public void checkCollision() {
        //Проверка на выход за размер игрового поля
        for (int i = dots; i > 0; i--) {
            if(i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if (x[0] > SIZE){
            inGame = false;
        }

        if (x[0] < 0){
            inGame = false;
        }
        if(y[0] > SIZE){
            inGame = false;
        }

        if(y[0] < 0){
            inGame = false;
        }
    }

    class FieldKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_RIGHT &&  !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP &&  !down){
                right = false;
                up = true;
                left = false;
            }

            if(key == KeyEvent.VK_DOWN &&  !down){
                right = false;
                down = true;
                left = false;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollision();
            move();
            repaint();
        }
    }
}
