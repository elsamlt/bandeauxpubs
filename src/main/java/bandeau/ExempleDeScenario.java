package bandeau;

public class ExempleDeScenario {

    /**
     * "Programme principal" : point d'entrée d'exécution
     *
     * @param args les "arguments de ligne de commande", transmis au lancement du programme
     */
    public static void main(String[] args) {
        String message;
        if (args.length > 0) // J'ai au moins un paramètre
        {
            message = args[0]; // le premier paramètre
        } else {
            message = "Démonstration du bandeau";
        }

        // On cree un scenario
        Scenario s = new Scenario();
        // On lui ajoute des effets
        s.addEffect(new RandomEffect(message, 700), 1);
        s.addEffect(new TeleType("Je m'affiche caractère par caractère", 100), 1);
        s.addEffect(new Blink("Je clignote 10x", 100), 10);
        // s.addEffect(new Zoom("Je zoome", 50), 1);
        // s.addEffect(new FontEnumerator(10), 1);
        // s.addEffect(new Rainbow("Comme c'est joli !", 30), 1);
        // s.addEffect(new Rotate("2 tours à droite", 180, 4000, true), 2);

        Scenario s2 = new Scenario();
        s2.addEffect(new Rainbow("Comme c'est joli !", 30), 1);
        s2.addEffect(new Rotate("2 tours à droite", 180, 4000, true), 2);
        
        // On cree les bandeaux
        Bandeau b1 = new Bandeau();
        Bandeau b2 = new Bandeau();
        //Bandeau b3 = new Bandeau();
        System.out.println("CTRL+C pour terminer le programme");
        // V1 - On doit jouer le scénario en même temps sur plusieurs bandeaux (intégrer dans le playOn):
        s.playOn(b1);
        s2.playOn(b1);
        s.playOn(b2);
        
        // On ne doit pas pouvoir changer un scénario quand il est en train de se jouer
        try {
           Thread.sleep(1000);
        } catch (InterruptedException e) {}
        System.out.println("Ajout de l'effet");
        s.addEffect(new Rotate("2 tours à gauche", 180, 4000, false), 2);
        System.out.println("Ajout terminé");
        
        // On rejoue le scénario sur b1 quand le premier jeu est fini
//      s.playOn(b1);

    }

}
