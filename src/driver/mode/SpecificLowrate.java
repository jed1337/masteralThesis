package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import driver.categoricalType.CategoricalType;

public final class SpecificLowrate extends SpecificAttack{
<<<<<<< HEAD
   public SpecificLowrate() throws IOException {
      super.pfBS.add(new PreprocessSlowBody());
      super.pfBS.add(new PreprocessSlowHeaders());
      super.pfBS.add(new PreprocessSlowRead());
=======
   public SpecificLowrate(int totalInstancesCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(totalInstancesCount, nl, categoricalType);
      
      super.pfL.add(new PreprocessSlowBody());
      super.pfL.add(new PreprocessSlowHeaders());
      super.pfL.add(new PreprocessSlowRead());

      categoricalType.setPreprocessFileCount(super.pfL, totalInstancesCount);
>>>>>>> parent of f698a90... Before trying NetMate featuers
   }
      
   @Override
   public String getSystemType() {
      return "Specific Highrate";
   }
}