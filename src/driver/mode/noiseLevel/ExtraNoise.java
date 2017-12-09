package driver.mode.noiseLevel;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessExtraNoise;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessNoise;
import preprocessFiles.PreprocessNormal;

public final class ExtraNoise implements NoiseLevel{
   @Override
   public List<PreprocessFile> getPreprocessedFiles() throws IOException {
      return Collections.unmodifiableList(Arrays.asList(
         new PreprocessNoise(),
         new PreprocessExtraNoise(),
         new PreprocessNormal()
      ));
   }
}