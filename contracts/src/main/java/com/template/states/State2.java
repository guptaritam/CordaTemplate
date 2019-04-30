package com.template.states;


import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.Arrays;
import java.util.List;
import com.template.contracts.*;

// *********
// * State *
// *********
@BelongsToContract(TemplateContract.class)
public class State2 implements ContractState {

    public Party getPartyA() {
        return PartyA;
    }

    public Party getPartyB() {
        return PartyB;
    }

    public Integer getPercent() {
        return percent;
    }


    public Party PartyA;
    public Party PartyB;


    public  Integer percent;

    public State2(Party PartyA,Party PartyB,Integer percent) {
        this.PartyA=PartyA;
        this.PartyB=PartyB;
        this.percent=percent;
    }

    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(PartyA, PartyB);
    }

}