package driver.mode.noiseLevel;

import java.util.ArrayList;
import preprocessFiles.PreprocessFile;

public class NoData extends NoiseLevel{
   @Override
   public ArrayList<PreprocessFile> getPreprocessedFiles() {
      return super.pfAL;
   }
}