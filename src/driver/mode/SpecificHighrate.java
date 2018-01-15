package driver.mode;

import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;
import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class SpecificHighrate extends SpecificAttack{
   public SpecificHighrate() throws IOException {}
   
   @Override
   public String getSystemType() {
      return "Specific Highrate";
   }

   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      ArrayList<PreprocessFile> pfL = new ArrayList<>();
      pfL.add(new PreprocessTCPFlood());
      pfL.add(new PreprocessUDPFlood());
      pfL.add(new PreprocessHTTPFlood());
      return pfL;
   }
}