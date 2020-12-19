package htwview;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents the view that displays the rules and controls of the
 * game. It extends JFrame.
 */
public class MessageView extends JFrame {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor to create a MessageView Object.
   */
  public MessageView() {
    super("Rules");
  }

  /**
   * This method displays the rules and controls of the game on a Frame.
   */
  public void displayRules() {

    JLabel rules;
    JPanel panel;

    panel = new JPanel();
    panel.setLayout(new BorderLayout());
    rules = new JLabel(
        "<html><p><strong><span style=\"font-size: 17px; " + "color: rgb(226, 80, 65);\">"
            + "Rules</span></strong></p>\r\n" + "<ul>\r\n" + "    <li>There are 3 hazards:<ol>\r\n"
            + "            <li>A bottomless pit (you will feel a breeze nearby).</li>\r\n"
            + "            <li>A colony of bats that will pick you up "
            + "and drop you in a random space"
            + "--including into a pit or in to the mouth of the Wumpus.</li>\r\n"
            + "            <li>A fearsome, hungry, and unbathed Wumpus (you will smell it nearby)."
            + "</li>\r\n" + "        </ol>\r\n" + "    </li>\r\n"
            + "    <li>The Wumpus is heavy; bats cannot lift him.</li>\r\n"
            + "    <li>The Wumpus will not fall down the bottomless pit.</li>\r\n"
            + "    <li>For a two player mode, the player takes turns to move to the next room or "
            + "shoot an arrow. " + "An invalid move will be counted as a turn.</li>\r\n"
            + "    <li>If two players enter the same room, then the players will indulge in "
            + "a scuffle with one player getting knocked unconscious. "
            + "The user will have to move the player for it to become visible again.</li>\r\n"
            + "    <li>The setting option lets you create a maze. (Default is a 10X10 maze)."
            + "</li>\r\n" + "</ul>\r\n"
            + "<p><strong><span style=\"font-size: 17px; color: rgb(226, 80, 65);\">"
            + "Controls</span></strong></p>\r\n" + "<ul>\r\n"
            + "    <li>Use Arrow Keys to move the Player using the Keyboard.</li>\r\n"
            + "    <li>To Shoot an Arrow, press the S key which will open a diaolog box. "
            + "Enter the number of caves and the direction.Then press the ok button.</li>\r\n"
            + "    <li>To move using a mouse, click on a valid room.</li>\r\n"
            + "    <li>Clicking on the restart button will restart the game with the same maze."
            + "</li>\r\n" + "</ul><html>");

    rules.setFont(new Font("Serif", Font.BOLD, 20));
    panel.add(rules);

    JButton ok = new JButton("OK");

    panel.add(ok, BorderLayout.SOUTH);
    this.setResizable(false);
    this.add(panel);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);

    ok.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        closeFrame();

      }

    });

  }

  /**
   * This method closes the frame.
   */
  public void closeFrame() {
    this.dispose();

  }

}