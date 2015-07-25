package app.controllers;

import app.domain.PersistencyCommunicator;

/**
 * Base class for all controllers.
 * @author jonathan
 */
public class Controller {
    // store communicator
    private final PersistencyCommunicator communicator;
    
    /**
     * Create a new controller
     * @param communicator Communicator to persistency layer
     */
    public Controller(PersistencyCommunicator communicator) {
        this.communicator = communicator;
    }
    
    protected PersistencyCommunicator getCommunicator() {
        return communicator;
    }
}
