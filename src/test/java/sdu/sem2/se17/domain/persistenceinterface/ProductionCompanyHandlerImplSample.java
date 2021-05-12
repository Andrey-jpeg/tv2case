package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.production.ProductionCompany;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ProductionCompanyHandlerImplSample implements ProductionCompanyHandler {
    private final ArrayList<ProductionCompany> productionCompanies = new ArrayList<>();

    private long productionCompanyIdCounter = 1;

    @Override
    public Optional<ProductionCompany> create(ProductionCompany productionCompany) {

        var newProductionCompany = new ProductionCompany(
                productionCompanyIdCounter++,
                productionCompany.getName()
        );

        productionCompanies.add(newProductionCompany);

        return Optional.of(newProductionCompany);
    }

    @Override
    public Optional<ProductionCompany> read(long id) {
        return productionCompanies
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public void update(ProductionCompany productionCompany) {
        var storedProductionCompany = productionCompanies
                .stream()
                .filter(c -> c.getId() == productionCompany.getId())
                .findFirst();

        storedProductionCompany.get().setId(productionCompany.getId());
        storedProductionCompany.get().setName(productionCompany.getName());
    }

    @Override
    public void delete(long id) {
        productionCompanies.removeIf(c -> c.getId() == id);
    }
    @Override
    public ArrayList<ProductionCompany> readAll() {
        return productionCompanies;
    }
}
