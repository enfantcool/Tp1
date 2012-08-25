/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.inf202.tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 *
 * @author Usager
 */
public class Tp1
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        Carte ahaha = new Carte();
        Joueur thesee = new Joueur(Case.getThesee(), true);
        Joueur minotaure = new Joueur(Case.getMino(), false);
        visibilite(thesee.getCarreau(true));
        boolean fini = false;
        while (!fini)
        {
            mAJCarte();
            Joueur.affScore();
            mouvement(thesee);
            visibilite(thesee.getCarreau(true));
            decouvrirPoint();
            tourMinotaure();
            fini = Joueur.testGagnant();
        }




    }

    public static void mAJCarte()
    {
        Case carreau;
        System.out.println("");
        for (int i = 0; i < Case.getLigne(); i++)
        {
            for (int j = 0; j < Case.getColonne(); j++)
            {
                carreau = Case.getCarreau(i, j);
                if (carreau.getContenu() == 'M')
                {
                    boolean leMino=false;
                    leMino=auMino(Case.getThesee(),1);
                    leMino=auMino(Case.getThesee(),2);
                    leMino=auMino(Case.getThesee(),3);
                    leMino=auMino(Case.getThesee(),4);
                    if (carreau.contientPoint()&&!leMino)
                    {
                        System.out.print(".");
                    }
                    else if(carreau.estVisible()&&!leMino)
                    {
                        System.out.print("M");
                        
                    }
                    else if(carreau.contientPoint()&&leMino)
                    {
                        System.out.print('M');
                    }
                    else if(carreau.estVisible())
                    {
                        System.out.print(carreau.getContenu());
                    }
                    
                }
                else if (carreau.estVisible())
                {
                    System.out.print(carreau.getContenu());
                }
                
                else
                {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }

    }

    public static void mouvement(Joueur joueur)
    {
        int direction = getDirection(traitementClavier());
        if (direction == 0)
        {
            mouvement(joueur);
        }
        else
        {
            joueur.bouge(direction, joueur.getIndexT());
        }
    }

    public static void tourMinotaure()
    {
        Random random = new Random();
        int direction;
        boolean notBouge = true;
        do
        {
            direction = random.nextInt(4) + 1;
            int directionIndex = Case.getDirectionIndex(direction, Case.getMino().getIndex());
            if (Case.getCarreau(directionIndex).getContenu() == 'X')
            {
                direction = random.nextInt(4) + 1;
                directionIndex = Case.getDirectionIndex(direction, Case.getMino().getIndex());
            }
            else
            {
                Joueur.bouge(direction, Case.getMino().getIndex());
                notBouge = false;
            }
        } while (notBouge);

    }

    public static char traitementClavier()
    {
        boolean erreur = true;
        String choix = "";
        while (erreur)
        {
            System.out.println("Il est temps de bouger.\n w:Haut; a:Gauche; s:Bas; d:Droite; q: Sortie");
            choix = lectureClavier();
            System.out.println("");
            if (!(choix == null) && choix.length() == 1)
            {
                erreur = false;

            }
            else
            {
                System.out.println("Entrez une direction ou entrez q pour sortir.");
            }
        }
        return choix.toLowerCase().charAt(0);


    }

    public static int getDirection(char choix)
    {
        int direction = 0;
        switch (choix)
        {
            case 'w':
                direction = 1;
                break;
            case 'a':
                direction = 2;
                break;
            case 's':
                direction = 3;
                break;
            case 'd':
                direction = 4;
                break;
            case 'q':
                System.exit(0);
                break;
            default:
                System.out.println("Entrez une direction ou entrez q pour sortir.");
        }

        return direction;
    }

    public static String lectureClavier()
    {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            String choix = r.readLine();
            return choix;
        }
        catch (IOException e)
        {
            System.out.println("Une erreur est survenue.");
            return null;
        }
    }

    public static void visibilite(Case thesee)
    {
        decouvrir(thesee, true);
        decouvrir(thesee, false);
        if (auMurX(thesee, 1))
        {
            System.out.println("Une erreur est survenue");
        }
        if (auMurX(thesee, 2))
        {
            System.out.println("Une erreur est survenue");
        }
        if (auMurX(thesee, 3))
        {
            System.out.println("Une erreur est survenue");
        }
        if (auMurX(thesee, 4))
        {
            System.out.println("Une erreur est survenue");
        }

    }

    public static void decouvrir(Case temp, boolean inverse)
    {
        if (inverse)
        {
            for (int i = 0; i < Case.getLastIndex(); i++)
            {
                Case.getCarreau(i).setVisible(false);

            }
        }
        else
        {
            for (int i = -1; i <= 1; i++)
            {
                for (int j = -1; j <= 1; j++)
                {
                    Case.getCarreau(temp.getX() + i, temp.getY() + j).setVisible(true);
                }

            }
        }
    }
public static void decouvrirPoint(){
    for (int i = 0; i < Case.getLastIndex(); i++)
    {
       Case temp= Case.getCarreau(i);
       if (temp.getContenu()=='.')
       {
           for (int k = -1; k <= 1; k++)
            {
                for (int j = -1; j <= 1; j++)
                {
                    Case temp2=Case.getCarreau(temp.getX() + k, temp.getY() + j);
                        temp2.setVisible(true); 
                }

            }
       }
        
    }
}
public static boolean auMino(Case debut, int direction){
   Case fin;
        switch (direction)
        {
            case 1:
                fin = Case.getCarreau(debut.getX() - 1, debut.getY());
                break;
            case 2:
                fin = Case.getCarreau(debut.getX(), debut.getY() - 1);
                break;
            case 3:
                fin = Case.getCarreau(debut.getX() + 1, debut.getY());
                break;
            case 4:
                fin = Case.getCarreau(debut.getX(), debut.getY() + 1);
                break;
            default:
                System.out.println("La direction est mauvaise, c'est bizarre!");
                fin = null;
        }
        if (fin.getContenu() == 'M')
        {
            return true;


        }
        else if(fin.getContenu()=='X'||fin.getContenu()=='O')
        {
            return false;
        }
        else
        {
            return auMino(fin, direction);
        } 
}
    public static boolean auMurX(Case debut, int direction)
    {

        Case fin;
        switch (direction)
        {
            case 1:
                fin = Case.getCarreau(debut.getX() - 1, debut.getY());
                break;
            case 2:
                fin = Case.getCarreau(debut.getX(), debut.getY() - 1);
                break;
            case 3:
                fin = Case.getCarreau(debut.getX() + 1, debut.getY());
                break;
            case 4:
                fin = Case.getCarreau(debut.getX(), debut.getY() + 1);
                break;
            default:
                System.out.println("La direction est mauvaise, c'est bizarre!");
                fin = null;
        }
        if (fin.getContenu() == 'X'||fin.getContenu()=='O')
        {
            fin.setVisible(true);
            return false;


        }
        else
        {
            fin.setVisible(true);
            decouvrir(fin, false);
            return auMurX(fin, direction);
        }


    }
    public static void mAJFinal(){
        for (int i = 0; i < Case.getLigne(); i++) {
            for (int j = 0; j < Case.getColonne(); j++) {
                System.out.print(Case.getCarreau(i,j).getContenu());
                
            }
            System.out.println("");
            
        }
    }
}
