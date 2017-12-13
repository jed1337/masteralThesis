package driver.mode.noiseLevel;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessNoiseRandomWebsites;
import preprocessFiles.PreprocessFile;

public final class NoiseNormal implements NoiseLevel{
   @Override
   public List<PreprocessFile> getPreprocessedFiles() throws IOException {
      return Collections.unmodifiableList(Arrays.asList(
         new PreprocessNoiseRandomWebsites()
      ));
   }
}