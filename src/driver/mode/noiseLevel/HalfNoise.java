package driver.mode.noiseLevel;

import constants.NoiseDatasetNames;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;
import preprocessFiles.noise.PreprocessNoiseNormal;

/**
 * Contains the noise files involving normal accesses that behave like DDoS attacks
 */
public class HalfNoise implements NoiseDataset{

   @Override
   public NoiseDatasetNames getNoiseDatasetName() {
      return NoiseDatasetNames.NOISE_1;
   }

   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      return Collections.unmodifiableList(Arrays.asList(
			new PreprocessNoiseNormal()
      ));
   }
}