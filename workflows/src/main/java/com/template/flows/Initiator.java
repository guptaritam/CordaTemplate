package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.crypto.SecureHash;
import net.corda.core.flows.*;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.flows.StartableByRPC;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.contracts.Command;
import net.corda.core.identity.Party;
import net.corda.core.contracts.*;

import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import com.template.states.*;
import com.template.contracts.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.contracts.Command;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;
import com.template.contracts.*;

import javax.persistence.criteria.CriteriaBuilder;

// ******************
// * Initiator flow *
// ******************
@InitiatingFlow
@StartableByRPC
public class Initiator extends FlowLogic<Void> {
    public Date date;
    public String identifier;

    public Party otherParty;
    public Integer percent ;
    //SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");


    public Integer Percent(){
    Date dateCheck = new Date(2010-01-01);

    if (date.after(dateCheck));
    Integer percent=50;
    if (date.before(dateCheck));
     percent=15;
    return percent;

};

    public Initiator(Date date, String identifier, Party otherParty) {
        this.date=date;
        this.identifier=identifier;
        this.otherParty=otherParty;
        this.percent=Percent();

    }

    private final ProgressTracker progressTracker = new ProgressTracker();

    @Override
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {
        // Initiator flow logic goes here.

        Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

// We create the transaction components.

        /*if (date.after(dateCheck));
        percent=50;
        if (date.before(dateCheck));
        percent=15;*/

        State1 outputState = new State1(getOurIdentity(), otherParty, identifier, date, percent);





        List<PublicKey> requiredSigners = Arrays.asList(getOurIdentity().getOwningKey(), otherParty.getOwningKey());
        Command command = new Command<>(new TemplateContract.Create(), requiredSigners);

// We create a transaction builder and add the components.
        TransactionBuilder txBuilder = new TransactionBuilder(notary)
                .addOutputState(outputState, TemplateContract.ID)
                .addCommand(command);

// Verifying the transaction.
        txBuilder.verify(getServiceHub());

// Signing the transaction.
        SignedTransaction signedTx = getServiceHub().signInitialTransaction(txBuilder);

// Creating a session with the other party.
        FlowSession otherPartySession = initiateFlow(otherParty);

// Obtaining the counterparty's signature.
        SignedTransaction fullySignedTx = subFlow(new CollectSignaturesFlow(
                signedTx, Arrays.asList(otherPartySession), CollectSignaturesFlow.tracker()));

// Finalising the transaction.
        subFlow(new FinalityFlow(fullySignedTx, otherPartySession));

        return null;
    }
}

