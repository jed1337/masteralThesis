package preprocessFiles.noise;

import constants.FileNameConstants;
import constants.PreprocessFileName;
import java.io.IOException;

public final class PreprocessNoiseRandomWebsites extends PreprocessNoiseFile{
   public PreprocessNoiseRandomWebsites() throws IOException {
      super(
         FileNameConstants.NOISE_RANDOM_WEBSITES,
         PreprocessFileName.NOISE_RANDOM_WEBSITES);
   }
}