package sdu.sem2.se17.domain.persistenceinterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import sdu.sem2.se17.domain.production.ProductionCompany;

import static org.junit.jupiter.api.Assertions.*;

class ProductionCompanyHandlerTest {

    private ProductionCompanyHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ProductionCompanyHandlerImplSample();
    }

    @DisplayName("CRUD operations")
    @Nested
    class Crud {
        @Test
        void create() {
            var productionCompany = new ProductionCompany(0, "Sample");
            var result = handler.create(productionCompany);

            assertTrue(result.isPresent());
        }
        @Test
        void read() {
            var productionCompany = new ProductionCompany(0, "Sample");
            var savedProductionCompany = handler.create(productionCompany);

            var result = handler.read(savedProductionCompany.get().getId());

            assertTrue(result.isPresent());
        }
        @Test
        void update() {
            var productionCompany = new ProductionCompany(0, "Sample");
            var startProductionCompany = handler.create(productionCompany);

            startProductionCompany.get().setName("Changed name");

            handler.update(startProductionCompany.get());

            var result = handler.read(startProductionCompany.get().getId());
            assertEquals(
                    startProductionCompany.get().getName(),
                    result.get().getName());

        }
        @Test
        void delete() {
            var productionCompany = new ProductionCompany(0, "Sample");
            var savedProductionCompany = handler.create(productionCompany);

            handler.delete(savedProductionCompany.get().getId());

            var result = handler.read(savedProductionCompany.get().getId());

            assertFalse(result.isPresent());
        }
    }
    
    @Test
    void readAll() {
        handler.create(new ProductionCompany(0, "Sample1"));
        handler.create(new ProductionCompany(0, "Sample2"));
        handler.create(new ProductionCompany(0, "Sample3"));
        handler.create(new ProductionCompany(0, "Sample4"));

        assertEquals(4, handler.readAll().size());
    }
}