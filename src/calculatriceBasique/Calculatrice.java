package calculatriceBasique;

import com.sun.source.tree.NewArrayTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculatrice extends JFrame {

    private JPanel container = new JPanel();
    //tabeau stokant les element a afficher dans la caculatrice
    String[] tabString = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "=", "c","+", "-", "*", "/"};
    //un bouton par element a afficher
    JButton[] tabButton = new JButton[tabString.length];
    private JLabel ecran = new JLabel();
    private Dimension dim = new Dimension(50, 40);
    private Dimension dim2 = new Dimension(50, 30);
    private double chiffre1;
    private boolean clicOperateur = false, update = false;
    private String operateur="" ;

    public Calculatrice() {
        this.setSize(400, 300);
        this.setTitle("Calculette");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        //on initialise le conteneur avec tout les composants
        initComposant();
        //on ajoute le conteneur
        this.setContentPane(container);
        this.setVisible(true);
    }

    private void initComposant() {
        //on definit la police d'ecriture a utiliser
        Font police = new Font("Arial", Font.BOLD, 20);
        ecran = new JLabel("0");
        ecran.setFont(police);
        //on aligne les informations a droite dans le jlabel
        ecran.setHorizontalAlignment(JLabel.RIGHT);
        ecran.setPreferredSize(new Dimension(220, 20));
        JPanel chiffre = new JPanel();
        JPanel operateur=new JPanel();
        operateur.setPreferredSize(new Dimension(55,200));
        chiffre.setPreferredSize(new Dimension(165, 225));
        JPanel paneEcran = new JPanel();
        paneEcran.setPreferredSize(new Dimension(220, 30));
        //on parcourt le tableau initialiser
        //afin de creer  nos bouttons
        for (int i = 0; i < tabString.length; i++) {
            tabButton[i] = new JButton(tabString[i]);
            tabButton[i].setPreferredSize(dim);
            switch (i) {
                //pour chaque element situer a la fin du tableau
                //etqui n'est pas un chiffre
                //on definir le comportement a avoir grace a un listeneur
                case 11:
                    tabButton[i].addActionListener(new EgalListener());
                    chiffre.add(tabButton[i]);
                    break;
                case 12:
                    tabButton[i].setForeground(Color.red);
                    tabButton[i].addActionListener(new ResetListener());
                    operateur.add(tabButton[i]);
                    break;
                case 13:
                    tabButton[i].addActionListener(new PlusListener());
                    tabButton[i].setPreferredSize(dim2);
                    operateur.add(tabButton[i]);
                    break;
                case 14:
                    tabButton[i].addActionListener(new MoinsListener());
                    tabButton[i].setPreferredSize(dim2);
                    operateur.add(tabButton[i]);
                    break;
                case 15:
                    tabButton[i].addActionListener(new MultiListener());
                    tabButton[i].setPreferredSize(dim2);
                    operateur.add(tabButton[i]);
                    break;
                case 16:
                    tabButton[i].addActionListener(new DivListener());
                    tabButton[i].setPreferredSize(dim2);
                    operateur.add(tabButton[i]);
                    break;
                default:
                    //par defaut,ce sont les remiers elements du tableau
                    //donc des chiffres,on affecte alors le bon listeneur
                    chiffre.add(tabButton[i]);
                    tabButton[i].addActionListener(new ChiffreListener());
                    break;
            }
        }

        paneEcran.add(ecran);
        paneEcran.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        container.add(paneEcran, BorderLayout.NORTH);
        container.add(chiffre, BorderLayout.CENTER);
        container.add(operateur, BorderLayout.WEST);
    }

    //methode permettant d'effectuer un calcul selon l'operateur selectionne
    private void calcul() {
        if (operateur.equals("+")) {
            chiffre1 = chiffre1 + Double.valueOf(ecran.getText()).doubleValue();
            ecran.setText(String.valueOf(chiffre1));
        }
        if (operateur.equals("-")) {
            chiffre1 = chiffre1 - Double.valueOf(ecran.getText()).doubleValue();
            ecran.setText(String.valueOf(chiffre1));
        }
        if (operateur.equals("*")) {
            chiffre1 = chiffre1 * Double.valueOf(ecran.getText()).doubleValue();
            ecran.setText(String.valueOf(chiffre1));
        }
        if (operateur.equals("/")) {
            try {
                chiffre1 = chiffre1 / Double.valueOf(ecran.getText()).doubleValue();
                ecran.setText(String.valueOf(chiffre1));
            } catch (ArithmeticException e) {
                ecran.setText("0");
            }
        }
    }

    //listener utiliser our les chiffres
    //permet de stocker les chiffres et les afficher
    class ChiffreListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //on affiche le chiffre additionnel dans le label
            String str = ((JButton) e.getSource()).getText();
            if (update) {
                update = false;
            } else {
                if (!ecran.getText().equals("0"))
                    str = ecran.getText() + str;
            }
            ecran.setText(str);
        }
    }
    //Listener affecter au bouton =
    class EgalListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0){
            calcul();
            update=true;
            clicOperateur=false;
        }
    }
    //Listener affecte au bouton +
    class PlusListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (clicOperateur) {
                calcul();
                ecran.setText(String.valueOf(chiffre1));
            } else {
                chiffre1 = Double.valueOf(ecran.getText()).doubleValue();
                clicOperateur = true;
            }
            operateur = "+";
            update = true;
        }
    }
    //listener affecte au bouton -
    class MoinsListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (clicOperateur) {
                calcul();
                ecran.setText(String.valueOf(chiffre1));
            } else {
                chiffre1 = Double.valueOf(ecran.getText()).doubleValue();
                clicOperateur = true;
            }
            operateur="-";
            update=true;
        }
    }
    //Listener affecte au bouton *
    class MultiListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0){
            if(clicOperateur){
                calcul();
                ecran.setText(String.valueOf(chiffre1));
            }
            else{
                chiffre1=Double.valueOf(ecran.getText()).doubleValue();
                clicOperateur=true;
            }
            operateur="*";
            update=true;
        }
    }
    //Listener affecte au bouton /
    class DivListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0){
            if(clicOperateur){
                calcul();
                ecran.setText(String.valueOf(chiffre1));
            }
            else{
                chiffre1=Double.valueOf(ecran.getText()).doubleValue();
                clicOperateur=true;
            }
            operateur="/";
            update=true;
        }
    }
    //Listener affecte au boutton de remise a zero
    class ResetListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0){
            clicOperateur=false;
            update=true;
            chiffre1=0;
            operateur="";
            ecran.setText("");
        }
    }
}
