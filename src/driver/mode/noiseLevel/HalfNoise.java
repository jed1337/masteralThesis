package driver.mode.noiseLevel;

import constants.NoiseDatasetNames;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;
import preprocessFiles.noise.PreprocessNoiseNormal;

public class HalfNoise implements NoiseLevel{
   /**
    * @return the NosieDatasetNames
    */
   @Override
   public NoiseDatasetNames getNoiseDatasetName() {
      return NoiseDatasetNames.NOISE_1;
   }

   @Override
   public float normalToNoiseRatio() {
      return 0.5f;
   }

   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      return Collections.unmodifiableList(Arrays.asList(
			new PreprocessNoiseNormal()
      ));
   }
}