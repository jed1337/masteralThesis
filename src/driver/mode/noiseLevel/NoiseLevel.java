package driver.mode.noiseLevel;

import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;

public abstract class NoiseLevel {
   protected ArrayList<PreprocessFile> pfAL = new ArrayList<>();
   public abstract ArrayList<PreprocessFile> getPreprocessedFiles() throws IOException;
}