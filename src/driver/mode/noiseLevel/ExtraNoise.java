package driver.mode.noiseLevel;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessNoiseRandomWebsites;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessNoiseApacheBenchmark;
import preprocessFiles.PreprocessNormal;

public final class ExtraNoise implements NoiseLevel{
   @Override
   public List<PreprocessFile> getPreprocessedFiles() throws IOException {
      return Collections.unmodifiableList(Arrays.asList(
         new PreprocessNoiseApacheBenchmark(),
         new PreprocessNoiseRandomWebsites(),
         new PreprocessNormal()
      ));
   }
}