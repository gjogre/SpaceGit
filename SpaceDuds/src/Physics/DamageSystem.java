package Physics;

import GameObjects.GameObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;


public class DamageSystem implements ContactListener{

    private static float damageTreshold = 2f;

    private ArrayList<GameObject> damageObjects = new ArrayList<>();
    @Override
    public void beginContact(Contact cntct) {
        
    }

    @Override
    public void endContact(Contact cntct) {
       
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
     
    }

    public void addObject(GameObject b){
        System.out.println(b.toString());
        damageObjects.add(b);
    }
    public void clearObjects(){
        damageObjects.clear();
    }

    public void removeObject(GameObject b){

        if(damageObjects.contains(b)){
                damageObjects.remove(b);
        }

    }
    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
        if(ci.normalImpulses[0] > damageTreshold)
            {

                if(cntct.getFixtureA() != null && cntct.getFixtureB() != null)
                {
                    Iterator<GameObject> itr = damageObjects.iterator();
                        while(itr.hasNext()){
                            GameObject g = itr.next();
                            if(g.markForKill){
                                itr.remove();
                            } else {
                                if(cntct.getFixtureA().getBody() == g.getBody()||cntct.getFixtureB().getBody() == g.getBody()){
                                    g.takeHit(ci.normalImpulses[0]);
                                }
                            }
                    }
                }

        }

    }
    
}
