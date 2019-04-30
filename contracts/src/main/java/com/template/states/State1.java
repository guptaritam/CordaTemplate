package com.template.states;


import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.*;
import com.template.contracts.*;

import java.util.Arrays;
import java.util.List;
import net.corda.core.flows.*;
import net.corda.core.schemas.QueryableState;
import org.jetbrains.annotations.NotNull;


// *********
// * State *
// *********
@BelongsToContract(TemplateContract.class)

public class State1 implements ContractState{

    public Party getPartyA() {
        return PartyA;
    }

    public Party getPartyB() {
        return PartyB;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Date getDate() {
        return date;
    }

    public Integer getPercent() {
        return percent;
    }

    public Party PartyA;
    public Party PartyB;

    public  String identifier;
    public Date date;



    public  Integer percent;


    public State1(Party PartyA,Party PartyB,String identifier,Date date,Integer percent) {
        this.PartyA=PartyA;
        this.PartyB=PartyB;
        this.identifier=identifier;
        this.date=date;
        this.percent=percent;


    }

    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(PartyA, PartyB);
    }



}
