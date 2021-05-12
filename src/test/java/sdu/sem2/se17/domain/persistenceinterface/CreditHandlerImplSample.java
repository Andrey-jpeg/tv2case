package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.production.Production;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreditHandlerImplSample implements CreditHandler {

    private final ArrayList<Credit> credits = new ArrayList<>();
    private final HashMap<Long, ArrayList<Long>> creditAssociations = new HashMap<>();

    private long creditIdCounter = 1;

    @Override
    public Optional<Credit> create(Credit credit) {

        credit.setId(creditIdCounter++);

        var newCredit = new Credit(){{
            setId(credit.getId());
            setRole(credit.getRole());
            setParticipant(getParticipant());
        }};

        credits.add(newCredit);

        return Optional.of(newCredit);
    }

    @Override
    public Optional<Credit> read(long id) {
        return credits
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public void update(Credit credit) {
        var storedCredit = credits
                .stream()
                .filter(c -> c.getId() == credit.getId())
                .findFirst();

        storedCredit.get().setId(credit.getId());
        storedCredit.get().setParticipant(credit.getParticipant());
        storedCredit.get().setRole(credit.getRole());

    }

    @Override
    public void delete(long id) {
        credits.removeIf(c -> c.getId() == id);
    }

    @Override
    public ArrayList<Credit> findByProductionId(long id) {
        var creditIds = creditAssociations.get(id);
        return (ArrayList<Credit>) credits
                .stream()
                .filter(c -> creditIds.contains(c.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Credit> create(Credit credit, long productionId) {
        return Optional.empty();
    }

    public void sampleUpdateProductionWithCredits(Production production) {
        if(!creditAssociations.containsKey(production.getId())){
            creditAssociations.put(production.getId(), new ArrayList<>());
        }

        var creditIds = creditAssociations.get(production.getId());
        var existingCredits = credits
                .stream()
                .filter(c -> creditIds.contains(c.getId()))
                .collect(Collectors.toList());

        for (Credit credit: production.getCredits()) {
            if(existingCredits
                    .stream()
                    .noneMatch(c -> c.getId() == credit.getId())){
                addCreditToProduction(production.getId(), credit);
            }
        }
        for (Credit credit: existingCredits) {
            if(production.getCredits()
                    .stream()
                    .noneMatch(c -> c.getId() == credit.getId())){
                removeCreditFromProduction(production.getId(), credit);
            }
        }
    }

    private void addCreditToProduction(long productionId, Credit credit) {
        if (read(credit.getId()).isEmpty()){
            credit = create(credit).get();
        }
        creditAssociations.get(productionId).add(credit.getId());
    }
    private void removeCreditFromProduction(long productionId, Credit credit) {
        creditAssociations.get(productionId).remove(credit.getId());
    }


}
