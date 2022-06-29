
package rs.ac.bg.fon.nprog.client.controller;

import rs.ac.bg.fon.nprog.client.communication.Communication;
import rs.ac.bg.fon.nprog.commonlibrary.communication.Request;
import rs.ac.bg.fon.nprog.commonlibrary.communication.Response;
import rs.ac.bg.fon.nprog.commonlibrary.communication.util.Operation;
import rs.ac.bg.fon.nprog.commonlibrary.communication.util.ResponseType;
import rs.ac.bg.fon.nprog.commonlibrary.domain.RepairItem;

import java.util.List;

/**
 *
 * @author Dragon
 */
public class RepairItemController {

    private static RepairItemController instance;

    private RepairItemController() {
    }

    public static RepairItemController getInstance() {
        if (instance == null) {
            instance = new RepairItemController();
        }
        return instance;
    }

    public List<RepairItem> getRepairItemsByFKCondition(RepairItem repairItem) throws Exception {
        Request request = new Request(Operation.GET_REPAIR_ITEMS_BY_FK_CONDITION, repairItem);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<RepairItem>) response.getResult();
        }
        throw response.getException();
    }
}
