package bandeau;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe utilitaire pour représenter la classe-association UML
 */
class ScenarioElement {

    Effect effect;
    int repeats;

    ScenarioElement(Effect e, int r) {
        effect = e;
        repeats = r;
    }
}
/**
 * Un scenario mémorise une liste d'effets, et le nombre de repetitions pour chaque effet
 * Un scenario sait se jouer sur un bandeau.
 */
public class Scenario {

    private final List<ScenarioElement> myElements = new LinkedList<>();

    /**
     * Ajouter un effect au scenario.
     *
     * @param e l'effet à ajouter
     * @param repeats le nombre de répétitions pour cet effet
     */
    public void addEffect(Effect e, int repeats) {
        myElements.add(new ScenarioElement(e, repeats));
    }

    // Vérifie si la liste des éléments du scénario est vide
    public boolean isEmpty() {
        return myElements.isEmpty();
    }

    /**
     * Jouer ce scenario sur un bandeau
     *
     * @param b le bandeau ou s'afficher.
     */
    public void playOn(Bandeau b) {
        // for (ScenarioElement element : myElements) {
        //     for (int repeats = 0; repeats < element.repeats; repeats++) {
        //         element.effect.playOn(b);
        //     }
        // }

        // V1 - On doit jouer le scénario en même temps sur plusieurs bandeaux (intégrer dans le playOn):
        // Thread t = new Thread() {
        //     @Override
        //     public void run() {
        //         for (ScenarioElement element : myElements) {
        //             for (int repeats = 0; repeats < element.repeats; repeats++) {
        //                 element.effect.playOn(b); 
        //             }
        //         }
        //     }
        // };

        // V2 - On ne doit pas jouer plusieurs scénarios en même temps sur le même bandeau (synchronisation)
        // Thread t2 = new Thread() {
        //     @Override
        //     public void run() {
        //         synchronized (b) { 
        //             for (ScenarioElement element : myElements) {
        //                 for (int repeats = 0; repeats < element.repeats; repeats++) {
        //                     element.effect.playOn(b);  
        //                 }
        //             }
        //         }
        //     }
        // };

        // V3 - On ne doit pas modifier un scénario s'il est en train d'être joué dans un bandeau        
        // Thread t3 = new Thread() {
        //     @Override
        //     public void run() {
        //         synchronized (b) {
        //         // Attendre que la pile ne soit pas vide
        //         while(isEmpty()) {
        //             b.wait();
        //         }

        //         for (ScenarioElement element : myElements) {
        //             for (int repeats = 0; repeats < element.repeats; repeats++) {
        //                 element.effect.playOn(b);  
        //             }
        //         }
                    
        //         Thread.sleep(10000);
        //         }
        //         synchronized (b) {
        //             b.notifyAll();
        //         }
        //     }
        // };

        Thread t3 = new Thread() {
            @Override
            public void run() {
                synchronized (b) { 
                    try {
                        while (isEmpty()) {
                            b.wait();  
                        }

                        for (ScenarioElement element : myElements) {
                            for (int repeats = 0; repeats < element.repeats; repeats++) {
                                element.effect.playOn(b);
                            }
                        }

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        b.notifyAll();
                    }
                }
            }
        };

        // Démarrer les deux threads
        // t.start();
        // t2.start();
        t3.start();
    }
}
