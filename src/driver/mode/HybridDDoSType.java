package driver.mode;

import driver.mode.noiseLevel.NoNoise;
import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;
import driver.categoricalType.CategoricalType;

<<<<<<< HEAD
public final class HybridDDoSType extends SystemType{
   public HybridDDoSType(CategoricalType categoricalType) throws IOException {
      super(NoNoise.getInstance(), categoricalType);
      
      super.pfBS.add(new PreprocessTCPFlood());
      super.pfBS.add(new PreprocessUDPFlood());
      super.pfBS.add(new PreprocessHTTPFlood());
      
      super.pfBS.add(new PreprocessSlowBody());
      super.pfBS.add(new PreprocessSlowHeaders());
      super.pfBS.add(new PreprocessSlowRead());
=======
public final class HybridDDoSType extends Mode{
   
   public HybridDDoSType(int totalInstancesCount, CategoricalType categoricalType) throws IOException {
      super(totalInstancesCount, NoNoise.getInstance(), categoricalType);

//TODO make this part in all of the other subclasses not repeaet code      
      super.pfL.add(new PreprocessTCPFlood());
      super.pfL.add(new PreprocessUDPFlood());
      super.pfL.add(new PreprocessHTTPFlood());
      
      super.pfL.add(new PreprocessSlowBody());
      super.pfL.add(new PreprocessSlowHeaders());
      super.pfL.add(new PreprocessSlowRead());
      
      categoricalType.setPreprocessFileCount(super.pfL, totalInstanceCount);
>>>>>>> parent of f698a90... Before trying NetMate featuers
   }

   @Override
   public String getSystemType() {
      return "Hybrid DDoS Type";
   }
}