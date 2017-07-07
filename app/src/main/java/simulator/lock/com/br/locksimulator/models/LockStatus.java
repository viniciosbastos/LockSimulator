package simulator.lock.com.br.locksimulator.models;

/**
 * Created by Vinicios on 06/07/2017.
 */

public enum LockStatus {
    LOCKED ("Trancado"),
    OPENED ("Aberto");

    private String label;

    private LockStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
