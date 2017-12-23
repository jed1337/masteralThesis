package driver.mode;

import driver.categoricalType.CategoricalType;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;
import preprocessFiles.PreprocessNormal;

import driver.categoricalType.*;

public final class Single extends Mode{
   public Single(int totalCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(totalCount, nl, categoricalType);
      
      super.pfL.add(new PreprocessHighrate());
      super.pfL.add(new PreprocessLowrate());
      super.pfL.add(new PreprocessNormal());
      
      super.setPreprocessFileCount();
   }

//   @Override
//   public String getRelabel() {
//      return "";
//   }
      
   @Override
   public String getSystemType() {
      return "Single";
   }
}