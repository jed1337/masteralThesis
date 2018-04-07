package driver.mode.noiseLevel;

import constants.NoiseDatasetNames;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;
import preprocessFiles.noise.PreprocessNoiseRandomWebsites;

public final class NoiseRandomWebsite implements NoiseDataset{
   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      return Collections.unmodifiableList(Arrays.asList(
         new PreprocessNoiseRandomWebsites()
      ));
   }
   
   /**
    * @return the NosieDatasetNames
    */
   @Override
   public NoiseDatasetNames getNoiseDatasetName(){
      return NoiseDatasetNames.RANDOM_WEBSITES;
   }
   
//   @Override
//   public float normalToNoiseRatio() {
//      return 0.5f;
//   }
}