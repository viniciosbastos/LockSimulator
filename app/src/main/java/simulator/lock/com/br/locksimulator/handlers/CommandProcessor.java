package simulator.lock.com.br.locksimulator.handlers;

import android.os.Handler;

/**
 * Created by Vinicios on 05/07/2017.
 */

public class CommandProcessor {
    private static final byte LOGIN = 'U';
    private static final byte GRANT_PERMISSION = 'C';
    private static final byte REVOKE_PERMISSION = 'E';
    private static final byte LOG = 'L';
    private static final byte OPEN = 'A';
    private static final byte CLOSE = 'F';

    public static IHandler HandlerFactory(byte[] command, Handler handler) {
        IHandler iHandler = null;
        if (command != null) {
            byte op = command[0];

            switch(op) {
                case LOGIN:
                    iHandler = new LoginHandler(command, handler);
                    break;

                case GRANT_PERMISSION:
                    iHandler = new GrantPermissionHandler(command, handler);
                    break;

                case REVOKE_PERMISSION:
                    break;

                case LOG:
                    break;

                case OPEN:
                    iHandler = new OpenHandler(command, handler);
                    break;

                case CLOSE:
                    iHandler = new CloseHandler(command, handler);
                    break;
            }
            iHandler.handle();
        }
        return iHandler;
    }
}
