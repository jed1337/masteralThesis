package driver.mode.noiseLevel;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;
import preprocessFiles.noise.PreprocessNoiseNormal;

public class HalfNoise implements NoiseLevel{
   /**
    * @return 0.5 since the amount of normal data and noise gets
    * evenly distributed by categoricalType.setPreprocessFileCount(). I.E.
    * 1/2: Normal, 1/2: NormalNoise
    */
   @Override
   public float getNoiseLevelFloat() {
      return 0.5f;
   }

   @Override
   public String getNoiseLevelString() {
      return "Half Noise";
   }

   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      return Collections.unmodifiableList(Arrays.asList(
			new PreprocessNoiseNormal()
      ));
   }
}