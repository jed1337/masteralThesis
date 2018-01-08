package driver.mode;

import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;

public final class SpecificHighrate extends SpecificAttack{
   public SpecificHighrate() throws IOException {
      super.pfL.add(new PreprocessTCPFlood());
      super.pfL.add(new PreprocessUDPFlood());
      super.pfL.add(new PreprocessHTTPFlood());
   }
      
   @Override
   public String getSystemType() {
      return "Specific High rate";
   }
}