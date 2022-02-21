package reversi;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;

/**
 * STUDENTS SHALL NOT MODIFY THIS CLASS
 * ReversiButton with customized display settings
 * @author Michael FUNG
 */
public class ReversiButton extends JButton {
    // constructor for preparing a green look to the button object
    // a graphical representation of a piece
    public ReversiButton(String label, Color boardColor)
    {
        // invoke a particular constructor of the super class
        super(label);
        // invoke a base method of the super class
        super.setBackground(boardColor);
    }
    
    /**
     * paintComponent draws/ redraws this ReversiButton object on the screen.
     * The system invokes this method at times. We DO NOT invoke this method.
     *
     * @param pen is a Graphics object prepared, kept and provided by the system.
     */
    @Override
    public void paintComponent(Graphics pen)
    {
        // invoke a base method in the super class, preparing the grey background, etc.
        super.paintComponent(pen);

        // draw a GREEN square with inset, leaving a fence of grid lines
        pen.setColor(getBackground());
        pen.fill3DRect(3, 3, getWidth() - 6, getHeight() - 6, true);
        
        // draw a circle piece in BLACK or WHITE
        Color buttonColor = getForeground();
        if (buttonColor == Color.BLACK || buttonColor == Color.WHITE)
        {
            pen.setColor(buttonColor);
            int diameter = Math.min(getWidth(), getHeight()) * 5 / 8;
            pen.fillOval((getWidth() - diameter) / 2, (getHeight() - diameter) / 2, diameter, diameter);
        }
        
        // draw text showing coordinates in RED
        FontMetrics fontSize = pen.getFontMetrics();
        Rectangle2D strBound = fontSize.getStringBounds(getText(), pen);
        pen.setColor(Color.RED);
        pen.drawString(getText(), (int) ((getWidth() - strBound.getWidth()) / 2 + 0.5), (int) ((getHeight() + strBound.getHeight() / 2) / 2 + 0.5));
    }
}
