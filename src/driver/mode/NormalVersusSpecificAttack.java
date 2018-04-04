package driver.mode;

import driver.categoricalType.SpecificAttackType;
import driver.mode.noiseLevel.NoNoise;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessNormal;

public class NormalVersusSpecificAttack extends Mode{
   private final String type;
   private final List<PreprocessFile> pfL;
   
   public NormalVersusSpecificAttack(String type, PreprocessFile pf) throws IOException {
      super(NoNoise.getInstance(), new SpecificAttackType());
      this.type = type;
      
      this.pfL = new ArrayList<>();
      this.pfL.add(new PreprocessNormal());
      this.pfL.add(pf);
   }
   
   @Override
   public String getSystemType() {
      return type;
   }

   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      return this.pfL;
   }
}