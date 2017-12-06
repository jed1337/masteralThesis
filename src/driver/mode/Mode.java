package driver.mode;

import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;

public abstract class Mode {
   protected final int totalCount;

   public Mode(int totalCount) {
      this.totalCount = totalCount;
   }
   
   public abstract ArrayList<PreprocessFile> getPreprocessFiles() throws IOException;
   public abstract String getReplacement();
}