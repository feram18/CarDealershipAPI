package edu.towson.cosc457.CarDealership.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class SiteManagerTest {
    private SiteManager siteManager;
    private Manager manager;

    @BeforeEach
    void setUp() {
        siteManager = SiteManager.builder()
                .id(1L)
                .build();

        manager = Manager.builder()
                .id(2L)
                .build();
    }

    @Test
    void shouldAssignManager() {
        siteManager.assignManager(manager);

        assertThat(siteManager.getManagers()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(manager));
    }

    @Test
    void shouldRemoveManager() {
        siteManager.assignManager(manager);
        siteManager.removeManager(manager);

        assertThat(siteManager.getManagers().isEmpty()).isTrue();
    }
}
