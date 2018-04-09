package driver.mode;

import driver.categoricalType.CategoricalType;
import driver.mode.noiseLevel.NoiseDataset;
import generalInterfaces.GetPreprocessFiles;
import java.io.IOException;

public abstract class Mode implements GetPreprocessFiles{
   private final NoiseDataset nl;
   private final CategoricalType categoricalType;

   public Mode(NoiseDataset nl, CategoricalType categoricalType) throws IOException {
      this.nl = nl;
      this.categoricalType = categoricalType;
   }

   public final NoiseDataset getNoiseLevel() {
      return this.nl;
   }

   public final CategoricalType getCategoricalType() {
      return this.categoricalType;
   }
   
   public abstract String getSystemType();
}