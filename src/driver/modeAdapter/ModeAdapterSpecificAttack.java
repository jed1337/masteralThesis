package driver.modeAdapter;

import driver.categoricalType.SpecificAttackType;
import driver.mode.Mode;
import driver.mode.noiseLevel.NoNoise;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;

/**
 * This class was made to avoid creating a lot of classes when creating custom comparisons
 */
public class ModeAdapterSpecificAttack extends Mode{
   final String systemType;
   final List<PreprocessFile> preprocesseFilesArr; 
   
   public ModeAdapterSpecificAttack(String systemType, PreprocessFile... preprocessFilesArr) throws IOException {
      super(NoNoise.getInstance(), new SpecificAttackType());
      this.systemType = systemType;
      
      this.preprocesseFilesArr = Collections.unmodifiableList(Arrays.asList(preprocessFilesArr));
   }
   @Override
   public final String getSystemType() {
      return this.systemType;
   }

   @Override
   public final List<PreprocessFile> getPreprocessFiles() throws IOException {
      return this.preprocesseFilesArr;
   }
}