package simulator.lock.com.br.locksimulator.handlers;

/**
 * Created by Vinicios on 05/07/2017.
 */

public interface IHandler {
    byte[] getResponse();
    void handle();
}
