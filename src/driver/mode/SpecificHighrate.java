package driver.mode;

import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;

public final class SpecificHighrate extends SpecificAttack{
   public SpecificHighrate() throws IOException {
      super.pfBS.add(new PreprocessTCPFlood());
      super.pfBS.add(new PreprocessUDPFlood());
      super.pfBS.add(new PreprocessHTTPFlood());
   }
      
   @Override
   public String getSystemType() {
      return "Specific High rate";
   }
}