package driver.mode;

import java.io.IOException;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class SpecificLowrate extends SpecificAttack{
   public SpecificLowrate() throws IOException {}
   
   @Override
   public String getSystemType() {
      return "Specific Lowrate";
   }

   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      ArrayList<PreprocessFile> pfL = new ArrayList<>();
      pfL.add(new PreprocessSlowBody());
      pfL.add(new PreprocessSlowHeaders());
      pfL.add(new PreprocessSlowRead());
      return pfL;
   }
}