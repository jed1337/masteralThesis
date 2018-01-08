package driver.mode;

import java.io.IOException;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;

public final class SpecificLowrate extends SpecificAttack{
   public SpecificLowrate() throws IOException {
      super.pfBS.add(new PreprocessSlowBody());
      super.pfBS.add(new PreprocessSlowHeaders());
      super.pfBS.add(new PreprocessSlowRead());
   }
      
   @Override
   public String getSystemType() {
      return "Specific Low rate";
   }
}