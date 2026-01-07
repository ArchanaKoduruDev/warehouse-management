package com.fulfilment.application.monolith.stores;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

@ApplicationScoped
public class LegacyStoreManagerGateway {

  private static final Logger LOGGER = Logger.getLogger(LegacyStoreManagerGateway.class);
  public void createStoreOnLegacySystem(Store store) {
    // just to emulate as this would send this to a legacy system, let's write a temp file with the
    writeToFile(store);
  }

  public void updateStoreOnLegacySystem(Store store) {
    // just to emulate as this would send this to a legacy system, let's write a temp file with the
    writeToFile(store);
  }

    private void writeToFile(Store store) {
        try {
            Path tempFile = Files.createTempFile(store.name, ".txt");
            String content = "Store: " + store.name + " stock: " + store.quantityProductsInStock;
            Files.write(tempFile, content.getBytes());
            LOGGER.debugf("Temporary file created and written: %s", tempFile);
            Files.delete(tempFile);
        } catch (Exception e) {
            LOGGER.errorf(e, "Failed to write store %s to temp file", store.name);
        }
    }
}
