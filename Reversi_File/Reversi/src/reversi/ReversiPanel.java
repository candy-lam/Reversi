package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * STUDENTS SHALL NOT MODIFY THIS CLASS
 * ReversiPanel GUI
 * @author Michael FUNG
 */
public class ReversiPanel extends javax.swing.JPanel implements ActionListener {

    private Reversi app;
    private Color boardColor;
    private JButton currentPlayerIndicator;
    private JButton[][] buttons;
    
    /**
     * Creates new form ReversiPanel
     * 
     * @param studentApp refers to/ links up the student app
     */
    public ReversiPanel(Reversi studentApp) {
        initComponents();

        boardColor = Color.GREEN;

        currentPlayerIndicator = new ReversiButton("", boardColor);
        currentPlayerPanel.setLayout(new BorderLayout());
        currentPlayerPanel.add(currentPlayerIndicator);
        
        board.setLayout(new GridLayout(8, 8));
        
        buttons = new JButton[10][10];
        for (int row = 1; row <= 8; row++)
            for (int col = 1; col <= 8; col++)
            {
                buttons[row][col] = new ReversiButton("R"+row+"C"+col, boardColor);
                buttons[row][col].setActionCommand("R"+row+"C"+col);
                // let this ReversiPanel object listen and handle ActionEvent
                ActionListener listenerObject = this;
                buttons[row][col].addActionListener(listenerObject);
                // add button to the grid layout
                board.add(buttons[row][col]);
            }
        
        this.app = studentApp;
    }

    /**
     * Set board color
     */
    public void setBoardColor(Color newColor)
    {
        boardColor = newColor;
        currentPlayerIndicator.setBackground(boardColor);
        for (int row = 1; row <= 8; row++)
            for (int col = 1; col <= 8; col++)
                buttons[row][col].setBackground(boardColor);
    }

    /**
     * This class implements ActionListener, thus defining this method
     * for handling user clicking actions.
     * The system invokes this method at times. We DO NOT invoke this method.
     * At the end of this method, it invokes student app's userClicked(row, col).
     * 
     * @param eventObject is a reference to the button clicked
     */
    @Override
    public void actionPerformed(ActionEvent eventObject) {
        // identify which button was clicked by the user
        JButton buttonClicked = (JButton) eventObject.getSource();
        String coordinates = eventObject.getActionCommand();
        int row = coordinates.charAt(1) - '0';
        int col = coordinates.charAt(3) - '0';
        addText("User clicked " + coordinates);
        
        // send message to student app's method for game processing
        app.userClicked(row, col);
    }
    
    
    /**
     * addText to the ReversiPanel, to be used by student app
     * 
     * @param message to append at the end of the text window
     */
    public void addText(String message) {
        messageArea.append(message + '\n');
        // scroll to the end of the text window
        messageArea.setCaretPosition(messageArea.getText().length());
    }
    
    
    /**
     * updateStatus of the game, to be invoked by student app during game processing
     * 
     * @param pieces is passed by reference, expected to be a 2D array from Reversi
     * @param currentPlayer is the current player of Reversi, either BLACK or WHITE
     */
    public void updateStatus(int[][] pieces, int currentPlayer)
    {
        int blackCount = 0;
        int whiteCount = 0;
        int emptyCount = 0;
        
        for (int row = 1; row <= 8; row++)
            for (int col = 1; col <= 8; col++)
                switch (pieces[row][col]) {
                case Reversi.BLACK:
                    buttons[row][col].setForeground(Color.BLACK);
                    blackCount++;
                    break;
                case Reversi.WHITE:
                    buttons[row][col].setForeground(Color.WHITE);
                    whiteCount++;
                    break;
                case Reversi.EMPTY:
                    buttons[row][col].setForeground(Color.GREEN);
                    emptyCount++;
                    break;
                default:
                    buttons[row][col].setForeground(Color.GREEN);
                    buttons[row][col].setBackground(Color.YELLOW);
                    String errorMessage;
                    errorMessage = String.format("Hey, wrong piece %d at [%d][%d]!",
                                                 pieces[row][col], row, col);
                    addText(errorMessage);
            }
        blackCountLabel.setText("" + blackCount);
        whiteCountLabel.setText("" + whiteCount);
        emptyCountLabel.setText("" + emptyCount);
        
        if (currentPlayer == Reversi.BLACK)
        {
            addText("*** Black's turn");
            currentPlayerIndicator.setForeground(Color.BLACK);
        }
        else if (currentPlayer == Reversi.WHITE)
        {
            addText("*** White's turn");
            currentPlayerIndicator.setForeground(Color.WHITE);
        }
        else
        {
            addText("Hey, unknown player " + currentPlayer); 
            currentPlayerIndicator.setForeground(Color.GREEN);
            currentPlayerIndicator.setBackground(Color.YELLOW);
            currentPlayerIndicator.setText("" + currentPlayer);
        }
        
        // deliver the graphical contents at once, asking for repaint
        this.repaint();
    }
    
    
    
    // This class has been created and managed using NetBeans JPanel Design view.
    // The following method and field declarations are computer generated.
    // These "grey-code" are maintained and updated by NetBeans Design view.
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        board = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        blackCountLabel = new javax.swing.JLabel();
        whiteCountLabel = new javax.swing.JLabel();
        messageAreaScrollPane = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        emptyCountLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        currentPlayerPanel = new javax.swing.JPanel();

        jLabel1.setText("Reversi");

        javax.swing.GroupLayout boardLayout = new javax.swing.GroupLayout(board);
        board.setLayout(boardLayout);
        boardLayout.setHorizontalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );
        boardLayout.setVerticalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );

        jLabel2.setText("Black");

        jLabel3.setText("White");

        blackCountLabel.setText("2");

        whiteCountLabel.setText("2");

        messageArea.setEditable(false);
        messageArea.setColumns(20);
        messageArea.setRows(5);
        messageAreaScrollPane.setViewportView(messageArea);

        jLabel4.setText("Empty");

        emptyCountLabel.setText("60");

        jLabel5.setText("Player");

        javax.swing.GroupLayout currentPlayerPanelLayout = new javax.swing.GroupLayout(currentPlayerPanel);
        currentPlayerPanel.setLayout(currentPlayerPanelLayout);
        currentPlayerPanelLayout.setHorizontalGroup(
            currentPlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );
        currentPlayerPanelLayout.setVerticalGroup(
            currentPlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(board, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(messageAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(blackCountLabel))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(whiteCountLabel))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addGap(18, 18, 18)
                                            .addComponent(emptyCountLabel)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(currentPlayerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 77, Short.MAX_VALUE))))
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(blackCountLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(whiteCountLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(emptyCountLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(currentPlayerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(34, 34, 34)))
                        .addComponent(messageAreaScrollPane))
                    .addComponent(board, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blackCountLabel;
    private javax.swing.JPanel board;
    private javax.swing.JPanel currentPlayerPanel;
    private javax.swing.JLabel emptyCountLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextArea messageArea;
    private javax.swing.JScrollPane messageAreaScrollPane;
    private javax.swing.JLabel whiteCountLabel;
    // End of variables declaration//GEN-END:variables
}
