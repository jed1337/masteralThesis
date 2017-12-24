package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;
import preprocessFiles.PreprocessNormal;

import driver.categoricalType.*;
import driver.categoricalType.AttackType;

public final class Single extends Mode{
   public Single(int totalCount, NoiseLevel nl, AttackType categoricalType) throws IOException {
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