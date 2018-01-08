package driver.mode;

import java.io.IOException;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;

public final class SpecificLowrate extends SpecificAttack{
   public SpecificLowrate() throws IOException {
      super.pfL.add(new PreprocessSlowBody());
      super.pfL.add(new PreprocessSlowHeaders());
      super.pfL.add(new PreprocessSlowRead());
   }
      
   @Override
   public String getSystemType() {
      return "Specific Low rate";
   }
}