package com.template.contracts;

import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;
import net.corda.core.contracts.CommandWithParties;
import net.corda.core.identity.Party;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.*;




import static net.corda.core.contracts.ContractsDSL.requireSingleCommand;
import com.template.states.*;



// ************
// * Contract *
// ************
public class TemplateContract implements Contract {
    // This is used to identify our contract when building a transaction.
    public static final String ID = "com.template.contracts.TemplateContract";



    // Our Create command.
    public static class Create implements CommandData {
    }



    // A transaction is valid if the verify() function of the contract of all the transaction's input and output states
    // does not throw an exception.
    @Override
    public void verify(LedgerTransaction tx) {
        final CommandWithParties<TemplateContract.Create> command = requireSingleCommand(tx.getCommands(), TemplateContract.Create.class);

       // Date dateCheck = new Date(2010-11-11);


        // Constraints on the shape of the transaction.
        if (!tx.getInputs().isEmpty())
            throw new IllegalArgumentException("No inputs should be consumed when issuing an IOU.");

        if (!(tx.getOutputs().size() == 1))
            throw new IllegalArgumentException("There should be one output state of type IOUState.");

        // IOU-specific constraints.
        final State1 output = tx.outputsOfType(State1.class).get(0);
        final Party PartyA = output.getPartyA();
        final Party PArtyB = output.getPartyB();
       /* if(output.date.before(dateCheck)) {
            output.percent=15;
        }
        if(output.date.after(dateCheck)) {
            output.percent=50;
        }
*/

        if (output.getIdentifier() == null)
            throw new IllegalArgumentException("The identifiers value must be non-null.");
        if (PartyA.equals(PArtyB))
            throw new IllegalArgumentException("Both the parties can not be equal");



        // Constraints on the signers.
        final List<PublicKey> requiredSigners = command.getSigners();
        final List<PublicKey> expectedSigners = Arrays.asList(PartyA.getOwningKey(), PArtyB.getOwningKey());
        if (requiredSigners.size() != 2)
            throw new IllegalArgumentException("There must be two signers.");
        if (!(requiredSigners.containsAll(expectedSigners)))
            throw new IllegalArgumentException("The two parties must be signers.");

    }

    public interface Commands extends CommandData {
        class Action implements Commands {
        }
    }
}