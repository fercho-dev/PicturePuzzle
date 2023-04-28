package picturepuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Puzzle extends JFrame implements ActionListener {
    ArrayList<Icon> allImages = new ArrayList<Icon>();
    ArrayList<JButton> allButtons = new ArrayList<JButton>();
    JPanel panel;
    boolean firstClick = false;
    JButton firstButton;
    JButton secondButton;

    public Puzzle() {
        super("Picture picturepuzzle.Puzzle");
        storeImages();
        init();
    }

    public void init() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(3,3,1,1));
        getContentPane().add(panel, BorderLayout.CENTER);
        createButtons();
        JButton resetBtn = new JButton("RESET");
        getContentPane().add(resetBtn, BorderLayout.SOUTH);
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allButtons.clear();
                getContentPane().removeAll();
                init();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void storeImages() {
        for(int i = 1; i < 10; i++) {
            Icon ic = new ImageIcon(getClass().getClassLoader().getResource("dog-puzzle/" + i + ".jpg"));
            allImages.add(ic);
        }
    }

    public void createButtons() {
        for(int i = 0; i < 9; i++) {
            JButton btn = new JButton(resizeImage(allImages.get(i)));
            ((ImageIcon) btn.getIcon()).setDescription(String.valueOf(i));
            btn.setPreferredSize(new Dimension(150,150));
            btn.addActionListener(this);
            allButtons.add(btn);
        }
        Collections.shuffle(allButtons);
        for(int j = 0; j < 9; j++) {
            panel.add(allButtons.get(j));
        }
    }

    public Icon resizeImage(Icon input) {
        ImageIcon img = new ImageIcon(((ImageIcon) input).getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH));
        return img;
    }

    public static void main(String[] args) {
        new Puzzle();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!firstClick) {
            firstClick = true;
            firstButton = (JButton) e.getSource();
        } else {
            firstClick = false;
            secondButton = (JButton) e.getSource();
            if(secondButton != firstButton) {
                swap();
            }
            boolean result = checkWin();
            if(result) {
                JOptionPane.showMessageDialog(this,"Great job! You WON!!", "Congrats", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    public void swap() {
        Icon ic1 = firstButton.getIcon();
        Icon ic2 = secondButton.getIcon();
        firstButton.setIcon(ic2);
        secondButton.setIcon(ic1);
    }

    public boolean checkWin() {
        boolean win = true;
        for(int i = 0; i < 9; i++) {
            if(!((ImageIcon) allButtons.get(i).getIcon()).getDescription().equals(String.valueOf(i))) {
                win = false;
                break;
            }
        }
        return win;
    }
}
