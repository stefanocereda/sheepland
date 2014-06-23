/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import java.io.Serializable;

/**
 * This is a superclass for all the game objects (excluding all the enums and
 * the deck of card). It provides an identificator for each object, this is done
 * by a setId method that should be called by the server whenever it creates a
 * game object. All the objects should be created only by the server so we use
 * as identificator the number of objects created. Then the server passes all
 * the objects to the client, that should NEVER set the id. This class also
 * provides an implementation of equals. In this way when the client and the
 * server speaks to each other they can understand what is the object they are
 * referring to.
 * 
 * @author Stefano
 * 
 */
public class GenericGameObject implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = -7360277395568886611L;

    /** a class counter of the number of objects created */
    private static long created = 0;

    /** The id of the object */
    protected long id;

    /**
     * This method sets the id of the object, it MUST be called ONLY by the
     * server on EACH object it creates and ONLY on their creation
     */
    public void setID() {
        id = created++;
    }

    /**
     * we override hashCode to match the overridden version of equals
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ id >>> 32);
        return result;
    }

    /**
     * Two game objects are equals if they are of the same type and if they have
     * the same id
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GenericGameObject)) {
            return false;
        }
        GenericGameObject other = (GenericGameObject) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
