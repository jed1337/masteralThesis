package driver.mode.noiseLevel;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessNoiseRandomWebsites;
import preprocessFiles.PreprocessFile;

public final class Noise implements NoiseLevel{
   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      return Collections.unmodifiableList(Arrays.asList(
         new PreprocessNoiseRandomWebsites()
      ));
   }
   
   /**
    * @return 0.5 since the amount of normal data and noise gets
    * evenly distributed by categoricalType.setPreprocessFileCount(). I.E.
    * 50% noise, 50% normal
    */
   @Override
   public float getNoiseLevelFloat(){
      return 0.50f;
   }
   
   /**
    * From random websites
    * @return 
    */
   @Override
   public String getNoiseLevelString() {
//      return "Random websites";
      return "Noise";
   }
}