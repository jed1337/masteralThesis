package driver.mode;

import driver.categoricalType.CategoricalType;
import driver.mode.noiseLevel.NoiseLevel;
import generalInterfaces.GetPreprocessFiles;
import java.io.IOException;

public abstract class Mode implements GetPreprocessFiles{
   private final NoiseLevel nl;
   private final CategoricalType categoricalType;

   public Mode(NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      this.nl = nl;
      this.categoricalType = categoricalType;
   }

   public NoiseLevel getNoiseLevel() {
      return this.nl;
   }

   public CategoricalType getCategoricalType() {
      return this.categoricalType;
   }
   
   public abstract String getSystemType();
}